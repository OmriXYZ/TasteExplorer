package com.omrimega.tasteexplorer.utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.omrimega.tasteexplorer.RecipeAdapter;
import com.omrimega.tasteexplorer.RecipeUploadCallback;
import com.omrimega.tasteexplorer.models.Recipe;

import java.util.List;

public class DataManager {
    private static DataManager instance = null;
    private Context context;
    public FirebaseDatabase mDatabase;
    public DatabaseReference myRef;
    private static FirebaseUser mFirebaseUser;
    public static FirebaseStorage mStorage;


    private DataManager(Context context) {
        this.context = context;
        mDatabase = FirebaseDatabase.getInstance();
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        myRef = mDatabase.getReference().child("recipes");
        mStorage = FirebaseStorage.getInstance();
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new DataManager(context);
        }
    }

    public static FirebaseUser getUser() {
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return mFirebaseUser;
    }

    public static DataManager getInstance() {
        return instance;
    }


    public static void uploadRecipe(Uri imageUrl, String title, String instructions, String tags, int time, int persons, ProgressDialog dialog, RecipeUploadCallback callback) {
        StorageReference filepath = mStorage.getReference().child("imagePost").child(imageUrl.getLastPathSegment());
        filepath.putFile(imageUrl).addOnSuccessListener(taskSnapshot -> {
            Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DatabaseReference newPost = instance.myRef.push();
                    newPost.child("title").setValue(title);
                    newPost.child("instructions").setValue(instructions);
                    newPost.child("tags").setValue(tags);
                    newPost.child("time").setValue(time + "");
                    newPost.child("numOfPersons").setValue(persons + "");
                    newPost.child("image").setValue(task.getResult().toString());
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    newPost.child("creator").setValue(firebaseAuth.getCurrentUser().getEmail());
                    callback.onRecipeUploadSuccess(dialog);
                } else {
                    callback.onRecipeUploadFailure(task.getException());
                }
                dialog.dismiss();
            });
        });
    }
    public static void findMyRecipes(List<Recipe> recipeList, RecipeAdapter recipeAdapter, List<String> idKeysList) {
        DataManager.getInstance().myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Recipe recipe = snapshot.getValue(Recipe.class);

                if (FirebaseAuth.getInstance().getCurrentUser() == null)
                    return;
                if (recipe.getCreator().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                    recipeList.add(recipe);
                    recipeAdapter.notifyDataSetChanged();
                    idKeysList.add(snapshot.getKey());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static void deleteRecipeByIdKey(String idKey) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("recipes").child(idKey);
        myRef.removeValue()
                .addOnSuccessListener(aVoid -> SignalGenerator.getInstance().showToast("The recipe has been successfully deleted", 500))
                .addOnFailureListener(e -> {
                    // Failed to delete data
                });
    }
    public static void findRecipes(List<Recipe> recipeList, RecipeAdapter recipeAdapter, List<String> idKeysList) {
        DataManager.getInstance().myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Recipe recipe = snapshot.getValue(Recipe.class);
                recipeList.add(recipe);
                recipeAdapter.notifyDataSetChanged();
                idKeysList.add(snapshot.getKey());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

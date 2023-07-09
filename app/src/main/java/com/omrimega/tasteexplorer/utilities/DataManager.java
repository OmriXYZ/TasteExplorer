package com.omrimega.tasteexplorer.utilities;

import android.content.Context;
import android.net.Uri;
import android.os.Debug;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class DataManager {
    private static DataManager instance = null;
    private Context context;
    public FirebaseDatabase mDatabase;
    public DatabaseReference myRef;

    public static FirebaseStorage mStorage;


    private DataManager(Context context) {
        this.context = context;
        mDatabase = FirebaseDatabase.getInstance();
        FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        myRef = mDatabase.getReference().child("recipes");
        mStorage = FirebaseStorage.getInstance();
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new DataManager(context);
        }
    }

    public static DataManager getInstance() {
        return instance;
    }


    public static void uploadRecipe(Uri imageUrl, String title, String instructions, String tags, int time, int persons) {
        StorageReference filepath = mStorage.getReference().child("imagePost").child(imageUrl.getLastPathSegment());
        filepath.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        String t = task.getResult().toString();

                        DatabaseReference newPost = instance.myRef.push();

                        newPost.child("title").setValue(title);
                        newPost.child("instructions").setValue(instructions);
                        newPost.child("tags").setValue(tags);
                        newPost.child("time").setValue(time + "");
                        newPost.child("numOfPersons").setValue(persons + "");
                        newPost.child("image").setValue(task.getResult().toString());
                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        newPost.child("creator").setValue(firebaseAuth.getCurrentUser().getEmail());
                    }
                });
            }
        });
    }
}

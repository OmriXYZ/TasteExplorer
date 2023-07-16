package com.omrimega.tasteexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.omrimega.tasteexplorer.databinding.RecipesFilteredBinding;
import com.omrimega.tasteexplorer.models.Recipe;
import com.omrimega.tasteexplorer.utilities.DataManager;

import java.util.ArrayList;
import java.util.List;


public class RecipeFiltered extends AppCompatActivity {

    private RecipesFilteredBinding binding;
    private RecyclerView recipefiltered_VIEW_recyclerView;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_filtered);
        binding = RecipesFilteredBinding.inflate(getLayoutInflater());

        recipefiltered_VIEW_recyclerView = findViewById(R.id.recipefiltered_VIEW_recyclerView);
        recipefiltered_VIEW_recyclerView.setHasFixedSize(true);
        recipefiltered_VIEW_recyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        recipeList = new ArrayList<>();
        recipeAdapter = new RecipeAdapter(binding.getRoot().getContext(), recipeList);
        recipefiltered_VIEW_recyclerView.setAdapter(recipeAdapter);

        Intent prevIntent = getIntent();
        String tags = prevIntent.getStringExtra("tags");
        String[] tagsList = tags.split(", ");


        DataManager.getInstance().myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Recipe recipe = snapshot.getValue(Recipe.class);
                for (String tag: tagsList) {
                    Log.d("tag", tag);
                    if (!recipe.getTags().contains(tag)) {
                        return;
                    }
                }
                recipeList.add(recipe);
                recipeAdapter.notifyDataSetChanged();
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

package com.omrimega.tasteexplorer.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.omrimega.tasteexplorer.R;
import com.omrimega.tasteexplorer.RecipeAdapter;
import com.omrimega.tasteexplorer.RecipeFullPage;
import com.omrimega.tasteexplorer.RecyclerItemClickListener;
import com.omrimega.tasteexplorer.RetrieveDataInRecyclerView;
import com.omrimega.tasteexplorer.databinding.FragmentHomeBinding;
import com.omrimega.tasteexplorer.models.Recipe;
import com.omrimega.tasteexplorer.utilities.DataManager;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipeList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.recipeview_VIEW_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        recipeList = new ArrayList<Recipe>();

        recipeAdapter = new RecipeAdapter(root.getContext(), recipeList);
        recyclerView.setAdapter(recipeAdapter);

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        DataManager.getInstance().myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Recipe recipe = snapshot.getValue(Recipe.class);
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

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent recipeFullPage = new Intent(getContext(), RecipeFullPage.class);
                        recipeFullPage.putExtra("tags", recipeList.get(position).getTags());
                        recipeFullPage.putExtra("title", recipeList.get(position).getTitle());
                        recipeFullPage.putExtra("inst", recipeList.get(position).getInstructions());
                        recipeFullPage.putExtra("time", recipeList.get(position).getTime());
                        recipeFullPage.putExtra("persons", recipeList.get(position).getNumOfPersons());
                        recipeFullPage.putExtra("by", recipeList.get(position).getCreator());
                        recipeFullPage.putExtra("img", recipeList.get(position).getImage());
                        startActivity(recipeFullPage);
//                        ((Activity) getActivity()).overridePendingTransition(0, 0);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.omrimega.tasteexplorer.ui;

import static com.omrimega.tasteexplorer.utilities.DataManager.findMyRecipes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omrimega.tasteexplorer.R;
import com.omrimega.tasteexplorer.RecipeAdapter;
import com.omrimega.tasteexplorer.RecipeFullPage;
import com.omrimega.tasteexplorer.RecyclerItemClickListener;
import com.omrimega.tasteexplorer.databinding.FragmentMyRecipesBinding;
import com.omrimega.tasteexplorer.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class MyRecipesFragment extends Fragment {

    private FragmentMyRecipesBinding binding;
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipeList;
    private List<String> idKeys;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMyRecipesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.myRecipes_VIEW_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        idKeys = new ArrayList<>();

        recipeList = new ArrayList<>();

        recipeAdapter = new RecipeAdapter(root.getContext(), recipeList);
        recyclerView.setAdapter(recipeAdapter);

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

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
                        recipeFullPage.putExtra("id", idKeys.get(position));

                        startActivity(recipeFullPage);
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
    @Override
    public void onResume() {
        super.onResume();
        recipeList.clear();
        idKeys.clear();
        findMyRecipes(recipeList, recipeAdapter, idKeys);
    }

}
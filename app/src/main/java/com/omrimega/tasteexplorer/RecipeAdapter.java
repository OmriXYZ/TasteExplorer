package com.omrimega.tasteexplorer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.omrimega.tasteexplorer.models.Recipe;
import com.omrimega.tasteexplorer.utilities.DataManager;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private Context context;
    private List<Recipe> recipeList;

    public RecipeAdapter(Context context, List<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_recycleview, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.recipeview_TXT_title.setText(recipe.getTitle());
        holder.recipeview_TXT_tags.setText(recipe.getTags());
        holder.recipeview_TXT_time.setText("minutes: " + recipe.getTime());
        holder.recipeview_TXT_persons.setText("persons: " + recipe.getNumOfPersons());
        holder.recipeview_TXT_by.setText("by: " + recipe.getCreator());


        String imageUri = null;
        imageUri = recipe.getImage();
        Picasso.get().load(imageUri).into(holder.recipeview_IMG_image);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView recipeview_IMG_image;
        private TextView recipeview_TXT_tags, recipeview_TXT_title, recipeview_TXT_time, recipeview_TXT_persons, recipeview_TXT_by;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeview_IMG_image = itemView.findViewById(R.id.recipeview_IMG_image);
            recipeview_TXT_tags = itemView.findViewById(R.id.recipeview_TXT_tags);
            recipeview_TXT_title = itemView.findViewById(R.id.recipeview_TXT_title);
            recipeview_TXT_time = itemView.findViewById(R.id.recipeview_TXT_time);
            recipeview_TXT_persons = itemView.findViewById(R.id.recipeview_TXT_persons);
            recipeview_TXT_by = itemView.findViewById(R.id.recipeview_TXT_by);
        }

    }
}

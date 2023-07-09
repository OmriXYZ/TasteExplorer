package com.omrimega.tasteexplorer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
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
import com.omrimega.tasteexplorer.databinding.FragmentHomeBinding;
import com.omrimega.tasteexplorer.models.Recipe;
import com.omrimega.tasteexplorer.utilities.DataManager;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RecipeFullPage extends AppCompatActivity {


    private FragmentHomeBinding binding;

    private TextView recipefull_TXT_tags, recipefull_TXT_title, recipefull_TXT_instructions, recipefull_TXT_time, recipefull_TXT_persons, recipefull_TXT_by;

    private ImageView recipefull_IMG_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_full);

        findViews();

        Intent prevIntent = getIntent();
        String tags = prevIntent.getStringExtra("tags");
        String title = prevIntent.getStringExtra("title");
        String inst = prevIntent.getStringExtra("inst");
        String time = prevIntent.getStringExtra("time");
        String persons = prevIntent.getStringExtra("persons");
        String by = prevIntent.getStringExtra("by");
        String img = prevIntent.getStringExtra("img");

        recipefull_TXT_tags.setText(tags);
        recipefull_TXT_title.setText(title);
        recipefull_TXT_instructions.setText(inst);
        recipefull_TXT_time.setText("minutes: " + time);
        recipefull_TXT_by.setText("by: " + by);
        recipefull_TXT_persons.setText("persons: " + persons);

        Picasso.get().load(img).into(recipefull_IMG_image);
    }

    private void findViews() {
        recipefull_TXT_tags = findViewById(R.id.recipefull_TXT_tags);
        recipefull_TXT_by = findViewById(R.id.recipefull_TXT_by);
        recipefull_TXT_instructions = findViewById(R.id.recipefull_TXT_instructions);
        recipefull_TXT_persons = findViewById(R.id.recipefull_TXT_persons);
        recipefull_TXT_time = findViewById(R.id.recipefull_TXT_time);
        recipefull_TXT_title = findViewById(R.id.recipefull_TXT_title);
        recipefull_IMG_image = findViewById(R.id.recipefull_IMG_image);
    }

}
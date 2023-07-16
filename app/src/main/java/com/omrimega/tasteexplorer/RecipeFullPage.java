package com.omrimega.tasteexplorer;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.omrimega.tasteexplorer.utilities.DataManager;
import com.omrimega.tasteexplorer.utilities.SignalGenerator;
import com.squareup.picasso.Picasso;

public class RecipeFullPage extends AppCompatActivity {
    private TextView recipefull_TXT_tags, recipefull_TXT_title, recipefull_TXT_instructions, recipefull_TXT_time, recipefull_TXT_persons, recipefull_TXT_by;
    private ImageView recipefull_IMG_image;
    private Button recipefull_BTN_delete;

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
        String idKey = prevIntent.getStringExtra("id");

        recipefull_TXT_tags.setText(tags);
        recipefull_TXT_title.setText(title);
        recipefull_TXT_instructions.setText(inst);
        recipefull_TXT_time.setText("minutes: " + time);
        recipefull_TXT_by.setText("by: " + by);
        recipefull_TXT_persons.setText("persons: " + persons);

        if (DataManager.getUser().getEmail().equals(by))
            recipefull_BTN_delete.setVisibility(View.VISIBLE);
        else
            recipefull_BTN_delete.setVisibility(View.INVISIBLE);

        recipefull_BTN_delete.setOnClickListener(v -> {
            onDeleteRecipe(idKey);
        });

        Picasso.get().load(img).into(recipefull_IMG_image);
    }

    private void onDeleteRecipe(String idKey) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RecipeFullPage.this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_delete_recipe, null);

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        dialogView.findViewById(R.id.dialog_BTN_delete).setOnClickListener(view -> {
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("recipes").child(idKey);
            myRef.removeValue()
                    .addOnSuccessListener(aVoid -> {
                        SignalGenerator.getInstance().showToast("The recipe has been successfully deleted", 1000);
                        dialog.dismiss();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        // Failed to delete data
                    });

        });

        dialogView.findViewById(R.id.dialog_BTN_cancel).setOnClickListener(view -> dialog.dismiss());
        if (dialog.getWindow() != null){
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        dialog.show();
    }

    private void findViews() {
        recipefull_TXT_tags = findViewById(R.id.recipefull_TXT_tags);
        recipefull_TXT_by = findViewById(R.id.recipefull_TXT_by);
        recipefull_TXT_instructions = findViewById(R.id.recipefull_TXT_instructions);
        recipefull_TXT_persons = findViewById(R.id.recipefull_TXT_persons);
        recipefull_TXT_time = findViewById(R.id.recipefull_TXT_time);
        recipefull_TXT_title = findViewById(R.id.recipefull_TXT_title);
        recipefull_IMG_image = findViewById(R.id.recipefull_IMG_image);
        recipefull_BTN_delete = findViewById(R.id.recipefull_BTN_delete);
    }

}
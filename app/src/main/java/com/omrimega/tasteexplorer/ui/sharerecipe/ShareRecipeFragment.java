package com.omrimega.tasteexplorer.ui.sharerecipe;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.circularreveal.CircularRevealGridLayout;
import com.omrimega.tasteexplorer.R;
import com.omrimega.tasteexplorer.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class ShareRecipeFragment extends Fragment implements View.OnClickListener {

    private EditText share_recipe_EDIT_name, share_recipe_EDIT_instructions, share_recipe_EDIT_tags;
    private NumberPicker share_recipe_PICK_time, share_recipe_PICK_persons;
    private Button share_recipe_BTN_uploadphotos, share_recipe_BTN_share;
//    CircularRevealGridLayout share_recipe_GRIDBTN_tags_buttons;
//    private Button btnBreakfast, btnBrunch, btnLunch, btnDinner;
//    private Button btnPickTime, btnUploadPhotos, btnShareRecipe;
//    private Spinner spinnerDifficulty;
    private List<String> selectedTags;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_share_recipe, container, false);

        share_recipe_EDIT_name = rootView.findViewById(R.id.share_recipe_EDIT_name);
        share_recipe_EDIT_instructions = rootView.findViewById(R.id.share_recipe_EDIT_instructions);
        share_recipe_EDIT_tags = rootView.findViewById(R.id.share_recipe_EDIT_tags);

        share_recipe_PICK_persons = rootView.findViewById(R.id.share_recipe_PICK_persons);
        share_recipe_PICK_time = rootView.findViewById(R.id.share_recipe_PICK_time);

        share_recipe_BTN_uploadphotos = rootView.findViewById(R.id.share_recipe_BTN_uploadphotos);
        share_recipe_BTN_share = rootView.findViewById(R.id.share_recipe_BTN_share);


//        share_recipe_GRIDBTN_tags_buttons = rootView.findViewById(R.id.share_recipe_GRIDBTN_tags_buttons);
//        share_recipe_GRIDBTN_tags_buttons.addView(createCustomButton(requireContext(), "Breakfast"));
//        share_recipe_GRIDBTN_tags_buttons.addView(createCustomButton(requireContext(), "Brunch"));
//        share_recipe_GRIDBTN_tags_buttons.addView(createCustomButton(requireContext(), "Lunch"));
//        share_recipe_GRIDBTN_tags_buttons.addView(createCustomButton(requireContext(), "Dinner"));
//        share_recipe_GRIDBTN_tags_buttons.addView(createCustomButton(requireContext(), "shachar"));
//        share_recipe_GRIDBTN_tags_buttons.addView(createCustomButton(requireContext(), "omriki"));
//        share_recipe_GRIDBTN_tags_buttons.addView(createCustomButton(requireContext(), "dddd"));
//        share_recipe_GRIDBTN_tags_buttons.addView(createCustomButton(requireContext(), "omrqweiki"));
//
//        share_recipe_GRIDBTN_tags_buttons.addView(createCustomButton(requireContext(), "lala"));
//        share_recipe_GRIDBTN_tags_buttons.addView(createCustomButton(requireContext(), "popo"));

        selectedTags = new ArrayList<>();

//        etNumOfPersons = rootView.findViewById(R.id.et_num_of_persons);
//
//        btnBreakfast = rootView.findViewById(R.id.btn_breakfast);
//        btnBrunch = rootView.findViewById(R.id.btn_brunch);
//        btnLunch = rootView.findViewById(R.id.btn_lunch);
//        btnDinner = rootView.findViewById(R.id.btn_dinner);

//        btnPickTime = rootView.findViewById(R.id.btn_pick_time);
//        btnUploadPhotos = rootView.findViewById(R.id.btn_upload_photos);
//        btnShareRecipe = rootView.findViewById(R.id.btn_share_recipe);
//
//        spinnerDifficulty = rootView.findViewById(R.id.spinner_difficulty);
//        ArrayAdapter<CharSequence> difficultyAdapter = ArrayAdapter.createFromResource(requireContext(),
//                R.array.difficulty_levels, android.R.layout.simple_spinner_item);
//        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerDifficulty.setAdapter(difficultyAdapter);

//        selectedTags = new ArrayList<>();
//
//        btnBreakfast.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addTag("Breakfast");
//            }
//        });
//
//        btnBrunch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addTag("Brunch");
//            }
//        });
//
//        btnLunch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addTag("Lunch");
//            }
//        });
//
//        btnDinner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addTag("Dinner");
//            }
//        });
//
//        btnPickTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showTimePickerDialog();
//            }
//        });
//
        share_recipe_BTN_uploadphotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilePicker();
            }
        });
//
//        btnShareRecipe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                shareRecipe();
//            }
//        });

        return rootView;
    }

    private void addTag(String tag, MaterialButton button) {
        if (selectedTags.contains(tag)) {
            selectedTags.remove(tag);
            String tags = share_recipe_EDIT_tags.getText().toString();
            if (tags.contains(tag + ", ")) {
                tags = tags.replace(tag + ", ", "");
            } else if (tags.contains(", " + tag)) {
                tags = tags.replace(", " + tag, "");
            } else if (tags.contains(tag)) {
                tags = tags.replace(tag, "");
            }
            share_recipe_EDIT_tags.setText(tags);
            button.setBackgroundColor(getResources().getColor(R.color.basic_color));
        } else {
            selectedTags.add(tag);
            String tags = share_recipe_EDIT_tags.getText().toString();
            if (!TextUtils.isEmpty(tags)) {
                tags += ", ";
            }
            tags += tag;
            share_recipe_EDIT_tags.setText(tags);
            button.setBackgroundColor(getResources().getColor(R.color.selected_btn));
            button.setPaintFlags(button.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }
    }



    private void showTimePickerDialog() {
        // Implement the time picker dialog logic
    }

    private void openFilePicker() {
        // Implement the file picker logic for photo upload
    }

//    private void shareRecipe() {
//        String recipeName = share_recipe_EDIT_name.getText().toString().trim();
//        String description = share_recipe_EDIT_instructions.getText().toString().trim();
//        String tags = share_recipe_EDIT_tags.getText().toString().trim();
//        String numOfPersons = etNumOfPersons.getText().toString().trim();
//        String difficulty = spinnerDifficulty.getSelectedItem().toString();
//
//        // Validate the input fields
//        if (TextUtils.isEmpty(recipeName)) {
//            share_recipe_EDIT_name.setError("Please enter a recipe name");
//            share_recipe_EDIT_name.requestFocus();
//            return;
//        }
//
//        // Create a Recipe object and upload it to Firebase
////        Recipe recipe = new Recipe(recipeName, Arrays.asList(tags.split(",")), difficulty, numOfPersons, "Creator Name", 3,מק, description);
////        uploadRecipeToFirebase(recipe);
//
//        // Clear the input fields
//        share_recipe_EDIT_name.setText("");
//        share_recipe_EDIT_instructions.setText("");
//        share_recipe_EDIT_tags.setText("");
//        etNumOfPersons.setText("");
//        spinnerDifficulty.setSelection(0);
//        selectedTags.clear();
//
//        // Show a success message or perform any additional actions
//        Toast.makeText(requireContext(), "Recipe shared successfully", Toast.LENGTH_SHORT).show();
//    }

    private void uploadRecipeToFirebase(Recipe recipe) {
        // Implement the logic to upload the recipe to Firebase
    }

    @Override
    public void onClick(View v) {

    }

    public MaterialButton createCustomButton(Context context, String text) {
        MaterialButton button = new MaterialButton(context);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                dpToPx(context, 30)
        );
        button.setLayoutParams(params);
        button.setPadding(5, 1, 1, 1);
        button.setText(text);
        button.setCornerRadius(dpToPx(context, 20));
        button.setBackgroundTintList(context.getResources().getColorStateList(R.color.basic_color));
        button.setOnClickListener(v -> addTag(text, button));
        return button;
    }

    private int dpToPx(Context context, int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}

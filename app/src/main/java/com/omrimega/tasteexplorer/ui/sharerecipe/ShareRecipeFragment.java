package com.omrimega.tasteexplorer.ui.sharerecipe;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.circularreveal.CircularRevealGridLayout;
import com.google.firebase.storage.StorageReference;
import com.omrimega.tasteexplorer.R;
import com.omrimega.tasteexplorer.models.Recipe;
import com.omrimega.tasteexplorer.utilities.CustomSpinner;
import com.omrimega.tasteexplorer.utilities.DataManager;
import com.omrimega.tasteexplorer.utilities.SignalGenerator;

import java.util.ArrayList;
import java.util.List;

public class ShareRecipeFragment extends Fragment implements View.OnClickListener {

    private EditText share_recipe_EDIT_name, share_recipe_EDIT_instructions, share_recipe_EDIT_tags;
    private NumberPicker share_recipe_PICK_time, share_recipe_PICK_persons;
    private Button share_recipe_BTN_uploadphotos, share_recipe_BTN_share;
    private ImageButton share_recipe_IMGBTN_imgbtn1;
    private CustomSpinner share_recipe_SPN_category, share_recipe_SPN_type, share_recipe_SPN_flavor;


    private static final int GalleryCode = 1;
    private Uri imageUrl = null;
    private ProgressDialog progressDialog;


//    CircularRevealGridLayout share_recipe_GRIDBTN_tags_buttons;
//    private Button btnBreakfast, btnBrunch, btnLunch, btnDinner;
//    private Button btnPickTime, btnUploadPhotos, btnShareRecipe;
//    private Spinner spinnerDifficulty;
    private List<String> selectedTags;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_share_recipe, container, false);

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        findViews(rootView);

        initCategorySpinner();
        initGeneralSpinner();
        initFlavorSpinner();
//        share_recipe_SPN_type.set


        progressDialog = new ProgressDialog(getContext());

        share_recipe_PICK_time.setMinValue(1);
        share_recipe_PICK_time.setMaxValue(1000);

        share_recipe_PICK_persons.setMinValue(1);
        share_recipe_PICK_persons.setMaxValue(30);


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


        share_recipe_BTN_uploadphotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });


        share_recipe_BTN_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = share_recipe_EDIT_name.getText().toString().trim();
                String instructions = share_recipe_EDIT_instructions.getText().toString().trim();
                String tags = share_recipe_EDIT_tags.getText().toString().trim();
                int time = share_recipe_PICK_time.getValue();
                int persons = share_recipe_PICK_persons.getValue();

                if (!title.isEmpty() && !instructions.isEmpty() && time != 0 && persons != 0 && imageUrl != null) {
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();
                    DataManager.getInstance().uploadRecipe(imageUrl, title, instructions, tags, time, persons);
                    progressDialog.dismiss();
                    SignalGenerator.getInstance().showToast("Upload completed!", 400);
                    cleanBoxes();
                } else {
                    SignalGenerator.getInstance().showToast("Upload not completed", 400);
                    SignalGenerator.getInstance().vibrate(100);
                }

            }
        });


        return rootView;
    }

    private void initCategorySpinner() {
        String[] category_items = new String[]{"Vegetarian", "Vegan", "Italian", "Gluetn free", "Asian", "Japanese", "American", "Spanish", "Mexican", "Healthy"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, category_items);
        share_recipe_SPN_category.setAdapter(adapter);
        share_recipe_SPN_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                addTag(selectedItem);
                Log.d("selected", selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initFlavorSpinner() {
        String[] flavor_items = new String[]{"Sweet", "Sour", "Salty", "Bitter", "Spicy"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, flavor_items);
        share_recipe_SPN_flavor.setAdapter(adapter);
        share_recipe_SPN_flavor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                addTag(selectedItem);
                Log.d("selected", selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initGeneralSpinner() {
        String[] general_items = new String[]{"Natural", "Organic", "Healthy food", "Fresh", "HOT food", "COLD food"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, general_items);
        share_recipe_SPN_type.setAdapter(adapter);
        share_recipe_SPN_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                addTag(selectedItem);
                Log.d("selected", selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void findViews(View rootView) {
        share_recipe_EDIT_name = rootView.findViewById(R.id.share_recipe_EDIT_name);
        share_recipe_EDIT_instructions = rootView.findViewById(R.id.share_recipe_EDIT_instructions);
        share_recipe_EDIT_tags = rootView.findViewById(R.id.share_recipe_EDIT_tags);
        share_recipe_EDIT_tags.setInputType(InputType.TYPE_NULL);

        share_recipe_PICK_persons = rootView.findViewById(R.id.share_recipe_PICK_persons);
        share_recipe_PICK_time = rootView.findViewById(R.id.share_recipe_PICK_time);

        share_recipe_BTN_uploadphotos = rootView.findViewById(R.id.share_recipe_BTN_uploadphotos);
        share_recipe_BTN_share = rootView.findViewById(R.id.share_recipe_BTN_share);

        share_recipe_IMGBTN_imgbtn1 = rootView.findViewById(R.id.share_recipe_IMGBTN_imgbtn1);

        share_recipe_SPN_category = rootView.findViewById(R.id.share_recipe_SPN_category);
        share_recipe_SPN_flavor = rootView.findViewById(R.id.share_recipe_SPN_flavor);
        share_recipe_SPN_type = rootView.findViewById(R.id.share_recipe_SPN_type);
    }

    private void cleanBoxes() {
        share_recipe_EDIT_name.setText("");
        share_recipe_EDIT_instructions.setText("");
        share_recipe_EDIT_tags.setText("");
        share_recipe_PICK_persons.setValue(1);
        share_recipe_PICK_time.setValue(1);
        share_recipe_IMGBTN_imgbtn1.setImageURI(null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryCode && resultCode == RESULT_OK) {
            imageUrl = data.getData();
            share_recipe_IMGBTN_imgbtn1.setImageURI(imageUrl);
        }
    }

    private void uploadImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
//                startActivity(intent);
        startActivityForResult(intent, GalleryCode);
    }

    private void addTag(String tag) {
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
        } else {
            selectedTags.add(tag);
            String tags = share_recipe_EDIT_tags.getText().toString();
            if (!TextUtils.isEmpty(tags)) {
                tags += ", ";
            }
            tags += tag;
            share_recipe_EDIT_tags.setText(tags);
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
//        button.setOnClickListener(v -> addTag(text, button));
        return button;
    }

    private int dpToPx(Context context, int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}

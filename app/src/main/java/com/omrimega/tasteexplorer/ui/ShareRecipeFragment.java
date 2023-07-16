package com.omrimega.tasteexplorer.ui;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.omrimega.tasteexplorer.R;
import com.omrimega.tasteexplorer.RecipeUploadCallback;
import com.omrimega.tasteexplorer.utilities.CustomSpinner;
import com.omrimega.tasteexplorer.utilities.DataManager;
import com.omrimega.tasteexplorer.utilities.SignalGenerator;

import java.util.ArrayList;
import java.util.List;

public class ShareRecipeFragment extends Fragment implements View.OnClickListener, RecipeUploadCallback {

    private EditText share_recipe_EDIT_name, share_recipe_EDIT_instructions, share_recipe_EDIT_tags;
    private NumberPicker share_recipe_PICK_time, share_recipe_PICK_persons;
    private Button share_recipe_BTN_uploadphotos, share_recipe_BTN_share, share_recipe_BTN_resetForm;
    private ImageButton share_recipe_IMGBTN_imgbtn1;
    private CustomSpinner share_recipe_SPN_category, share_recipe_SPN_type, share_recipe_SPN_flavor;
    private ImageView share_recipe_IMG_deleteTags;
    private static final int GalleryCode = 1;
    private Uri imageUrl = null;
    private List<String> selectedTags;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_share_recipe, container, false);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        findViews(rootView);
        selectedTags = new ArrayList<>();

        initCategorySpinner();
        initGeneralSpinner();
        initFlavorSpinner();

        share_recipe_PICK_time.setMinValue(1);
        share_recipe_PICK_time.setMaxValue(1000);

        share_recipe_PICK_persons.setMinValue(1);
        share_recipe_PICK_persons.setMaxValue(30);
        share_recipe_BTN_uploadphotos.setOnClickListener(v -> uploadImage());
        share_recipe_BTN_share.setOnClickListener(v -> {
            String title = share_recipe_EDIT_name.getText().toString().trim();
            String instructions = share_recipe_EDIT_instructions.getText().toString().trim();
            String tags = share_recipe_EDIT_tags.getText().toString().trim();
            int time = share_recipe_PICK_time.getValue();
            int persons = share_recipe_PICK_persons.getValue();

            if (!title.isEmpty() && !instructions.isEmpty() && time != 0 && persons != 0 && imageUrl != null) {
                ProgressDialog dialog = ProgressDialog.show(getContext(), "", "Uploading...", true);
                dialog.show();
                DataManager.getInstance().uploadRecipe(imageUrl, title, instructions, tags, time, persons, dialog, this);
                cleanBoxes();
            } else {
                SignalGenerator.getInstance().showToast("Check your inputs!", 1500);
            }

        });

        share_recipe_IMG_deleteTags.setOnClickListener(v -> {
            share_recipe_EDIT_tags.setText("");
            selectedTags.clear();
        });

        share_recipe_BTN_resetForm.setOnClickListener(v -> cleanBoxes());

        return rootView;
    }

    @Override
    public void onRecipeUploadSuccess(ProgressDialog dialog) {
        SignalGenerator.getInstance().showToast("Sharing is caring!", 800);
    }

    @Override
    public void onRecipeUploadFailure(Exception exception) {
        SignalGenerator.getInstance().showToast("Upload not completed", 800);
        SignalGenerator.getInstance().vibrate(100);
    }

    private void initCategorySpinner() {
        String[] category_items = new String[]{"None", "Vegetarian", "Vegan", "Italian", "Gluetn free", "Asian", "Japanese", "American", "Spanish", "Mexican", "Healthy"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, category_items);
        share_recipe_SPN_category.setAdapter(adapter);
        share_recipe_SPN_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    return;
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
        String[] flavor_items = new String[]{"None", "Sweet", "Sour", "Salty", "Bitter", "Spicy"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, flavor_items);
        share_recipe_SPN_flavor.setAdapter(adapter);
        share_recipe_SPN_flavor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    return;
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
        String[] general_items = new String[]{"None", "Natural", "Organic", "Healthy food", "Fresh", "HOT food", "COLD food"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, general_items);
        share_recipe_SPN_type.setAdapter(adapter);
        share_recipe_SPN_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    return;
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

        share_recipe_PICK_persons = rootView.findViewById(R.id.share_recipe_PICK_persons);
        share_recipe_PICK_time = rootView.findViewById(R.id.share_recipe_PICK_time);

        share_recipe_BTN_uploadphotos = rootView.findViewById(R.id.share_recipe_BTN_uploadphotos);
        share_recipe_BTN_share = rootView.findViewById(R.id.share_recipe_BTN_share);

        share_recipe_IMGBTN_imgbtn1 = rootView.findViewById(R.id.share_recipe_IMGBTN_imgbtn1);

        share_recipe_SPN_category = rootView.findViewById(R.id.share_recipe_SPN_category);
        share_recipe_SPN_flavor = rootView.findViewById(R.id.share_recipe_SPN_flavor);
        share_recipe_SPN_type = rootView.findViewById(R.id.share_recipe_SPN_type);

        share_recipe_IMG_deleteTags = rootView.findViewById(R.id.share_recipe_IMG_deleteTags);

        share_recipe_BTN_resetForm = rootView.findViewById(R.id.share_recipe_BTN_resetForm);
    }

    private void cleanBoxes() {
        share_recipe_EDIT_name.setText("");
        share_recipe_EDIT_instructions.setText("");
        share_recipe_EDIT_tags.setText("");
        share_recipe_PICK_persons.setValue(1);
        share_recipe_PICK_time.setValue(1);
        share_recipe_IMGBTN_imgbtn1.setImageURI(null);
        selectedTags.clear();
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

    @Override
    public void onClick(View v) {

    }
}

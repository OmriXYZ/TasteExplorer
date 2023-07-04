package com.omrimega.tasteexplorer.ui.slideshow;

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

import androidx.fragment.app.Fragment;

import com.omrimega.tasteexplorer.R;
import com.omrimega.tasteexplorer.utilities.CustomSpinner;

import java.util.ArrayList;
import java.util.List;

public class FindRecipeFragment extends Fragment {

    private CustomSpinner find_recipe_SPN_category, find_recipe_SPN_type, find_recipe_SPN_flavor;
    private EditText find_recipe_EDIT_tags;
    private Button find_recipe_BTN_find;
    private List<String> selectedTags;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_find_recipe, container, false);

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        findViews(rootView);

        initCategorySpinner();
        initGeneralSpinner();
        initFlavorSpinner();

        selectedTags = new ArrayList<>();

        find_recipe_BTN_find.setOnClickListener(v -> {

        });

        return rootView;
    }

    private void findViews(View rootView) {
        find_recipe_SPN_category = rootView.findViewById(R.id.find_recipe_SPN_category);
        find_recipe_SPN_flavor = rootView.findViewById(R.id.find_recipe_SPN_flavor);
        find_recipe_SPN_type = rootView.findViewById(R.id.find_recipe_SPN_type);
        find_recipe_EDIT_tags = rootView.findViewById(R.id.find_recipe_EDIT_tags);
        find_recipe_BTN_find = rootView.findViewById(R.id.find_recipe_BTN_find);
    }

    private void initCategorySpinner() {
        String[] category_items = new String[]{"Vegetarian", "Vegan", "Italian", "Gluetn free", "Asian", "Japanese", "American", "Spanish", "Mexican", "Healthy"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, category_items);
        find_recipe_SPN_category.setAdapter(adapter);
        find_recipe_SPN_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        find_recipe_SPN_flavor.setAdapter(adapter);
        find_recipe_SPN_flavor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        find_recipe_SPN_type.setAdapter(adapter);
        find_recipe_SPN_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
    private void addTag(String tag) {
        if (selectedTags.contains(tag)) {
            selectedTags.remove(tag);
            String tags = find_recipe_EDIT_tags.getText().toString();
            if (tags.contains(tag + ", ")) {
                tags = tags.replace(tag + ", ", "");
            } else if (tags.contains(", " + tag)) {
                tags = tags.replace(", " + tag, "");
            } else if (tags.contains(tag)) {
                tags = tags.replace(tag, "");
            }
            find_recipe_EDIT_tags.setText(tags);
        } else {
            selectedTags.add(tag);
            String tags = find_recipe_EDIT_tags.getText().toString();
            if (!TextUtils.isEmpty(tags)) {
                tags += ", ";
            }
            tags += tag;
            find_recipe_EDIT_tags.setText(tags);
        }
    }
}
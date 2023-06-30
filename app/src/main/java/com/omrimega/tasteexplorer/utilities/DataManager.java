package com.omrimega.tasteexplorer.utilities;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class DataManager {
    private static DataManager instance = null;
    private Context context;

    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;
    private FirebaseStorage mStorage;


    private DataManager(Context context) {
        this.context = context;
        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference().child("Recipes");
        mStorage = FirebaseStorage.getInstance();
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new DataManager(context);
        }
    }
}

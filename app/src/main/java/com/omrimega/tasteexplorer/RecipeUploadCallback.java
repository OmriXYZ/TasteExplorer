package com.omrimega.tasteexplorer;

import android.app.ProgressDialog;

public interface RecipeUploadCallback {
    void onRecipeUploadSuccess(ProgressDialog dialog);
    void onRecipeUploadFailure(Exception exception);
}

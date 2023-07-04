package com.omrimega.tasteexplorer;

import android.app.Application;

import com.omrimega.tasteexplorer.utilities.DataManager;
import com.omrimega.tasteexplorer.utilities.SignalGenerator;

public class App extends Application {

        @Override
        public void onCreate() {
            super.onCreate();
            DataManager.init(this);
            SignalGenerator.init(this);
        }
}

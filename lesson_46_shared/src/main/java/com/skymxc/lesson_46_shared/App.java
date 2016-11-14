package com.skymxc.lesson_46_shared;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.skymxc.lesson_46_shared.entity.User;

/**
 * Created by sky-mxc
 */
public class App extends Application {
    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        Configuration configuration = new Configuration.Builder(this)
                .setDatabaseName("sky.DB")
                .setDatabaseVersion(1)
                .addModelClass(User.class)
                .create();
        ActiveAndroid.initialize(configuration);
    }
}

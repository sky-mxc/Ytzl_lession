package com.skymxc.demo.lession_19_animation.practice.util;

import android.app.Application;
import android.util.Log;

/**
 * Created by sky-mxc
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("App","=======onCreate()=====");
        DBHelper.init(this);
    }
}

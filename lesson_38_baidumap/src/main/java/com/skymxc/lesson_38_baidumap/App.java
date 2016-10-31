package com.skymxc.lesson_38_baidumap;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by sky-mxc
 */
public class App extends Application {
    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
    }
}

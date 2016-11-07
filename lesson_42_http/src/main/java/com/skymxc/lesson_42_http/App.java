package com.skymxc.lesson_42_http;

import android.app.Application;

import org.xutils.x;

/**
 * Created by sky-mxc
 */
public class App extends Application {
    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化
        x.Ext.init(this);
    }
}

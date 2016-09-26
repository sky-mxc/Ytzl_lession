package com.skymxc.demo.lesson_25_xutil;

import android.app.Application;

import org.xutils.x;

/**
 * Created by sky-mxc
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化xutils
        x.Ext.init(this);
        x.Ext.setDebug(true);//log  日志
    }
}

package com.skymxc.demo.lesson_21_activeandroid_db.practice;

import android.app.Application;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.skymxc.demo.lesson_21_activeandroid_db.practice.entity.Score;
import com.skymxc.demo.lesson_21_activeandroid_db.practice.entity.Teacher;

/**
 * Created by sky-mxc
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * 对activeAndroid进行初始化
         */
        Configuration configuration = new Configuration.Builder(this)
                .setDatabaseName("sky.DB")
                .setDatabaseVersion(1)
//                .addModelClass(Teacher.class)
//                .addModelClass(Score.class)
                .addModelClasses(Teacher.class,Score.class)
                .create();
        Log.e("TAG","======onCreate=====");
        ActiveAndroid.initialize(configuration);
    }
}

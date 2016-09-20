package com.skymxc.demo.lesson_21_activeandroid_db;

import android.app.Application;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.skymxc.demo.lesson_21_activeandroid_db.entity.Student;

/**
 * Created by sky-mxc
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("App","=====onCreate=======");
        //activeAndroid配置
        Configuration configuration = new Configuration.Builder(this)
                .setDatabaseName("sky.DB")                 //db name    have default
                .setDatabaseVersion(1)                    //db version must
                .addModelClasses(Student.class)           //增加 实例  must
                .create();
        ActiveAndroid.initialize(configuration);        //初始化
    }
}

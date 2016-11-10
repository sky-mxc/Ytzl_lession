package com.skymxc.lesson_45_jpush;

import android.support.v7.app.AppCompatActivity;

import cn.jpush.android.api.JPushInterface;

/**
 * 对应用程序使用频率的统计功能
 */
public class BaseActivity extends AppCompatActivity {

    //

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base);
//    }

    @Override
    protected void onPause() {
        super.onPause();
        //必须使用 application Context
        JPushInterface.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }
}

package com.skymxc.demo.lesson_26_actionbar;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SecondActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("sky-mxc");
        actionBar.setBackgroundDrawable(getDrawable(R.drawable.shape_actionbar_bg)); //ActionBar背景
        actionBar.setDisplayHomeAsUpEnabled(true);      //返回键是否显示
    }
}

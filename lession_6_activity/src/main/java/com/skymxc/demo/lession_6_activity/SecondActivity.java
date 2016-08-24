package com.skymxc.demo.lession_6_activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by sky-mxc
 * Date : 2016/8/24
 */
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取启动这个活动的intent
        Intent intent= getIntent();
        //取出 存入的值 参数以是存入时的key 参数2 是 默认值  只有 取出的是 基本数据类型才有默认值
        boolean flag =   intent.getBooleanExtra("use",false);

        setTheme(flag?R.style.AppTheme_Transparent:R.style.AppTheme);

        setContentView(R.layout.activity_second);
    }
}

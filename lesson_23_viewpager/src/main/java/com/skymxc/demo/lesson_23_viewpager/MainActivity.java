package com.skymxc.demo.lesson_23_viewpager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;

    private int [] images = new int[]{R.mipmap.a,R.mipmap.d,R.mipmap.f,R.mipmap.g,R.mipmap.h};

    private  MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        pager = (ViewPager) findViewById(R.id.pager);
        myAdapter = new MyAdapter(images,this);
        pager.setAdapter(myAdapter);
    }
}

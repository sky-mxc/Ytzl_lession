package com.skymxc.demo.lesson_23_viewpager.practice;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.skymxc.demo.lesson_23_viewpager.R;

public class MainActivity extends AppCompatActivity {

    /**
     * 住activity ：fragment容器，下面：RadioGroup
     * 新闻Fragment ：viewPager{Fragment}
     * 实体类：result  ：News
     * Volley ：HTTP帮助类（单例）
     * 执行网络请求 加载 ，显示
     * @param
     */

    private NewsContainerFragment newsContainerFragment;
    private FragmentManager manager;
    private SecondFragmnet secondFragmnet;
    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        init();
    }

    private void init() {
        rg = (RadioGroup) findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(onCheckedChangeListener);
        newsContainerFragment = new NewsContainerFragment();

        secondFragmnet = new SecondFragmnet();
        manager= getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.container,newsContainerFragment)
                .add(R.id.container,secondFragmnet)
                .hide(secondFragmnet)
                .commit();
    }

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.news:
                    manager.beginTransaction()
                            .show(newsContainerFragment)
                            .hide(secondFragmnet)
                            .commit();
                    break;
                case R.id.page2:
                    manager.beginTransaction()
                            .show(secondFragmnet)
                            .hide(newsContainerFragment)
                            .commit();
                    break;
            }
        }
    };


}

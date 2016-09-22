package com.skymxc.demo.lesson_23_viewpager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sky-mxc
 */

public class ThirdActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG ="ThirdActivity";

    private ViewPager pager;
    private List<Fragment> fragments;
    private Button btTrue;
    private Button btFalse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = (ViewPager) findViewById(R.id.pager);
        btFalse = (Button) findViewById(R.id.smooth_false);
        btTrue = (Button) findViewById(R.id.smooth_true);
        btTrue.setOnClickListener(this);
        btFalse.setOnClickListener(this);
        fragments = new ArrayList<>();
        fragments.add(FirstFragment.getFragment(Color.RED,"0"));
        fragments.add(FirstFragment.getFragment(Color.GRAY,"1"));
        fragments.add(FirstFragment.getFragment(Color.GREEN,"2"));
        fragments.add(FirstFragment.getFragment(Color.DKGRAY,"3"));
        fragments.add(FirstFragment.getFragment(Color.BLUE,"4"));
        fragments.add(FirstFragment.getFragment(Color.YELLOW,"5"));
        MyFragmentAdapter fragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager());
        pager.setAdapter(fragmentAdapter);
        pager.addOnPageChangeListener(onPageChangeListener);
        //改变页面预加载数量 最少是1不可改变 即使改为0也是1 源码规定
        pager.setOffscreenPageLimit(2); //前后各两个
        //设置显示第几项
      //  pager.setCurrentItem(4,true);
    }

    private ViewPager.OnPageChangeListener onPageChangeListener =new ViewPager.OnPageChangeListener() {

        /**
         * 当页面滚动 持续调用
         * @param position
         * @param positionOffset
         * @param positionOffsetPixels
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                log("=onPageScrolled====position:"+position+"====positionOffset:"+positionOffset+"====positionOffsetPixels:"+positionOffsetPixels);
        }

        /**
         * 当页面被选中
         * @param position 被选中page的下标
         */
        @Override
        public void onPageSelected(int position) {

        }

        /**
         * 当页面滚动状态改变
         * @param state
         */
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.smooth_false:
                pager.setCurrentItem(0,false);
                break;
            case R.id.smooth_true:
                pager.setCurrentItem(0,true);
                break;
        }
    }

    class  MyFragmentAdapter extends FragmentPagerAdapter{

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    public void log(String string){
        Log.e(TAG,"==="+string+"=");
    }

}

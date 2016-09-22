package com.skymxc.demo.lesson_23_viewpager;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private ViewPager pager;
    private List<BasePager> pagers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        pager = (ViewPager) findViewById(R.id.pager);
        pagers = new ArrayList<>();
        pagers.add(new FirstPager(this,R.layout.layout_first_pager));
        pagers.add(new FirstPager(this,R.layout.layout_first_pager));
   //     pagers.add(new SecondPager(this,R.layout.layout_second_pager));
        MyPagerAdapter adapter = new MyPagerAdapter();
        pager.setAdapter(adapter);
    }

    class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return pagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pagers.get(position).getView());
            return pagers.get(position).getView();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}

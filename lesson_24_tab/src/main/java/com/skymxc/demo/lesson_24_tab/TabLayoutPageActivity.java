package com.skymxc.demo.lesson_24_tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sky-mxc
 */

public class TabLayoutPageActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager pager;
    private List<Fragment> fragments;
    private List<String> titles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        pager = (ViewPager) findViewById(R.id.pager);
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        titles.add("消息");
        titles.add("联系人");
        fragments.add( new MsgFragment());
        fragments.add(new ContactsFragment());
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        //tabLayout.setupWithViewPager(pager);

        tabLayout.addTab(tabLayout.newTab().setCustomView(createTab(R.drawable.selector_tab_msg,"消息")));
        tabLayout.addTab(tabLayout.newTab().setText("联系人"));
        tabLayout.setSelectedTabIndicatorHeight(0);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addOnTabSelectedListener(tabSelectedListener);
        pager.addOnPageChangeListener(onPageChangeListener);
    }

    private TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {

        /**
         * 标签选中时
         * @param tab 被选中标签项
         */
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
        pager.setCurrentItem(tab.getPosition(),true);
        }

        /**
         * 当标签变为不选中时调用
         * @param tab 取消选中的那个标签
         */
        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        /**
         * 重复选中时的调用
         * @param tab 被重复选中的标签
         */
        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };


    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Log.e("TAG","====onPageSelected============");
            tabLayout.getTabAt(position).select();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public View createTab(int icon, String tab){
        View view = getLayoutInflater().inflate(R.layout.layout_tab,null);
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);
        TextView textView = (TextView) view.findViewById(R.id.tab);
        iconView.setImageResource(icon);
        textView.setText(tab);
        return  view;
    }

    class MyAdapter  extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
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

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}

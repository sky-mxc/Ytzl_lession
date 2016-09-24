package com.skymxc.demo.lesson_24_tab.practice.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.skymxc.demo.lesson_24_tab.practice.entity.Tab;
import com.skymxc.demo.lesson_24_tab.practice.fragment.NewsListFragment;

import java.util.List;

/**
 * Created by sky-mxc
 */

public class NewsListFragmentAdapter extends FragmentPagerAdapter {
    private List<NewsListFragment > fragments;
    public NewsListFragmentAdapter(FragmentManager fm , List<NewsListFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public NewsListFragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments==null?0:fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ((Tab)fragments.get(position).getArguments().get("tab")).getName();
    }
}

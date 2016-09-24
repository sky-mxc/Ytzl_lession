package com.skymxc.demo.lesson_24_tab.practice.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.skymxc.demo.lesson_24_tab.practice.fragment.NewsFragment;

import java.util.List;

/**
 * Created by sky-mxc
 *
 */

public class NewsFragmentAdapter extends FragmentPagerAdapter {

    private List<NewsFragment> fragments;

    public NewsFragmentAdapter(FragmentManager fm,List<NewsFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public NewsFragment getItem(int position) {
        Log.e("NewsFragmentAdapter","===getItem:"+position);
        return fragments.get(position);
    }

    @Override
    public int getCount() {
       // Log.e("NewsFragmentAdapter","======getCount:"+fragments.size());
        return fragments ==null?0:fragments.size();
    }
}

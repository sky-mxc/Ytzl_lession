package com.skymxc.demo.lesson_23_viewpager.practice;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

/**
 * Created by sky-mxc
 */

public class NewsAdapter extends FragmentPagerAdapter {

    private List<NewsFragment> newsFragments;



    public NewsAdapter(FragmentManager fm, List<NewsFragment> newsFragments) {
        super(fm);
        this.newsFragments = newsFragments;
        Log.e("NewsAdapter","======size:="+newsFragments);
    }

    @Override
    public Fragment getItem(int position) {
        return newsFragments.get(position);
    }

    @Override
    public int getCount() {
        return newsFragments==null?0:newsFragments.size();
    }
}

package com.skymxc.demo.lesson_23_viewpager.practice;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.skymxc.demo.lesson_23_viewpager.R;
import com.skymxc.demo.lesson_23_viewpager.practice.entity.News;
import com.skymxc.demo.lesson_23_viewpager.practice.entity.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sky-mxc
 * 新闻页面
 */

public class NewsContainerFragment extends Fragment {

    private static final String TAG ="NewsContainerFragment";

    private View view ;
    private ViewPager pager;
    private List<News> newses;
    private List<NewsFragment> fragments;
    private NewsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view ==null){
            view = inflater.inflate(R.layout.layout_fragment_news_container,null);
            pager = (ViewPager) view.findViewById(R.id.news_container);
        }
        //page 改变监听
       pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

           }

           @Override
           public void onPageSelected(int position) {

           }

           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //execute httprequest, load data  bind Fragmentpageadapter
        loadNews();

    }

    /**
     *  bind news and Fragment
     */
    private void createNewsFragment() {
        if (newses!=null && newses.size()>0){
            /**
             *  create Fragment ,create FragmentPagerAdapter bind viewpager
             */
            fragments = new ArrayList<>();
            for (News news :newses){
                NewsFragment newsFragment = new NewsFragment();
                newsFragment.getArguments().putString("url",news.thumb);
                newsFragment.getArguments().putString("title",news.title);
                fragments.add(newsFragment);
            }
            log("====fragments  size:"+fragments.size());

        }
    }

    /**
     * execute httpRequest load news
     */
    private void loadNews() {
        String url ="http://mapi.univs.cn/mobile/index.php?app=mobile&type=mobile&controller=content&action=slide&catid=11&time=";
        HttpHelper.getInstance(getActivity())
                .executeRequest(url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (!TextUtils.isEmpty(s)){
                            Result<List<News>> result=  JSON.parseObject(s,new TypeReference<Result<List<News>>>(){});
                            Log.e("NewsContainerFragment","=news:==size:"+result.data.size());
                            newses = result.data;
                            createNewsFragment();
                            adapter = new NewsAdapter(getActivity().getSupportFragmentManager(),fragments);
                            pager.setAdapter(adapter);
                        }else{
                            Toast.makeText(getActivity(),"网络异常，稍后重试+s:"+s,Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity(),"网络异常，稍后重试",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void log(String text){
        Log.e(TAG,"====="+text+"======");
    }
}

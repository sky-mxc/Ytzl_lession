package com.skymxc.demo.lesson_24_tab.practice.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.skymxc.demo.lesson_24_tab.R;
import com.skymxc.demo.lesson_24_tab.practice.adapter.NewsAdapter;
import com.skymxc.demo.lesson_24_tab.practice.adapter.NewsFragmentAdapter;
import com.skymxc.demo.lesson_24_tab.practice.entity.News;
import com.skymxc.demo.lesson_24_tab.practice.entity.Result;
import com.skymxc.demo.lesson_24_tab.practice.entity.Tab;
import com.skymxc.demo.lesson_24_tab.practice.network.HttpHelper;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by sky-mxc
 * 显示新闻列表和滑动新闻
 */

public class NewsListFragment extends Fragment {

    private static final String TAG ="NewsListFragment";

    private View view;
    private Tab tab ;
    private ListView listView;
    private ViewPager pager;
    private TextView tvTitle;
    private List<NewsFragment > fragments;
    private NewsFragmentAdapter adapter;
    private List<News> newses;
    private NewsAdapter listadapter;

    public NewsListFragment(){
        setArguments(new Bundle());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null){
            view =  inflater.inflate(R.layout.layout_practice_fragment_news_list,null);
            listView = (ListView) view.findViewById(R.id.news_list);
            pager = (ViewPager) view.findViewById(R.id.news_sub_pager);
            tvTitle = (TextView) view.findViewById(R.id.title);
            tvTitle.setText(((Tab)getArguments().getSerializable("tab")).getName());
            tab = (Tab) getArguments().getSerializable("tab");
            pager.addOnPageChangeListener(onPageChangeListener);
        }
        return view;
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            News news = newses.get(position);
            tvTitle.setText(news.getTitle());
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onStart() {
        super.onStart();
        /**
         * TODO 加载 新闻滑动列表
         * TODO 加载 新闻列表
         * TODO 创建 pager适配器 绑定新闻滑动列表
         * TODO 创建 BaseAdapter 适配器 绑定ListView
         */

        loadNews();
        LoadNewsList();
    }

    /**
     * 加载新闻
     */
    private void loadNews() {
        String url ="http://mapi.univs.cn/mobile/index.php?controller=content&action=slide&catid="+tab.getCatid()+"&app=mobile";
        HttpHelper.getInstance(getActivity()).executeStringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e(TAG,"==json:"+s);
                if (!TextUtils.isEmpty(s)){
                    //转换
                    Result<List<News>> result = JSON.parseObject(s,new TypeReference<Result<List<News>>>(){});
                    if (result.isState()){
                        newses= result.getData();
                       bindPager(result.getData());
                    }else{
                        Toast.makeText(getActivity(),result.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(),"网络错误，请稍后重试",Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(),"网络错误，请稍后重试",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 滑动新闻绑定 pager
     * @param data
     */
    private void bindPager(List<News> data) {
        if (data!=null && data.size()>0){
            fragments = new ArrayList<>();
            for (News news :data){
                NewsFragment fragment = new NewsFragment();
                Log.e(TAG,"===thumb:"+news.getThumb());
                fragment.getArguments().putString("url",news.getThumb());
                fragments.add(fragment);
            }
        }
        Log.e(TAG,"==fragments size:"+fragments.size());
        //绑定适配器
        if (fragments!=null){
            adapter = new NewsFragmentAdapter(getActivity().getSupportFragmentManager(),fragments);
            pager.setAdapter(adapter);
        }
        tvTitle.setText(data.get(0).getTitle());
    }

    /**
     * 加载下拉新闻
     */
    private void LoadNewsList(){
        String url ="http://mapi.univs.cn/mobile/index.php?controller=content&action=index&page=1&app=mobile&catid="+tab.getCatid();
        HttpHelper.getInstance(getActivity()).executeStringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e(TAG,"==json:"+s);
                if (!TextUtils.isEmpty(s)){
                    //转换
                    Result<List<News>> result = JSON.parseObject(s,new TypeReference<Result<List<News>>>(){});
                    if (result.isState()){

                        //TODO 绑定ListView
                        listadapter = new NewsAdapter(getActivity(),result.getData());
                        listView.setAdapter(listadapter);
                    }else{
                        Toast.makeText(getActivity(),result.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(),"网络错误，请稍后重试",Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(),"网络错误，请稍后重试",Toast.LENGTH_SHORT).show();
            }
        });

    }


}

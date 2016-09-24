package com.skymxc.demo.lesson_24_tab.practice;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.skymxc.demo.lesson_24_tab.R;
import com.skymxc.demo.lesson_24_tab.practice.adapter.NewsListFragmentAdapter;
import com.skymxc.demo.lesson_24_tab.practice.entity.Result;
import com.skymxc.demo.lesson_24_tab.practice.entity.Tab;
import com.skymxc.demo.lesson_24_tab.practice.fragment.NewsListFragment;
import com.skymxc.demo.lesson_24_tab.practice.network.HttpHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "practice.MainActivity";

    private TabLayout tabLayout;
    private ViewPager pager;
    private List<Tab> tabs;
    private List<NewsListFragment> newsListFragments;
    private NewsListFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_main);
        initView();
        loadTabs();

    }

    private void loadTabs() {
        /**
         * =========加载tab
         */
        String url ="http://mapi.univs.cn/mobile/index.php?controller=content&action=category&app=mobile";
        HttpHelper.getInstance(this).executeStringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e(TAG,"==json:"+s);
                if (!TextUtils.isEmpty(s)){
                    //转换
                    Result<List<Tab>> result = JSON.parseObject(s,new TypeReference<Result<List<Tab>>>(){});
                    if (result.isState()){
                        tabs= result.getData();
                        //绑定tablayout
                        bindTablayout();
                    }else{
                        Toast.makeText(MainActivity.this,result.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this,"网络错误，请稍后重试",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this,"网络错误，请稍后重试",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 绑定tablayout 并创建fragment 集合
     */
    private void bindTablayout() {
        tabLayout.setupWithViewPager(pager);
        if (tabs!=null&& tabs.size()>0){
            newsListFragments = new ArrayList<>();
            for (Tab tab :tabs){
                //添加到tablayout 无暖用
           //     tabLayout.addTab(tabLayout.newTab().setText(tab.getName()));
                //TODO 创建 Fragment集合 适配器 绑定 viewPager
                NewsListFragment newsListFragment = new NewsListFragment();
                newsListFragment.getArguments().putSerializable("tab",tab);
                newsListFragments.add(newsListFragment);
            }
        }
        //绑定到pager上
     bindPager();
    }

    /**
     * 将fragment 绑定到viewPager上
     */
    private void bindPager() {
        adapter = new NewsListFragmentAdapter(getSupportFragmentManager(),newsListFragments);
        pager.setAdapter(adapter);
    }


    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.news_pager);
        tabs = new ArrayList<>();
        tabLayout.setBackgroundColor(Color.GRAY);
        tabLayout.setSelectedTabIndicatorColor(Color.YELLOW);

    }


}

package com.skymxc.demo.lesson_34_refreshframework;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity tag";

    private SwipeRefreshLayout refreshLayout;
    private ListView lv;
    private List<String> items;
    private  ArrayAdapter<String> adapter;
    private FrameLayout frameLayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        initData();
        initRefresh();
        lv.setOnScrollListener(scrollListener);

    }

    //滑动监听
    private AbsListView.OnScrollListener scrollListener =new AbsListView.OnScrollListener() {
        /**
         * 滚动状态改变
         * 开始滑动，停止滑动,fling
         * @param view
         * @param scrollState 滑动 1 冲击2 ，停止0
         */
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

            Log.i(TAG, "onScrollStateChanged: scrollState="+scrollState+";====getScaleY="+lv.getScaleY()+"==== lv.getScrollY()="+ lv.getScrollY());

            if (scrollState==0){    //滑动停止
                //最后一个显示项的下标
              int lastPosition=  lv.getLastVisiblePosition();
                //已经到最后一行
                if (lastPosition ==lv.getCount()-1){
                    //获取 ListView中子项的最后一下项
                   View v = lv.getChildAt(lv.getChildCount()-1);
                    //最后一项的底边如果跟 ListView重合 就说明 现在已经处于最底部
                    if (v.getBottom()==(lv.getHeight()-lv.getDividerHeight())){
                        onLoadMore();
                    }
                    int firstPosition = lv.getFirstVisiblePosition();
                    //第一项
                    if (firstPosition == 0){
                        View firstView = lv.getChildAt(0);
                        if (firstView.getTop() == 0){
                            refreshLayout.setEnabled(true);
                        }
                    }
                }else{
                    //禁用
                    refreshLayout.setEnabled(false);
                }
            }



        }

        /**
         * 滚动 时
         * @param view
         * @param firstVisibleItem 第一个显示项下标
         * @param visibleItemCount 显示项的总数
         * @param totalItemCount 总的数据量
         */
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            Log.i(TAG, "onScroll: ");
        }
    };

    /**
     * 加载更多
     */
    private void onLoadMore() {
        frameLayout.setVisibility(View.VISIBLE);
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {

                adapter.addAll(items);
            }
        },4000);
    }


    private void initView() {
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        lv = (ListView) findViewById(R.id.list_view);
        frameLayout = (FrameLayout) findViewById(R.id.more);

    }

    private void initRefresh() {
        refreshLayout.setColorSchemeColors(Color.RED,Color.YELLOW,Color.GREEN,Color.BLUE);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //模仿演示操作
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //隐藏刷新的指示标志
                        refreshLayout.setRefreshing(false);
                      //  adapter.clear();
                      //  adapter.addAll("refresh item","refresh item1","refresh item2");
                        adapter.addAll(items);
                    //    adapter.notifyDataSetChanged();
                    }
                },4000);
            }
        });

    }

    private void initData() {
        items = new ArrayList<>();
        for (int i=0;i<20;i++){
            items.add(new String("小_"+i));
        }
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,items);

        lv.setAdapter(adapter);
    }
}

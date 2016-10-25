package com.skymxc.demo.lesson_34_refreshframework;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrHandler2;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by sky-mxc
 */
public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "SecondActivity";

    private PtrClassicFrameLayout ptrFrameLayout;
    private ListView lv;

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initView();
        //设置一个handler
        ptrFrameLayout.setPtrHandler(ptrHander2);
        //最少要加载 5s
        ptrFrameLayout.setLoadingMinTime(1000*5);
        //设置上次更新头部时间对应的key
        ptrFrameLayout.setLastUpdateTimeHeaderKey("key");

        ptrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                //自动刷新
                ptrFrameLayout.autoRefresh();
            }
        },100);



        //自动加载更多
//        ptrFrameLayout.autoLoadMore();

        //头视图
        StoreHouseHeader houseHeader = new StoreHouseHeader(this);
        //通过字符串加载 支持26个英文字母
      //  houseHeader.initWithString("loading");
        //通过坐标点加载
     //   houseHeader.initWithPointList(getPointList());
//        houseHeader.initWithStringArray(R.array.header);
        houseHeader.setTextColor(Color.RED);
        //设置头视图
        ptrFrameLayout.setHeaderView(houseHeader);
        //添加进行更新UI操作的对象
        ptrFrameLayout.addPtrUIHandler(houseHeader);


       }


        private PtrHandler2 ptrHander2 = new PtrHandler2() {

            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                int lastPosition = lv.getLastVisiblePosition();
                if (lastPosition == lv.getCount()-1){//最后一项
                    View lastView = lv.getChildAt(lv.getChildCount()-1);
                    if (lastView.getBottom() == lv.getHeight() || (lastView.getHeight()+lv.getDividerHeight())==lastView.getBottom()){     //最后一项为距离底部的距离为0  在最下面
                        return  true;
                    }
                }
                return false;
            }

            @Override
            public void onLoadMoreBegin(final PtrFrameLayout frame) {
                    frame.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                         frame.refreshComplete();
                        }
                    },2000);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                int firstPosition = lv.getFirstVisiblePosition();
                if (firstPosition == 0) {//第一项
                    View firstView = lv.getChildAt(0);
                    if (firstView.getTop() == 0) {     //距离顶部为 0 在最上面了
                        //可以刷新
                        return true;
                    }

                }
                return false;
            }


            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                //模式远程耗时操作
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //刷新完成 隐藏头或者脚
                        frame.refreshComplete();
                        Log.i(TAG, "run: ");
                    }
                },4000);
            }
        };

    private PtrHandler ptrHandler = new PtrHandler() {
        /**
         * 检查是否能刷新
         * @param frame
         * @param content
         * @param header
         * @return true 可以，false不可以
         */
        @Override
        public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
            Log.i(TAG, "checkCanDoRefresh: ");
            int firstPosition = lv.getFirstVisiblePosition();
            if (firstPosition == 0){//第一项
                View firstView = lv.getChildAt(0);
                if (firstView.getTop()==0){     //距离顶部为 0 在最上面了
                    //可以刷新
                    return true;
                }

            }
            return false;
        }

        /**
         * 触发了刷新
         * @param frame
         */
        @Override
        public void onRefreshBegin(final PtrFrameLayout frame) {
            Log.i(TAG, "onRefreshBegin: ");
            //模式远程耗时操作
            frame.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //刷新完成 隐藏头或者脚
                    frame.refreshComplete();
                    Log.i(TAG, "run: ");
                }
            },4000);
        }
    };

    private void initView() {
        ptrFrameLayout= (PtrClassicFrameLayout) findViewById(R.id.ptr_layout);
        lv=(ListView) findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,getData());
        lv.setAdapter(adapter);
    }

    private List<String> getData() {
        List<String> items = new ArrayList<>();
        for (int i = 0;i<20;i++){
            items.add(new String("sky-"+i));
        }
        return items;
    }


    /**
     * 获取一个 字符串的 笔画 点数组
     * @return
     */
    public ArrayList<float[]> getPointList() {
        ArrayList<float[]> pointList = new ArrayList<>();
        float[] a ={0,15,60,15};
        float[] b = {60,15,50,40};
        float[] c = {50,70,40,60};
        float [] d ={35,0,10,70};
        return pointList;
    }
}

package com.skymxc.demo.lesson_32_desgin_widget;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.skymxc.demo.lesson_32_desgin_widget.entity.Item;

import java.util.ArrayList;
import java.util.List;

public class SlidingActivity extends AppCompatActivity implements View.OnClickListener {
    
    private static final String  TAG = "SlidingActivity tag";

    private SlidingPaneLayout slidingPaneLayout;
    private RecyclerView recyclerView;
    private FrameLayout menu;
    private MyAdapter adapter;
    private CoordinatorLayout body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding);
        slidingPaneLayout= (SlidingPaneLayout) findViewById(R.id.sliding);
        menu=(FrameLayout) findViewById(R.id.menu);
        body=(CoordinatorLayout) findViewById(R.id.body);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        //设置布局管理器
        LinearLayoutManager lm = new LinearLayoutManager(this);
        //自动测量子控件尺寸   //在24之前的版本 需要些 后来默认true；针对于包裹尺寸下 LinearLayout不显示额情况
        lm.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(lm);

        adapter = new MyAdapter(this,getItems());
        recyclerView.setAdapter(adapter);

        Log.i(TAG, "onCreate: childViewCount=:"+recyclerView.getChildCount());
        Log.i(TAG, "onCreate: dataCount:"+recyclerView.getAdapter().getItemCount());


        //添加子项的装饰
        recyclerView.addItemDecoration(new MyDecoration(this));

        adapter.setOnClickListener(this);

        //覆盖色
        slidingPaneLayout.setSliderFadeColor(Color.GRAY);

        //视差距离  滑动的话 都动
       slidingPaneLayout.setParallaxDistance(300);

        //滑动的阴影
       // slidingPaneLayout.setShadowResourceLeft(R.mipmap.lan0);


        //滑动监听
        slidingPaneLayout.setPanelSlideListener(slideListener);
    }

    private SlidingPaneLayout.PanelSlideListener slideListener = new SlidingPaneLayout.PanelSlideListener() {
        /**
         * 窗格滑动
         * @param panel 
         * @param slideOffset 偏移量
         */
        @Override
        public void onPanelSlide(View panel, float slideOffset) {
            Log.e(TAG, "onPanelSlide: slideOffset="+slideOffset);

            //中心点为 X轴 紧贴
            body.setPivotX(0);
            //缩放
            body.setScaleY(1-slideOffset*0.2f);
            body.setScaleX(1-slideOffset*0.2f);

            //menu 从缩小到放大
            //整个菜单的宽端作为 缩放重心
            menu.setPivotX(menu.getWidth());
            menu.setScaleX((float) (0.8+slideOffset*0.2f));
            menu.setScaleY((float) (0.8+slideOffset*0.2f));
            menu.setAlpha(slideOffset);

        }

        /**
         * 窗格打开后
         * @param panel
         */
        @Override
        public void onPanelOpened(View panel) {
            Log.e(TAG, "onPanelOpened: ");
        }

        /**
         * 窗格关闭后
         * @param panel
         */
        @Override
        public void onPanelClosed(View panel) {
            Log.e(TAG, "onPanelClosed: ");
        }
    };

    @Override
    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.open:
//                slidingPaneLayout.openPane();
//                break;
//            case R.id.close:
//                slidingPaneLayout.closePane();
//                break;
//        }
    }

    public List<Item> getItems() {
        List<Item> items =new ArrayList<>();
        for (int i =0;i<20;i++){
            Item item = new Item();
            item.setText1("text1_"+i);
            item.setText2("text2_"+i+" "+i);
            if(i%6==0){
                Log.i(TAG,"========2");
                item.setType(2);
                item.setIcon("dj");
                item.setProgress(i*5);
            }else  if (i%5!=0){
                item.setIcon("d");
                item.setType(0);
            }else  {
                //头
                item.setText1("Group"+i);
                item.setType(1);
            }

            items.add(item);
        }
        return items;
    }
}

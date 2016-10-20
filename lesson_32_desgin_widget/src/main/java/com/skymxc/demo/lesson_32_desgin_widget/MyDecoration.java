package com.skymxc.demo.lesson_32_desgin_widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by sky-mxc
 * Recycler 子项装饰
 */
public class MyDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "MyDecoration";

    private Paint paint;
    private Drawable drawable;

    public  MyDecoration(Context context){
        this.paint = new Paint();
        this.paint.setColor(Color.RED);
        //开启抗锯齿
        this.paint.setAntiAlias(true);
        drawable = context.getResources().getDrawable(R.mipmap.mtt);
    }

    /**
     *  控制四边的边距 等等  没一项都会 调用
     * @param outRect 矩形； 四个边距
     * @param view  子项的view childView
     * @param parent
     * @param state 状态
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        Log.i(TAG, "getItemOffsets: ");
        super.getItemOffsets(outRect, view, parent, state); //要留着
        //设置边距  单位 Px像素
        outRect.set(0,0,0,10);

    }

    /**
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int count = parent.getChildCount();
        Log.i(TAG, "onDraw: count="+count);
        //绘制底边 间距条
        for (int i =0;i<count;i++){
            //拿到子项
            View child = parent.getChildAt(i);
            //获取到 子项间那条间距的 矩形
            Rect rect = new Rect(child.getLeft(),child.getBottom()+4,child.getRight(),child.getBottom()+6);

            //绘制矩形
            c.drawRect(rect,paint);

        }
    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int count = parent.getChildCount();
        Log.i(TAG, "onDrawOver: count="+count);
        //绘制底边 间距条
        for (int i =0;i<count;i++){
            //拿到子项
            View child = parent.getChildAt(i);
            //获取到适配器数据下标
            int position = parent.getChildAdapterPosition(child);
            //根据下标获取数据类型
              int type=  parent.getAdapter().getItemViewType(position);
            if (type==0){
                //绘制角标图标
                 drawable.setBounds(child.getLeft(),child.getTop(),child.getLeft()+32,child.getTop()+32);

            }

            drawable.draw(c);

        }
    }
}

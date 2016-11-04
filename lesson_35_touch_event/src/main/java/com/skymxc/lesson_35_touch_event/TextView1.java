package com.skymxc.lesson_35_touch_event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.TextView;

/**
 * Created by sky-mxc
 */
public class TextView1 extends TextView {
    private static final String TAG = "TextView1";

    public TextView1(Context context) {
        super(context);
    }

    public TextView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 分发事件 处理
     * @param ev
     * @return true ：被处理；false：没有被处理； super.dispatchTouchEvent(ev):继续分发；
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "dispatchTouchEvent: 事件分发 start");
//        boolean f =super.dispatchTouchEvent(ev);
//        Log.i(TAG, "dispatchTouchEvent: 事件分发 end  ;result="+f);
        return super.dispatchTouchEvent(ev);
    }


    VelocityTracker vt;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: 触摸事件 start");

        //速度测量
        vt= VelocityTracker.obtain();

        //添加要测量的事件
        vt.addMovement(event);
        //设置单位时间 ，速度最大值；参数1 单位时间1000ms;参数2 最大速度1000px/1000ms;
        vt.computeCurrentVelocity(1000,1000);

        //相对于 父容器的坐标
        float x = event.getX();
        float y =  event.getY();
        //相对于整个屏幕的坐标
        float rawX = event.getRawX();
        float rawY = event.getRawY();
       int top= ((View) getParent()).getTop();
        Log.e(TAG, "onTouchEvent: x="+x+",,,y="+y+",,,rawX="+rawX+",,,rawY="+rawY+";top="+top);

        int action = event.getAction();
        //& MotionEvent.ACTION_MASK 支持多点事件处理
        switch (action & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "onTouchEvent: ACTION_DOWN");
                //获取x轴速度
                vt.getXVelocity();
                //获取y轴速度
                vt.getYVelocity();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "onTouchEvent: ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "onTouchEvent: ACTION_UP");
                vt.recycle();
                    break;
            case MotionEvent.ACTION_CANCEL:
                Log.e(TAG, "onTouchEvent: ACTION_CANCEL");
                break;
            //多点触控专享
            case MotionEvent.ACTION_POINTER_DOWN:
                //没有返回 -1
                Log.i(TAG, "onTouchEvent: 第二个手指点击位置x="+event.getX(1)+";y="+event.getY(1));
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            
        }

//        boolean f = super.onTouchEvent(event);
//        Log.i(TAG, "onTouchEvent: 触摸事件 end;result="+f);
        return super.onTouchEvent(event);
    }


}

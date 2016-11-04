package com.skymxc.lesson_35_touch_event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by sky-mxc
 */
public class LinearLayout2 extends LinearLayout {
    private static final String TAG = "LinearLayout2";

    public LinearLayout2(Context context) {
        super(context);
    }

    public LinearLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
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

    /**
     *  是否拦截 触摸事件
     * @param ev
     * @return true：拦截；false 不拦截；
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onInterceptTouchEvent: 触摸事件拦截  start" );
//        boolean f = super.onInterceptTouchEvent(ev);
//        Log.i(TAG, "onInterceptTouchEvent: 触摸事件拦截  end; result="+f);
        return false;
    }

    /**
     * 事件处理
     * @param event
     * @return true:事件已经处理（事件无法继续，如onClick不会触发）；false：事件没有被处理（事件无法继续，如onClick不会触发）；super():调用父类（事件继续传）；
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: 触摸事件处理 start");
//        boolean f = super.onTouchEvent(event);
//        Log.i(TAG, "onTouchEvent: 触摸事件处理 end result="+f);
        return super.onTouchEvent(event);
    }
}

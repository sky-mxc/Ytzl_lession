package com.skymxc.lesson_35_touch_event;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by sky-mxc
 */
public class MViewPager extends ViewPager {
    private static final String TAG = "MViewPager";

    public MViewPager(Context context) {
        super(context);
    }

    public MViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int position = getCurrentItem();
        if (position>0 && position < getAdapter().getCount()-1){
            //请求不要拦截 TouchEvent
            requestDisallowInterceptTouchEvent(true);
        }

        return super.dispatchTouchEvent(ev);
    }
}

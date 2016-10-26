package com.skymxc.lesson_35_touch_event;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
    
    private static final String TAG = "MainActivity";

    private TextView1 textView;
    private GestureDetector gd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView1) findViewById(R.id.text);
        gd = new GestureDetector(this,gestLis);
        gd.setOnDoubleTapListener(doubleTapLis);
    }
    
    private GestureDetector.OnDoubleTapListener doubleTapLis = new GestureDetector.OnDoubleTapListener() {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.i(TAG, "onSingleTapConfirmed: ");
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.i(TAG, "onDoubleTap: ");
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.i(TAG, "onDoubleTapEvent: ");
            return false;
        }
    };
    
    private GestureDetector.OnGestureListener gestLis = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            Log.i(TAG, "onDown: ");
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.i(TAG, "onShowPress: ");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.i(TAG, "onSingleTapUp: ");
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.i(TAG, "onScroll: ");
            return false;
            
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.i(TAG, "onLongPress: ");
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.i(TAG, "onFling: ");
            return false;
        }
    };

    /**
     * 分发事件 处理
     * @param ev
     * @return true ：被处理；false：没有被处理； super.dispatchTouchEvent(ev):继续分发；
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "dispatchTouchEvent: 事件分发");
        if (super.dispatchTouchEvent(ev)){
            return true;
        }
        return gd.onTouchEvent(ev);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: 触摸事件处理");
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick: ");
        switch (v.getId()){
            case R.id.text:
                int [] loc = new int[2];
                textView.getLocationInWindow(loc);
                Log.e(TAG, "onClick: window[]="+loc[0]+"====="+loc[1]);
                textView.getLocationOnScreen(loc);
                Log.e(TAG, "onClick: screen[]"+loc[0]+"====="+loc[1]);
                break;
        }
    }
}

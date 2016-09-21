package com.skymxc.demo.lesson_22_fragment;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"=========onCreate()========");
        setContentView(R.layout.activity_main);
        if (isLand()){
            OneFragment oneFragment = (OneFragment) getFragmentManager().findFragmentById(R.id.fragment_one);
            TwoFragment twoFragment = (TwoFragment) getFragmentManager().findFragmentById(R.id.fragment_two);
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG,"=========onPause()========");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"=========onResume()========");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"=========onDestroy()========");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.e(TAG,"=========onCreateOptionsMenu()========");
       if (isLand()){
           menu.add(Menu.NONE,1,0,"竖屏显示");
       }else {
           menu.add(Menu.NONE, 0, 0, "横屏显示");
       }
        return true;
    }

    /**
     * 判断屏幕方向
     * @return
     */
    public boolean isLand() {
        //获取屏幕方向
       // int orientation = getWindow().getAttributes().screenOrientation;

        //获取请求的屏幕方向
        int orientation = getRequestedOrientation();
            switch (orientation){
                    //竖屏
                case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
                case ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT:
                case ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT:
                case ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT:
                Log.e(TAG,"==竖屏==");
                    return false;
                //横屏
                case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
                case ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE:
                case ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE:
                case ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE:
                    Log.e(TAG,"==横屏==");
                    return true;
                default:
                    Log.e(TAG,"===其他==");
                    break;
            }
        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //请求屏幕方向
        switch (item.getItemId()){
            case 0:
                //请求横屏
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            case 1:
                //请求 竖屏
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
        }
        return true;
    }



    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG,"=========onStop()========");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.e(TAG,"=========onSaveInstanceState()========");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(TAG,"=========onConfigurationChanged()========");
    }
}

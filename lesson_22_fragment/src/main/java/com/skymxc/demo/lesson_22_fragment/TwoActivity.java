package com.skymxc.demo.lesson_22_fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioGroup;

/**
 * Created by sky-mxc
 */

public class TwoActivity extends AppCompatActivity {

    private static final  String TAG ="TwoActivity";
    private RadioGroup rg;

    private OneFragment oneFragment;
    private TwoFragment twoFragment;
    private FragmentManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        rg = (RadioGroup) findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(onCheckedChangeListener);
        //不会执行生命周期的方法 只是声明
        oneFragment = new OneFragment();
        twoFragment = new TwoFragment();
        manager = getFragmentManager();
        //开启Transaction
        FragmentTransaction ft= manager.beginTransaction();

        //添加
        /**
         * 不指定父容器 会充满activity
         * tag 你自己指定吧
         */
        ft.add(R.id.container,oneFragment,"one")
               // .add(R.id.container,twoFragment,"two")
        .commit();
        //必须commit 否则不会生效
    }


    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener =new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            switch (checkedId){
                case R.id.rb1:
                    log("remove-two and add one");
                    /**
                     * 添加到回退栈 是将一次事务添加到回退栈
                     */
                    manager.beginTransaction()
                         //   .addToBackStack("one")
                            .remove(twoFragment)
                            .add(R.id.container,oneFragment,"one")
                            .commit();
                    break;
                case R.id.rb2:
                    log("remove-one and add-two");
                    manager.beginTransaction()
                       //     .addToBackStack("two")
                            .remove(oneFragment)
                            .add(R.id.container,twoFragment,"two")
                            .commit();
                    break;
            }
        }
    };


    private void log(String str){
        Log.w(TAG,"====="+str+"======");
    }

}

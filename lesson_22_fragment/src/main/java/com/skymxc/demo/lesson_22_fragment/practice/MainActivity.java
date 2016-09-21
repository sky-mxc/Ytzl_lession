package com.skymxc.demo.lesson_22_fragment.practice;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioGroup;

import com.skymxc.demo.lesson_22_fragment.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="practice.MainActivity";

    private RadioGroup rg;
    private MsgFragment msgFragment;

    private FragmentManager manager;
    private CommonFragment commonFragment;
    private Fragment currentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_main);
        initView();

    }


    private void initView() {
        rg = (RadioGroup) findViewById(R.id.menu_bottom);
        rg.setOnCheckedChangeListener(checkedChangeListener);
        //两个Fragment 实例化
        msgFragment = new MsgFragment();
        commonFragment = new CommonFragment();
        //动态加载进来 消息Fragment
        manager = getFragmentManager();
        manager.beginTransaction()
                .add(R.id.container,msgFragment,"msg")
                .commit();
        //当前 Fragment为 消息Fragment
        currentFragment= msgFragment;
    }


    private RadioGroup.OnCheckedChangeListener checkedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.msg:
                    if (currentFragment instanceof  CommonFragment){
                        //如果当前为 通用Fragment就移除掉 将 消息Fragment加载
                        manager.beginTransaction()
                                .remove(currentFragment)
                                .add(R.id.container,msgFragment)
                                .commit();
                        currentFragment =msgFragment;
                    }

                    break;
                case R.id.person:
                   if (currentFragment instanceof  MsgFragment){
                        manager.beginTransaction()
                                .remove(currentFragment)
                               .add(R.id.container,commonFragment)
                                .commit();
                       currentFragment = commonFragment;
                    }
                    commonFragment.getArguments().putString("msg","联系人面板");
                    break;
                case R.id.active:
                    if (currentFragment instanceof  MsgFragment){
                        manager.beginTransaction()
                                .remove(currentFragment)
                                .add(R.id.container,commonFragment)
                                .commit();
                        currentFragment = commonFragment;
                    }
                    commonFragment.getArguments().putString("msg","活动面板");

                    break;
            }
            if (currentFragment instanceof  CommonFragment){
                commonFragment.setText();
            }
        }
    };


    public void log(String text){
        Log.e(TAG,"==="+text+"===");
    }
}

package com.skymxc.demo.lession_7_intent_intnentfilter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View v){
        switch (v.getId()){
            case R.id.to_lession7_activity1_main:

                Intent in = new Intent();
                //启动另一个程序
                in.setClassName("com.skymxc.demo.lession_7_activity1","com.skymxc.demo.lession_7_activity1.MainActivity");
                startActivity(in);
                break;
            case R.id.to_a:
                in = new Intent("com.skymxc.demo.lession_7_intent_intnentfilter.action.PLAY");
               // in.setAction("com.skymxc.demo.lession_7_intent_intnentfilter.action.PLAY");       //在 intentFilter中的定义的额

                in.setDataAndType(Uri.parse("file://lskdjflsdf"),"video");
                //没有加 category 默认加一个 default 如果加了 也有一个 default 默认的   自己添加的 category 必须在 intentfilter 中存在 如果有一个在 intentFilter中没有定义 ，那就不行
                in.addCategory("com.skymxc.demo.lession_7_intent_intnentfilter.category.AAA");
                in.addCategory("com.skymxc.demo.lession_7_intent_intnentfilter.category.DD");
                startActivity(in);

                break;
        }
    }
}

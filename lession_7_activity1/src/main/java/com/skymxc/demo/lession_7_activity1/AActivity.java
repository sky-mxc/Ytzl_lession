package com.skymxc.demo.lession_7_activity1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by sky-mxc
 * Date : 2016/8/25
 */
public class AActivity extends AppCompatActivity {

    private TextView tv ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        tv = (TextView) findViewById(R.id.text);

        StringBuffer sb =  new StringBuffer(tv.getText());
        sb.append("\n");
        sb.append("任务栈(Task) ID:"+getTaskId());
        sb.append("\n活动信息(activityInfo)：\n"+this.toString());
        tv.setText(sb.toString());
    }


    public void click(View v){
     switch (v.getId()){
         case R.id.to_a:
             Intent intent = new Intent(this,AActivity.class);

             intent.setClass(this,AActivity.class);
             intent.setClassName(this,AActivity.class.getName());

             //都是显示启动的
           //  intent.setClassName(getPackageName(),AActivity.class.getName());
          //   intent.setClassName("另一个程序的包名","程序中组件的包名类名(Activity的~完全限定名)");  //启动另一个程序的组件 显示启动方式  （程序间相互启动组件）
            // intent.setComponent(new ComponentName(params));

             //Action的设置
            /* intent.setAction();
             intent.setData();      //数据的设置  Uri
             intent.setType();      //数据的类型设置
             intent.setDataAndType();   //数据和数据类型同时设置   如果想要 data和type同时存在只能使用此方法


             intent.addCategory();      //附加信息的设置*/

             startActivity(intent);
             break;
         case R.id.to_b:
             intent = new Intent(this,BActivity.class);
             startActivity(intent);
             break;
         case R.id.to_c:
             intent = new Intent(this,CActivity.class);
             startActivity(intent);
             break;
         case R.id.to_d:
             intent = new Intent(this,DActivity.class);
             startActivity(intent);
             break;
     }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Tag","=============onDestroy================="+this.toString());
    }
}

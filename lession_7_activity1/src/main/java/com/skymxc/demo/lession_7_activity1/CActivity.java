package com.skymxc.demo.lession_7_activity1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by sky-mxc
 * Date : 2016/8/25
 */
public class CActivity extends AppCompatActivity {

    private TextView tv ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);
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
}

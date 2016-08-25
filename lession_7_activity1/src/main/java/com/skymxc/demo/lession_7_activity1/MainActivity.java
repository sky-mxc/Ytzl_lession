package com.skymxc.demo.lession_7_activity1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 点击事件
     * @param v
     */
    public void click(View v){
        switch (v.getId()){
            case R.id.standard:
                Intent intent= new Intent(this,AActivity.class);
                startActivity(intent);
                break;
            case R.id.single_top:
                intent = new Intent(this,BActivity.class);
                startActivity(intent);
                break;
            case R.id.single_task:
                intent = new Intent(this,CActivity.class);
                startActivity(intent);
                break;
            case R.id.single_instance:
                intent = new Intent(this,DActivity.class);
                startActivity(intent);
                break;
        }
    }
}

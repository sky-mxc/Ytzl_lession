package com.skymxc.demo.lession_6_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by sky-mxc
 * Date : 2016/8/24
 */
public class ThreeActivity extends AppCompatActivity {

    private EditText edit = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thress);
        edit= (EditText) findViewById(R.id.edit);
    }


    /**
     * Click event
     * @param v
     */
    public void click(View v){
        switch (v.getId()){
            case R.id.save:

                //这里主要用于数据传递
                Intent intent = new Intent();
                intent.putExtra("text",edit.getText().toString());

                //设置结果 参数1  结果  参数2 intent
                setResult(RESULT_OK,intent);


                break;
            case R.id.cancel:
                setResult(RESULT_CANCELED);
                break;
        }
        //关闭活动  关闭调用此方法的活动  谁调用的就关闭谁
        finish();
    }
}

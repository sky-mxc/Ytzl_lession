package com.skymxc.demo.practice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by sky-mxc
 */
public class ChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

    }

    public void click(View v){
        int result =0;
        switch (v.getId()){
            case R.id.camera:
                result=1;
                break;
            case R.id.picture:
                result=-1;
                break;
        }
        Intent intent = new Intent();
        intent.putExtra("result",result);
        setResult(RESULT_OK,intent);
        finish();
    }
}

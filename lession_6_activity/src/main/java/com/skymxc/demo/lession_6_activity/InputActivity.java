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
public class InputActivity extends AppCompatActivity {

    private EditText edit = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent= getIntent();
        String type = intent.getStringExtra("type");
        setContentView(R.layout.activity_input);
        edit = (EditText) findViewById(R.id.edit);

        switch (type){
            case "name":
                edit.setHint("请输入昵称");
                break;
            case "phone":
                edit.setHint("请输入手机号");
                break;
            case "email":
                edit.setHint("请输入邮箱");
                break;
        }
    }

    /**
     * 点击事件
     * @param v
     */
    public void click(View v){

        switch (v.getId()){
            case R.id.save:
                String text = edit.getText().toString();
                if (text!=null && text.trim().length()>0) {

                    Intent intent = new Intent();
                    intent.putExtra("text", text);
                    setResult(RESULT_OK, intent);
                }
                break;
            case R.id.cancel:

                break;
        }
        finish();
    }
}

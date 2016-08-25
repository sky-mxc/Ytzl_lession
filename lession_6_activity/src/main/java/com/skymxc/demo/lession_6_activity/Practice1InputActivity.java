package com.skymxc.demo.lession_6_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class Practice1InputActivity extends AppCompatActivity {

    //文本输入框
    private EditText edit;
    //条件 由前Activity传入 判断是增加还是移除
    private String opr;
    //确定按钮
    private Button bt ;

    private ArrayList<String> entries ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice1_input);
        //初始化操作
        Intent intent = getIntent();
        bt = (Button) findViewById(R.id.save);
        entries = intent.getStringArrayListExtra("entries");
         opr = intent.getStringExtra("opr");
        if (opr.equals("add")){
            bt.setText("增加");
        }else{
            bt.setText("移除");
        }
        edit = (EditText) findViewById(R.id.edit);
    }

    public void  click(View v){
        switch (v.getId()){
            case R.id.save:

                String text= edit.getText().toString();
                if (text !=null && text.trim().length()>0){     //空值无效
                   if (opr.equals("add")){  //增加数据
                        if (entries.size()<5){  //规定数据最多只能有5个
                            entries.add(text);
                        }
                   }else{   //移除数据
                       entries.remove(text);
                   }
                    Intent intent = new Intent();
                    intent.putStringArrayListExtra("entries",entries);
                    setResult(RESULT_OK,intent);//返回数据
                }
                break;
            case R.id.cancel:
                setResult(RESULT_CANCELED);
                break;
        }
        finish();   //结束这个activity
    }
}

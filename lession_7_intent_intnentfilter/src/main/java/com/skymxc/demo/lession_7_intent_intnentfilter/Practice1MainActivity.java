package com.skymxc.demo.lession_7_intent_intnentfilter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Practice1MainActivity extends AppCompatActivity {

    private Button bt;

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice1_main);
        bt = (Button) findViewById(R.id.to_login);
        tv = (TextView) findViewById(R.id.text);

        Intent intent = getIntent();
        if (intent.getExtras()!=null){
            boolean isLogin = intent.getBooleanExtra("isLogin",false);
            if (isLogin){
                String phone = intent.getStringExtra("phone");
                String pwd = intent.getStringExtra("pwd");
                tv.setText("欢迎 "+ phone+"  登录\n你的密码是："+pwd);
            }
        }
    }


    public void click(View v){
        switch (v.getId()){
            case R.id.to_login:
                Intent in = new Intent(this,LoginActivity.class);
                startActivity(in);
                break;
        }
    }
}

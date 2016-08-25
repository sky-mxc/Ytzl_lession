package com.skymxc.demo.lession_7_intent_intnentfilter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by sky-mxc
 */
public class LoginActivity extends AppCompatActivity {

    private EditText phone ;

    private EditText pwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phone = (EditText) findViewById(R.id.phone);
        pwd = (EditText) findViewById(R.id.pwd);
    }

    public void click(View v){
        switch (v.getId()){
            case R.id.to_register:
                //去注册
                Intent intent = new Intent(this,RegisterAcvtivity.class);
                startActivity(intent);
                break;
            case R.id.login:
                //登录
                String phoneStr = phone.getText().toString();
                String pwdStr = pwd.getText().toString();

                intent = new Intent(this,Practice1MainActivity.class);
                intent.putExtra("isLogin",true);
                intent.putExtra("phone",phoneStr);
                intent.putExtra("pwd",pwdStr);
                startActivity(intent);

                break;
        }
    }
}

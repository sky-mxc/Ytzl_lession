package com.skymxc.demo.lesson_32_desgin_widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by sky-mxc
 */
public class DrawerActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "DrawerActivity tag ";

    private TextInputLayout inputRePassword;
    private TextInputLayout inputPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        inputRePassword =(TextInputLayout) findViewById(R.id.text_input_repwd);
        inputPassword=(TextInputLayout) findViewById(R.id.text_input_pwd);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                String account = inputRePassword.getEditText().getText().toString();
                String pwd = inputPassword.getEditText().getText().toString();
                if (account.equals(pwd)){
                    inputPassword.setErrorEnabled(false);
                    Snackbar.make(v,"两次密码输入一致",Snackbar.LENGTH_INDEFINITE)
                            .setAction("确定", this).show();
                }else{
                    inputPassword.setError("两次输入不相同");
                }
                break;
            case R.id.float_action:
                break;
            default:
                Log.e(TAG, "onClick: 点击了确定");
                break;
        }

        Log.e(TAG, "onClick: id="+v.getId());
    }
}

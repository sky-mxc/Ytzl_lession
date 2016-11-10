package com.skymxc.lesson_45_jpush;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cn.jpush.sms.SMSSDK;
import cn.jpush.sms.listener.SmscheckListener;
import cn.jpush.sms.listener.SmscodeListener;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private EditText etPhone;
    private EditText code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        JPushInterface.stopPush(getApplicationContext());//停止
//        JPushInterface.resumePush(getApplicationContext());//重启

        //初始化短信SDK：
        SMSSDK.getInstance().initSdk(this);
        etPhone = (EditText) findViewById(R.id.phone);
        code = (EditText) findViewById(R.id.sms_code);
    }

    @Override
    public void onClick(View view) {
        String p = etPhone.getText().toString();
        switch (view.getId()){
            case R.id.get_sms_code:
                Log.i(TAG, "onClick: get_sms_code");
                SMSSDK.getInstance().getSmsCodeAsyn(p,"1",smscodeListener);
                break;
            case R.id.get_voice_code:
                break;
            case R.id.submit:
                Log.i(TAG, "onClick: submit");
                String c = code.getText().toString();
                SMSSDK.getInstance().checkSmsCodeAsyn(p,c,smsCheckLis);
                break;
        }
    }

    private SmscheckListener smsCheckLis = new SmscheckListener() {
        @Override
        public void checkCodeSuccess(String s) {
            Toast.makeText(MainActivity.this, "验证成功："+s, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void checkCodeFail(int i, String s) {
            Toast.makeText(MainActivity.this, i+"验证失败："+s, Toast.LENGTH_SHORT).show();
            Log.i(TAG, "smsCheckLis checkCodeFail: i="+i);
        }
    };

    private SmscodeListener smscodeListener = new SmscodeListener() {
        @Override
        public void getCodeSuccess(String s) {
            Toast.makeText(MainActivity.this, "验证码已发送:"+s, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void getCodeFail(int i, String s) {
            Toast.makeText(MainActivity.this, i+"验证啊发送失败："+s, Toast.LENGTH_SHORT).show();
            Log.i(TAG, "getCodeFail: i="+i);
        }
    };
}

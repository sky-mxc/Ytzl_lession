package com.skymxc.demo.lession_12_practice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.TypeReference;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    public static final int REQUEST_REGISTE =1;
    public static final int REQUEST_RESET =2;

    private EditText etPhone ;
    private EditText etPwd ;
    private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etPhone = (EditText) findViewById(R.id.phone);
        etPwd = (EditText) findViewById(R.id.pwd);
        bt = (Button) findViewById(R.id.login);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login:
                bt.setEnabled(false);
                bt.setText("登录中...");
                //登录
                login();
                break;
            case R.id.to_register:
                //注册
                toRegister();
                break;
            case R.id.to_reset:
                //重置
                toReset();
                break;
        }
    }

    /**
     * 重置
     */
    private void toReset() {
        Intent in = new Intent(this,RestActivity.class);
        startActivityForResult(in,REQUEST_RESET);
    }

    /**
     * 去注册
     */
    private void toRegister(){
        Intent in = new Intent(this,RegisterActivity.class);
        startActivityForResult(in,REQUEST_REGISTE);
    }

    /**
     * 执行登录操作
     */
    private void login(){
        String phone = etPhone.getText().toString();
        String pwd = etPwd.getText().toString();
        final HttpTask<Result<User>> task = new HttpTask<>(new TypeReference<Result<User>>(){});
        task.setOnResponseLinstener(new HttpTask.OnResponseLinstener<Result<User>>() {
            @Override
            public void onResponse(Result<User> userResult) {
                Log.e("Tag","===========userResult========"+userResult);
                if (userResult.error == Result.ERRORNO.NO_ERROR){
                    ProgrammeListActivity.startActivity(MainActivity.this);
                    finish();
                }else{
                    bt.setEnabled(true);
                    bt.setText("登录");
                    Toast.makeText(MainActivity.this,userResult.error.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        task.execute("http://toolsmi.com/Veeker/login?phone="+phone+"&pwd="+pwd+"&type=0");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data == null || resultCode != RESULT_OK) return;
        String phone = data.getStringExtra("phone");
        etPhone.setText(phone);

    }
}

package com.skymxc.demo.lession_12_practice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.TypeReference;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etPhone;
    private EditText etPwd;
    private EditText etRepwd;
    private EditText etNick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etPhone = (EditText) findViewById(R.id.phone);
        etPwd = (EditText) findViewById(R.id.pwd);
        etRepwd = (EditText) findViewById(R.id.re_pwd);
        etNick = (EditText) findViewById(R.id.nick);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register:
                final String phone = etPhone.getText().toString();
                String pwd = etPwd.getText().toString();
                String rePwd = etRepwd.getText().toString();
                String nick = etNick.getText().toString();
                HttpTask<Result<User>> task = new HttpTask<>(new TypeReference<Result<User>>(){});
                task.execute("http://toolsmi.com/Veeker/reg?phone="+phone+"&pwd="+pwd+"&nick="+nick+"&type=0");
                task.setOnResponseLinstener(new HttpTask.OnResponseLinstener<Result<User>>() {
                    @Override
                    public void onResponse(Result<User> userResult) {
                        if (Result.ERRORNO.NO_ERROR == userResult.error){

                            Intent intent = new Intent();
                            intent.putExtra("phone",phone);
                            setResult(RESULT_OK,intent);
                            finish();
                        }else{
                            Toast.makeText(RegisterActivity.this,userResult.error.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }

    }

}

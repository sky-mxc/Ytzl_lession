package com.skymxc.demo.lession_12_practice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.TypeReference;

public class RestActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etPhone;
    private EditText etPwd;
    private EditText etRepwd;
    private Button btReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);
        etPhone = (EditText) findViewById(R.id.phone);
        etPwd = (EditText) findViewById(R.id.pwd);
        etRepwd = (EditText) findViewById(R.id.re_pwd);
        btReset = (Button) findViewById(R.id.reset);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reset:
                btReset.setEnabled(false);
                btReset.setText("重置中...");
                final String phone = etPhone.getText().toString();
                String pwd = etPwd.getText().toString();
                String rePwd = etRepwd.getText().toString();

                String url ="http://toolsmi.com/Veeker/modifypwd?phone="+phone+"&pwd="+pwd;

                HttpTask<Result<Boolean>>  task = new HttpTask<Result<Boolean>>(new TypeReference<Result<Boolean>>(){});
                task.execute(url);
                task.setOnResponseLinstener(new HttpTask.OnResponseLinstener<Result<Boolean>>() {
                    @Override
                    public void onResponse(Result<Boolean> userResult) {
                        if (userResult.data == true){
                          //  Toast.makeText(RestActivity.this,userResult.error.toString(),Toast.LENGTH_SHORT).show();
                            Intent in = new Intent();
                            in.putExtra("phone",phone);
                            setResult(RESULT_OK,in);
                            finish();
                        }else{
                            Toast.makeText(RestActivity.this,"重置失败",Toast.LENGTH_SHORT).show();
                            btReset.setEnabled(true);
                            btReset.setText("重置");
                        }
                    }
                });

                break;
        }
    }
}

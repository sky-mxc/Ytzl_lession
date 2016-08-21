package com.skymxc.example.lession_1_wideget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticeActivity extends AppCompatActivity {


    //手机号 输入框
    private EditText editPhone = null;

    //验证码输入框
    private EditText editVerify = null;

    //获取验证码button
    private Button buttonGetVerify = null;

    //下一步button
    private  Button buttonNext = null;

    //手机号
    private  String oldPhone = null;

    //验证码
    private String oldverifyCode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);


        editPhone = (EditText) findViewById(R.id.editPhoneNumber);
        editVerify  = (EditText) findViewById(R.id.editVerifyCode);
        buttonGetVerify = (Button) findViewById(R.id.buttonGetVerifyCode);
        buttonNext  = (Button) findViewById(R.id.buttonNextStep);



        //button监听 获取 验证码
        buttonGetVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //手机号
                String phone = editPhone.getText().toString();


                if (phone.length()<11){
                    Toast.makeText(PracticeActivity.this,"手机号几位的",Toast.LENGTH_SHORT).show();
                    return ;
                }

                //生成验证码

               int verifyCode=(int)((Math.random()*(999999-100000))+100000);

                //追加到 验证码输入框
                Editable editable = editVerify.getText();
                editable.clear();
                editable.append(verifyCode+"");

                oldPhone= phone;
                oldverifyCode = verifyCode+"";


            }
        });

        //下一步 吐出验证码 查看当前手机号和验证码是否匹配
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = editPhone.getText().toString();
                String code = editVerify.getText().toString();

                if (phone.length()<11||code.length()<6){
                    Toast.makeText(PracticeActivity.this,"手机号和验证码有一个不对",Toast.LENGTH_SHORT).show();
                    return ;
                }

                Toast.makeText(PracticeActivity.this,"验证码："+code,Toast.LENGTH_SHORT).show();

                if (phone.equals(oldPhone)&&oldverifyCode.equals(code)){
                    //完全匹配
                    Toast.makeText(PracticeActivity.this,"手机号和验证码匹配，下一步吧",Toast.LENGTH_SHORT).show();
                }else{
                    //不匹配
                    show("手机号和验证码不匹配");
                }

            }
        });


    }
    private  void show(String str){
        Toast.makeText(PracticeActivity.this,str,Toast.LENGTH_SHORT).show();
    }
}

package com.skymxc.demo.lession_19_animation.practice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.skymxc.demo.lession_19_animation.R;
import com.skymxc.demo.lession_19_animation.practice.entity.User;
import com.skymxc.demo.lession_19_animation.practice.util.DBHelper;
import com.skymxc.demo.lession_19_animation.practice.util.UserUtils;

/**
 * Created by sky-mxc
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG ="RegisterActivity";

    private EditText etAccount;
    private EditText etPwd ;
    private EditText etRepwd;
    private CheckBox cbShow;
    private TranslateAnimation translateAnimation;
    private boolean isPass;
    private String message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();

    }

    private void initView() {
        etAccount = (EditText) findViewById(R.id.account);
        etPwd = (EditText) findViewById(R.id.pwd);
        etRepwd = (EditText) findViewById(R.id.repwd);
        cbShow = (CheckBox) findViewById(R.id.cb_show_pwd);
        etAccount.setOnFocusChangeListener(onFocusChangeListener);
        etPwd.setOnFocusChangeListener(onFocusChangeListener);
        etRepwd.setOnFocusChangeListener(onFocusChangeListener);
         translateAnimation = (TranslateAnimation) AnimationUtils.loadAnimation(this,R.anim.anim_p1_shake);
        cbShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){
                    etPwd.setInputType(InputType.TYPE_CLASS_TEXT);
                    etRepwd.setInputType(InputType.TYPE_CLASS_TEXT);
                }else{
                    etPwd.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etRepwd.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

    private View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
         if (b==false){
             String reg ="^\\w+$";
             EditText edit= (EditText) view;
             String text = edit.getText().toString();
             switch (view.getId()){
                 case R.id.account:

                     if (!text.matches(reg)){
                         message ="账户由 字母数字和下划线组成";
                         Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_SHORT).show();
                         etAccount.startAnimation(translateAnimation);
                         isPass=false;
                         return;
                     }
                     User user = UserUtils.isExists(text, DBHelper.getInstance(RegisterActivity.this).getDB());
                     if (user!=null){
                         message ="该账户已经存在";
                         Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_SHORT).show();
                         etAccount.startAnimation(translateAnimation);
                         isPass=false;
                         return;
                     }
                     isPass= true;
                     break;
                 case R.id.pwd:
                     if (!text.matches(reg)){
                         message ="密码由 字母数字和下划线组成";
                         Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_SHORT).show();
                         etPwd.startAnimation(translateAnimation);
                         isPass=false;
                         return;
                     }
                     isPass= true;
                     break;
                 case R.id.repwd:
                     String pwd = etPwd.getText().toString();
                     if (!pwd.equals(text)){
                         message = "两次输入密码不一致";
                         Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_SHORT).show();
                         etRepwd.startAnimation(translateAnimation);
                         isPass=false;
                         return;
                     }
                     isPass= true;
                     break;
             }
         }
        }
    };

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.register:
               if (isPass){
                    String  account = etAccount.getText().toString();
                   String pwd = etPwd.getText().toString();
                   User user = new User() ;
                   user.pwd=pwd;
                   user.account=account;
                   UserUtils.insert(DBHelper.getInstance(this).getDB(),user);
                   Intent in = new Intent();
                   in.putExtra("user",user);
                   setResult(RESULT_OK,in);
                   finish();
               }else{
                Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
               }
                break;
        }
    }
}

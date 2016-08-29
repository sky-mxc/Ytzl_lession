package com.skymxc.demo.practice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

/**
 * Created by sky-mxc
 */
public class UpdatePwdActvity extends AppCompatActivity {

    private ImageView imgBack;
    private EditText editPwd;
    private RatingBar ratingBar ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pwd);
        imgBack = (ImageView) findViewById(R.id.back);
        editPwd = (EditText) findViewById(R.id.edit_pwd);
        ratingBar = (RatingBar) findViewById(R.id.rpb);

        //设置文本监听
        editPwd.addTextChangedListener(new TextWatcher() {
            /**
             * 文本改变前
             * @param charSequence
             * @param i
             * @param i1
             * @param i2
             */
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            /**
             * 文本改变时
             * @param charSequence
             * @param i
             * @param i1
             * @param i2
             */
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            /**
             * 文本改变后
             * @param editable
             */
            @Override
            public void afterTextChanged(Editable editable) {

                String str = editable.toString();
                //数字 字符 符号
                if (str.matches("^(?![a-zA-Z0-9]+$)(?![^a-zA-Z/D]+$)(?![^0-9/D]+$).{5,16}$")){
                    ratingBar.setRating(3);
                }else if (str.matches("^(?![^a-zA-Z]+$)(?!\\\\D+$).{8,15}$")){
                    ratingBar.setRating(2);
                }else{
                    ratingBar.setRating(1);
                }
            }
        });
    }

    public void click(View v){
        String d ="低";
        Log.e("Tag","========Rating："+ratingBar.getProgress());
        switch (ratingBar.getProgress()){
            case 1:
                d ="弱";
                break;
            case 2:
                d="中";
                break;
            case 3:
                d= "强";
                break;
        }
        switch (v.getId()){
            case R.id.back:
                Intent in = new Intent();
               in.putExtra("d",d);
                setResult(RESULT_OK,in);
                break;
        }
        finish();
    }
}

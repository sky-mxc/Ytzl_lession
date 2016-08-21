package com.skymxc.example.lession_1_wideget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class Practice2Activity extends AppCompatActivity {

    //密码
    private EditText editPwd = null;

    //复选框
    private CheckBox cbShow = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice2);
        editPwd= (EditText) findViewById(R.id.editPwd);
        cbShow = (CheckBox) findViewById(R.id.checkboxShow);

        cbShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    //明文显示
                    editPwd.setInputType(InputType.TYPE_CLASS_TEXT);
                }else{
                    //密码状态
                    editPwd.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }
}

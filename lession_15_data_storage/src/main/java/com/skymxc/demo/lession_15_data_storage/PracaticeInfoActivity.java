package com.skymxc.demo.lession_15_data_storage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

class PracticeInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etName ;
    private EditText etAge;
    private EditText etGender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pracgtice_info);
        etName = (EditText) findViewById(R.id.name);
        etAge = (EditText) findViewById(R.id.age);
        etGender = (EditText) findViewById(R.id.gender);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ok:
                //TODO 获取到输入的信息 存入 SharedPreferences 并serResult回去 MainActivity 将has改为 true
                String name = etName.getText().toString();
                String age = etAge.getText().toString();
                String gender = etGender.getText().toString();
                SharedPreferences sp = getSharedPreferences("info",MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("has",true);
                edit.putString("name",name);
                edit.putString("age",age);
                edit.putString("gender",gender);
                edit.apply();
                finish();
                break;
        }
    }
}

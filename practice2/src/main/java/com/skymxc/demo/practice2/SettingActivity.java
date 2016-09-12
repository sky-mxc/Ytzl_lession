package com.skymxc.demo.practice2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.Response;
import com.skymxc.demo.practice2.entity.AppInfo;
import com.skymxc.demo.practice2.entity.Result;
import com.skymxc.demo.practice2.network.Http;

/**
 * Created by sky-mxc
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{

    private Button btUpGrade;
    private CheckBox cbMode;
    private  SharedPreferences sp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Tag","==SettingActivity=========onCreate===========");
        setContentView(R.layout.activity_setting);
        btUpGrade = (Button) findViewById(R.id.up_grade);
        cbMode = (CheckBox) findViewById(R.id.model);
        sp = getSharedPreferences("setting",MODE_PRIVATE);
        btUpGrade.setText(btUpGrade.getText()+"\t"+BuildConfig.VERSION_NAME);
        cbMode.setChecked(sp.getBoolean("mode",false));
        btUpGrade.setOnClickListener(this);
        cbMode.setOnCheckedChangeListener(this);

    }


    public static void startActivity(Context context) {
        Intent in = new Intent(context,SettingActivity.class);
        context.startActivity(in);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b){
         //  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            //  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("mode",b).commit();
    }

    @Override
    public void onClick(View view) {
    switch (view.getId()){
        case R.id.up_grade:
            String url ="http://toolsmi.com/starclass/ver?ver="+BuildConfig.VERSION_CODE;
            Http.getInstance(this).post(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {

                    Result<AppInfo> result = JSON.parseObject(s,new TypeReference<Result<AppInfo>>(){});
                    Toast.makeText(SettingActivity.this,result.describe,Toast.LENGTH_SHORT).show();
                    if (result.state==1){
                        new AlertDialog.Builder(SettingActivity.this).setTitle("检查更新")
                                .setMessage(String.format("当前版本%s,检查到新版本%s,是否更新？",BuildConfig.VERSION_NAME,result.data.getVersionName()))
                                .setNegativeButton("立即更新", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(SettingActivity.this,"已经开始下载了。。。",Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setPositiveButton("下次再说",null)
                                .show();
                    }else{
                        Toast.makeText(SettingActivity.this,"已经是最新版本了",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            break;
    }
    }
}


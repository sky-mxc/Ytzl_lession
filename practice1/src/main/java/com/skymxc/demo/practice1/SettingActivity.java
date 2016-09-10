package com.skymxc.demo.practice1;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.skymxc.demo.practice1.model.Result;
import com.skymxc.demo.practice1.model.VersionInfo;
import com.skymxc.demo.practice1.utils.Http;

public class SettingActivity extends AppCompatActivity  implements View.OnClickListener{

    private TextView tvVer;
    private ToggleButton toggle;
    private boolean checked;
    private  SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("setting",MODE_PRIVATE);
        if ((checked=preferences.getBoolean("checked",false))){
            //夜间模式
            setTheme(R.style.AppThemeNight);
        }else{
            //日间模式
            setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.activity_setting);
        tvVer = (TextView) findViewById(R.id.ver);
        toggle = (ToggleButton) findViewById(R.id.toggle);
      //  toggle.setOnCheckedChangeListener(null);
        Log.e("Tag","====================前===="+toggle.isChecked());
        initData();

        toggle.setChecked(checked);
        Log.e("Tag","====================后===="+toggle.isChecked());

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checked =b;
                Log.e("Tag","===========onCheckedChanged============"+b);
                //TODO 保存到SharedPreferences

                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("checked",b);
                editor.apply();
                recreate();

            }
        });
    }



    /**
     * 初始化数据
     */
    private void initData(){
       String verName= BuildConfig.VERSION_NAME;
        int verCode = BuildConfig.VERSION_CODE;
        Log.e("Tag","========verCode:"+verCode+"===============verName:"+verName);
        tvVer.setText("v"+verName);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.check:
                //检查版本更行
                checkVersion();
                Log.e("Tag","===========onClick()=========");
                break;
        }
    }

    /**
     * 检查版本
     */
    private  void checkVersion(){
        String url ="http://toolsmi.com/starclass/ver?ver="+BuildConfig.VERSION_CODE;
        Http.getInstance(this).get(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String json) {
                if (!TextUtils.isEmpty(json)) {
                    Result<VersionInfo> result = JSON.parseObject(json, new TypeReference<Result<VersionInfo>>() {});
                    if (result.state ==1){
                        //有更新
                        String message ="当前版本是v"+BuildConfig.VERSION_NAME+"，检查到新的版本v"+result.data.getVersionName()+",立即更新？";
                        new AlertDialog.Builder(SettingActivity.this)
                                .setMessage(message)
                                .setNegativeButton("立即更新",null)
                                .setPositiveButton("下次再说",null)
                                .show();
                    }else{
                        //无更新
                        Toast.makeText(SettingActivity.this,"已经是最新版本",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(SettingActivity.this,"网络加载出错",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

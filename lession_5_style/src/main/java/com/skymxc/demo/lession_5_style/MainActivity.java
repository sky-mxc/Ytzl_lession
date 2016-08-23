package com.skymxc.demo.lession_5_style;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private RadioGroup theme = null;

    private int checkId  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){
            //取出保存的状态，参数1 保存时的Key ，参数2 默认值
            checkId=  savedInstanceState.getInt("checkId",R.style.AppTheme1);
            //主题必须在setContentView 之前设置才能生效
            switch (checkId){
                case R.id.theme1:
                    setTheme(R.style.AppTheme);
                    break;
                case R.id.theme2:
                    setTheme(R.style.AppTheme1);
                    break;
            }
        }


        setContentView(R.layout.activity_main);
        theme= (RadioGroup) findViewById(R.id.theme);

        if (checkId!=0)theme.check(checkId);
        theme.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            /**
             *
             * @param radioGroup
             * @param i 选中的button的id
             */
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Log.e("Tag","=========onCheckedChanged========"+i);

                checkId=i;
                //重新加载  整个 Activity 都重新初始化
                recreate();
            }
        });


    }

    /**
     * 状态保存
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //将checkId 的值保存 因为 recreate会重新初始化 变量 都会被 销毁 重新加载
        outState.putInt("checkId",checkId);
    }
}

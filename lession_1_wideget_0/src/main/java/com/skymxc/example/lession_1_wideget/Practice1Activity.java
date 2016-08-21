package com.skymxc.example.lession_1_wideget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;


public class Practice1Activity extends AppCompatActivity {


    //全选
    private CheckBox cbAll = null;

    //吃饭
    private CheckBox cbEat = null;

    //睡觉
    private CheckBox cbSleep = null;

    //打豆豆
    private CheckBox cbDa = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice1);

        cbAll = (CheckBox) findViewById(R.id.checkboxAll);
        cbEat = (CheckBox) findViewById(R.id.checkboxEat);
        cbSleep = (CheckBox) findViewById(R.id.checkboxSleep);
        cbDa = (CheckBox) findViewById(R.id.checkboxDadoudou);

        cbAll.setOnCheckedChangeListener(checkedChange);
        cbEat.setOnCheckedChangeListener(checkedChange);
        cbSleep.setOnCheckedChangeListener(checkedChange);
        cbDa.setOnCheckedChangeListener(checkedChange);
    }

    //状态改变后 触发
    private CompoundButton.OnCheckedChangeListener checkedChange = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            switch (buttonView.getId()){
                case R.id.checkboxAll:
                            cbEat.setChecked(isChecked);
                            cbSleep.setChecked(isChecked);
                            cbDa.setChecked(isChecked);
                    break;
                default:
                    if (!isChecked){
                        cbAll.setOnCheckedChangeListener(null);
                        cbAll.setChecked(false);
                        cbAll.setOnCheckedChangeListener(checkedChange);
                    }
                    break;
            }
        }
    };
}

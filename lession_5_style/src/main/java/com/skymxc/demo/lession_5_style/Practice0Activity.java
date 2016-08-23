package com.skymxc.demo.lession_5_style;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

public class Practice0Activity extends AppCompatActivity {


    private Switch st = null;

    private boolean isChecked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState!=null){
            isChecked= savedInstanceState.getBoolean("isChecked",false);
            if (isChecked){
                this.setTheme(R.style.NightTheme);
            }else{
                this.setTheme(R.style.DayTheme);
            }
        }


        setContentView(R.layout.activity_practice0);

        st = (Switch) findViewById(R.id.st);
        st.setChecked(isChecked);
        st.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isChecked=b;
                recreate();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isChecked",isChecked);
        super.onSaveInstanceState(outState);
    }
}

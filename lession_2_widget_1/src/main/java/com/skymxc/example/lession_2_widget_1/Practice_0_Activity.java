package com.skymxc.example.lession_2_widget_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Practice_0_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_0_);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroupAnswer);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            /**
             *
             * @param radioGroup
             * @param i id  R类中的额id
             */
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (i==-1){
                    return ;
                }
                RadioButton radioButton = (RadioButton) findViewById(i);

                if (radioButton.getText().equals("2")){
                    Toast.makeText(Practice_0_Activity.this,"你真聪敏",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Practice_0_Activity.this,"傻吊",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

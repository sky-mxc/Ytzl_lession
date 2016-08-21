package com.skymxc.example.lession_2_widget_1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Practise_1_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practise_1_);


        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroupAnswer);
        Log.e("tag","==========="+radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {

                if (id==-1){
                    return;
                }
                RadioButton radioButton = (RadioButton) findViewById(id);

                if (!radioButton.getText().equals("2")){
                    Toast.makeText(Practise_1_Activity.this,"傻叉",Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(Practise_1_Activity.this,"慧眼",Toast.LENGTH_SHORT).show();
                radioGroup.setVisibility(View.GONE);
                ImageView image1 = (ImageView) findViewById(R.id.image2);
                image1.setVisibility(View.GONE);

                ImageView imageAnswer = (ImageView) findViewById(R.id.image1);
                imageAnswer.setImageResource(R.mipmap.car);

            }
        });
    }
}

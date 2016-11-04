package com.skymxc.lesson_41_jni;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import lesson_41_jni_demo.TestJni;

public class MainActivity extends AppCompatActivity {

    private  TestJni jni;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jni = new TestJni();
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.out:

                break;
            case R.id.add:

                Toast.makeText(this, "sum="+ jni.jniAdd(90,23), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

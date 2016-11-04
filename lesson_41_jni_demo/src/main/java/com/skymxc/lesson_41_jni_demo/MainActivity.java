package com.skymxc.lesson_41_jni_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TestJni testJni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testJni = new TestJni();
    }

    public void onClick(View v){
        int sum =testJni.jniAdd(90,2);
        Toast.makeText(this, "sum="+sum, Toast.LENGTH_SHORT).show();
    }
}

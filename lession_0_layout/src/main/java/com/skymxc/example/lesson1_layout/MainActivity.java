package com.skymxc.example.lesson1_layout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_table);


    }

    /**
     * 被点击事件
     * @param v
     */
    public void click(View v ){
        Toast.makeText(MainActivity.this,"被点击了",Toast.LENGTH_SHORT).show();
    }
}

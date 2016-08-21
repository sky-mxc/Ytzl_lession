package com.skymxc.example.lession_2_widget_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Practice_2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_2);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final TextView textView = (TextView) findViewById(R.id.text);

        String [] entries = {"全部","美国","中国","印度","阿拉伯","俄罗斯","法国"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Practice_2Activity.this,R.layout.list_item_simple,R.id.item,entries);

        Log.e("tag","================"+spinner.toString());
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                textView.setText("当前选种 第"+(position+1)+"项"+"，选中内容为："+adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}

package com.skymxc.example.lession_3_wedget_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteTextView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoCompleteTextView= (AutoCompleteTextView) findViewById(R.id.autoEdit);

        String [] city = {"alibaba","aha","ali","beijing","beida","bheze","菏泽","北京","北京城","菏泽牡丹"};

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_expandable_list_item_1,android.R.id.text1,city);

        autoCompleteTextView.setAdapter(adapter);


    }
}

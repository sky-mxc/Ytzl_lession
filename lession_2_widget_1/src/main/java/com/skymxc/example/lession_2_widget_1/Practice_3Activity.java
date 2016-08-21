package com.skymxc.example.lession_2_widget_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Practice_3Activity extends AppCompatActivity {

    private   ArrayAdapter<String> adapterTemp  = null;

    private  String[][] county = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_3);

        Spinner spinnerCity = (Spinner) findViewById(R.id.spinnerCity);
        final Spinner spinnerCounty = (Spinner) findViewById(R.id.spinnerCounty);

        String [] citris = {"北京","山东","天津"};

         county =new String[][]{
                {"海淀","昌平","朝阳","啥玩意"},
                {"菏泽","青岛","济南","over"},
                {"天津1","天津2","天津3","天津3"}};

        ArrayAdapter<String> adapterCity = new ArrayAdapter<String>(Practice_3Activity.this,R.layout.list_item_cascade,R.id.item,citris);
        ArrayAdapter<String> adapterCOunty = new ArrayAdapter<String>(Practice_3Activity.this,R.layout.list_item_cascade,R.id.item,county[0]);

        //设置适配器
        spinnerCity.setAdapter(adapterCity);
        spinnerCounty.setAdapter(adapterCOunty);

        //第一即 改变 选中项
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapterTemp = new ArrayAdapter<String>(Practice_3Activity.this,R.layout.list_item_cascade,R.id.item,county[i]);
                spinnerCounty.setAdapter(adapterTemp);
                Toast.makeText(Practice_3Activity.this,"选中项下标："+i+";选中项："+adapterView.getSelectedItem(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}

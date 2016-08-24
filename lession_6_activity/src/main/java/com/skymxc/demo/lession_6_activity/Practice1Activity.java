package com.skymxc.demo.lession_6_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class Practice1Activity extends AppCompatActivity {

    private Spinner spinner ;

    private ArrayList<String> entries ;

    private Button btremove ;

    private Button btAdd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice1);

        spinner = (Spinner) findViewById(R.id.spinner);
        btremove = (Button) findViewById(R.id.remove);
        btAdd = (Button) findViewById(R.id.add);

        entries = new ArrayList<String>();
        entries.add("java");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,entries);
        spinner.setAdapter(adapter);
    }

    /**
     * 点击事件
     * @param v
     */
    public void click(View v ){
        Intent intent =null;
        switch (v.getId()){
            case R.id.add:
                 intent = new Intent(this,Practice1InputActivity.class);
                intent.putExtra("opr","add");
                intent.putStringArrayListExtra("entries",entries);
                startActivityForResult(intent,1);
                break;
            case R.id.remove:
                 intent = new Intent(this,Practice1InputActivity.class);
                intent.putExtra("opr","remove");
                intent.putStringArrayListExtra("entries",entries);
                startActivityForResult(intent,2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            entries = data.getStringArrayListExtra("entries");
            ArrayAdapter adapter= new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,entries);
            spinner.setAdapter(adapter);

            if (entries.size()>=5){
                btAdd.setEnabled(false);
            }else{
                btAdd.setEnabled(true);
            }

            if (entries.size()==0){
                btremove.setEnabled(false);
            }else {
                btremove.setEnabled(true);
            }

        }
    }
}

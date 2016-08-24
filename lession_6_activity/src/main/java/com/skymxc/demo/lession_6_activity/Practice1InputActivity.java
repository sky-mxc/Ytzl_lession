package com.skymxc.demo.lession_6_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class Practice1InputActivity extends AppCompatActivity {

    private EditText edit;

    private String opr;

    private Button bt ;

    private ArrayList<String> entries ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice1_input);
        Intent intent = getIntent();
        bt = (Button) findViewById(R.id.save);
        entries = intent.getStringArrayListExtra("entries");
         opr = intent.getStringExtra("opr");
        if (opr.equals("add")){
            bt.setText("增加");
        }else{
            bt.setText("移除");
        }
        edit = (EditText) findViewById(R.id.edit);
    }

    public void  click(View v){
        switch (v.getId()){
            case R.id.save:

                String text= edit.getText().toString();
                if (text !=null && text.trim().length()>0){
                   if (opr.equals("add")){
                        if (entries.size()<5){
                            entries.add(text);
                        }
                   }else{
                       entries.remove(text);
                   }
                    Intent intent = new Intent();
                    intent.putStringArrayListExtra("entries",entries);
                    setResult(RESULT_OK,intent);
                }
                break;
            case R.id.cancel:
                setResult(RESULT_CANCELED);
                break;
        }
        finish();
    }
}

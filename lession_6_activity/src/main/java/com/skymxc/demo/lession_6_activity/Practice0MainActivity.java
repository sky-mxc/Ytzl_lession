package com.skymxc.demo.lession_6_activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Practice0MainActivity extends AppCompatActivity {

    private ImageView image ;

    private TextView tv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice0_main);
        image = (ImageView) findViewById(R.id.image);
    }

    /**
     * 点击事件
     * @param v 点击的谁
     */
    public void click(View v){
        Intent intent =null;
        switch (v.getId()){
            case R.id.image:
                Log.e("Tag","==========img==========");

                 intent = new Intent(Intent.ACTION_PICK);

                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent,1);

                break;
            case R.id.name:
                Log.e("Tag","=========name===========");
                 intent= new Intent(Practice0MainActivity.this,InputActivity.class);

                intent.putExtra("type","name");

                startActivityForResult(intent,2);

                break;
            case R.id.phone:
                Log.e("Tag","==========phone==========");
                intent =  new Intent(Practice0MainActivity.this,InputActivity.class);
                intent.putExtra("type","phone");
                startActivityForResult(intent,3);
                break;
            case R.id.email:
                Log.e("Tag","==========email==========");
                intent= new Intent(Practice0MainActivity.this,InputActivity.class);
                intent.putExtra("type","email");
                startActivityForResult(intent,4);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {

            if (requestCode == 1) {
                Uri uri = data.getData();
                image.setImageURI(uri);
            } else if(requestCode ==2){
                tv = (TextView) findViewById(R.id.name);
                String name = data.getStringExtra("text");
                tv.setText(name);
            }else if(requestCode ==3){
                tv = (TextView) findViewById(R.id.phone);
                String phone = data.getStringExtra("text");
                tv.setText(phone);
            }else if(requestCode ==4){
                tv = (TextView) findViewById(R.id.email);
                String email = data.getStringExtra("text");
                tv.setText(email);
            }
        }
    }
}

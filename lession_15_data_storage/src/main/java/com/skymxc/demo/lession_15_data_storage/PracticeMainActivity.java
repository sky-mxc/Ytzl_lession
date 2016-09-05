package com.skymxc.demo.lession_15_data_storage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;

public class PracticeMainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView image ;
    private TextView tvName;
    private TextView tvGender;
    private TextView tvAge;
    private SharedPreferences sp;
    //请求码
    private static final int REQUEST_INFO =1;
    private static final int REQUEST_PICK =2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_main);
        image = (ImageView) findViewById(R.id.image);
        tvAge = (TextView) findViewById(R.id.age);
        tvName = (TextView) findViewById(R.id.name);
        tvGender = (TextView) findViewById(R.id.gender);
         sp = getSharedPreferences("info",MODE_PRIVATE);
       init();

    }

    private void init(){
        //TODO 从SharedPreferences中获取一个 boolean（默认false） 值判断 SharedPreferences 中是否有了 信息

        boolean has = sp.getBoolean("has",false);
        //TODO 没有直接跳转至info输入， 有的话取出来显示
        if (has){
            String name = sp.getString("name","未填写");
            String gender = sp.getString("gender","未填写");
            String age = sp.getString("age","未填写");
            String uriStr = sp.getString("uri",null);
            tvAge.setText(age);
            tvName.setText(name);
            tvGender.setText(gender);
            Log.e("Tag","uriStr:"+uriStr);
            if (uriStr!=null){
//                Uri uri = Uri.parse(uriStr);
//                try {
//                   Bitmap bmp= BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
//                    image.setImageBitmap(bmp);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }

            }
        }else{
            Intent in = new Intent(this,PracticeInfoActivity.class);
            startActivityForResult(in,REQUEST_INFO);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.image:
                //TODO 从相册获取 图片显示在image
//                Intent in = new Intent(Intent.ACTION_PICK);
//                in.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(in,REQUEST_PICK);

               Intent intent= new Intent(Intent.ACTION_GET_CONTENT);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent,REQUEST_PICK);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //TODO 根据请求码 判断 是相册回来的还是 info
        switch (requestCode){
            case REQUEST_INFO:
                //TODO 从 SharedPreferences 中获取出has 查看是否已经存入，为true 就把 在info 存入的数据 显示出来
               if (sp.getBoolean("has",false)){
                   tvName.setText(sp.getString("name","未填写"));
                   tvAge.setText(sp.getString("age","未填写"));
                   tvGender.setText(sp.getString("gender","未填写"));
               }
                break;
            case REQUEST_PICK:
                //TODO 从 data 中 获取出图片 显示出来 将 图片uri 存入 SharedPreferences
                if (resultCode !=RESULT_OK)return;
               Uri uri = data.getData();
                Log.e("Tag","==uri:"+uri.toString()+"=====path:"+uri.getPath());
                test0(uri);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("uri",uri.toString());
                edit.apply();
                break;
        }

    }

    /**
     * 将 获取到的uri 转换为Bitmap
     * @param uri
     */
    private void test1(Uri uri) {
        try {
            Bitmap bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            image.setImageBitmap(bmp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将 获取到的 uri 转换为 Bitmap
     * @param uri
     */
    public void test0(Uri uri){
        try {
            Bitmap bmp= MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
            image.setImageBitmap(bmp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

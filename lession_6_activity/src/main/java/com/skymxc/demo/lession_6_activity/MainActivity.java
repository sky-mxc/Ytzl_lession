package com.skymxc.demo.lession_6_activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CheckBox cb ;

    private ImageView img ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cb = (CheckBox) findViewById(R.id.user_transpant_theme);
        img= (ImageView) findViewById(R.id.img);
        Log.e("Tag","====MainActivity=====onCreate================");
    }

    /**
     * 按钮点击
     * @param v
     */
    public void click(View v){
        switch (v.getId()){
            case R.id.to_second_activity:
                //创建意图 参数1 当前的 上下文环境 参数2 要调往的 activity
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                //传入参数数据
                intent.putExtra("use",cb.isChecked());//存放数据
                //启动activity
                startActivity(intent);
                break;
            case R.id.to_picture:
                Intent intent1 = new Intent(Intent.ACTION_PICK);
                intent1.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //为了结果去启动一个Activity
                startActivityForResult(intent1,50);
                break;
            case R.id.to_three_activity:
                Intent threeIntent = new Intent(MainActivity.this,ThreeActivity.class);
                startActivityForResult(threeIntent,100);
                break;
        }
    }

    /**
     *
     * @param requestCode 启动时的 requestCode
     * @param resultCode 跳转的activity给的
     * @param data 跳转的activity给的 返回的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== 50 && resultCode ==RESULT_OK && data != null){
           Uri uri = data.getData(); //返回是标识符指定图片所在的位置的统一资源标识符
            img.setImageURI(uri);  //设置给 ImageView
        }else if (requestCode ==100 && resultCode == RESULT_OK && data != null){
            //获取返回的字符串
            String str = data.getStringExtra("text");

            Toast.makeText(MainActivity.this,str,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Tag","===MainActivity========onStart==============");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Tag","===MainActivity========onResume==============");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Tag","===MainActivity======onPause================");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Tag","===MainActivity=========onStop=============");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Tag","===MainActivity==========onDestroy============");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("Tag","===MainActivity============onRestart==========");

    }
}

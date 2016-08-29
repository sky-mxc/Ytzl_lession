package com.skymxc.demo.practice;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    //url输入
    private EditText edit;
    //加载图片
    private ImageView img ;
    //圆形 图片
    private CircleImageView circleImage;
    //圆形图片旁边那个图片
    private ImageView imgTop;
    //进度条
    private ProgressBar pb ;
    //加载按钮
    private Button bt ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit = (EditText) findViewById(R.id.url);
        img = (ImageView) findViewById(R.id.load_image);
        circleImage = (CircleImageView) findViewById(R.id.top);
        imgTop = (ImageView) findViewById(R.id.top1);
        pb = (ProgressBar) findViewById(R.id.load_pb);
        bt = (Button) findViewById(R.id.to_load);

    }

    /**
     * 点击事件
     * @param view 触发对象
     */
    public void click(View view) {
        switch (view.getId()){
            case  R.id.to_load:
                Log.e("Tag","==当前进度值："+pb.getProgress());
                //加载图片
                String url= edit.getText().toString();
                Log.e("Tag","=========click===================url:"+url);
                if (url.trim().length()<=0){
                    Toast.makeText(this,"url 没输入啊",Toast.LENGTH_SHORT).show();
                    return;
                }
                MyTask myTask = new MyTask();
                myTask.execute(url);
                break;
            case R.id.top:
                Intent in =  new Intent(MainActivity.this,InfoActivity.class);
                Bitmap bmp= ((BitmapDrawable) circleImage.getDrawable()).getBitmap();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                //100是清晰度最大
                bmp.compress(Bitmap.CompressFormat.JPEG,100,bos);
                byte[] data = bos.toByteArray();
                in.putExtra("data",data);
                startActivity(in);
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getExtras()!=null){
            byte [] data = intent.getByteArrayExtra("data");
            Bitmap bmp= BitmapFactory.decodeByteArray(data,0,data.length);

            circleImage.setImageBitmap(bmp);
            imgTop.setImageBitmap(bmp);
        }
    }

    class  MyTask extends AsyncTask<String,Integer,Bitmap>{
        @Override
        protected void onPreExecute() {
            //设置加载按钮不可用
            bt.setEnabled(false);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlStr = strings[0];
            HttpURLConnection connection =null;
            try {
                URL url = new URL(urlStr);
                connection = (HttpURLConnection) url.openConnection();
                int code = connection.getResponseCode();
                Log.e("Tag","=========code========"+code);
                if (code==200){
                    InputStream is = connection.getInputStream();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    int currentLen =0;//当前进度
                    int totalSize = connection.getContentLength();//总大小
                    int length =-1;
                    byte[] b = new byte[5];
                    while ((length=is.read(b))!=-1){
                        currentLen+=length;
                       if (totalSize==0){
                           publishProgress(-1);
                       }else{
                           publishProgress((int)((float)currentLen/totalSize*100));
                       }
                        bos.write(b,0,length);
                    }

                    is.close();
                    bos.flush();
                    bos.close();
                    return BitmapFactory.decodeByteArray(bos.toByteArray(),0,bos.size());
                }else{
                    publishProgress(-1);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if (connection!=null){
                    connection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (values[0].intValue()==-1){
                /*Toast.makeText(MainActivity.this,"url地址不对哦0",Toast.LENGTH_SHORT).show();
                img.setImageResource(R.drawable.default_img);
                bt.setEnabled(true);*/
            }else{
                Log.e("Tag","========进度值："+values[0]);
                pb.setProgress(values[0]);
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap == null){
                Toast.makeText(MainActivity.this,"url地址不对哦1",Toast.LENGTH_SHORT).show();
                img.setImageResource(R.drawable.default_img);
                bt.setEnabled(true);
            }else{
                pb.setProgress(100);
                bt.setEnabled(true);
                img.setImageBitmap(bitmap);
            }
        }
    }
}

package com.skymxc.demo.lession_8_thread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

   private  ATM atm1;
    private ATM atm2;

    private Account account;

    private ImageView img ;

    private ImageView image;

    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (ImageView) findViewById(R.id.image);
        image = (ImageView) findViewById(R.id.image_task);
        pb = (ProgressBar) findViewById(R.id.pb);


        atm1 = new ATM("ATM1");
        atm2 = new ATM("ATM2");

         account = new Account();
        account.setMoney(1000);

        atm1.setAccount(account);
        atm1.takeMoney(900);
        atm2.setAccount(account);
        atm2.takeMoney(900);


    }

    public void click(View v){
        switch (v.getId()){
            case R.id.take_money:
                atm1.start();
                atm2.start();
                Log.e("Tag","账户余额："+account.getMoney());
                break;
            case R.id.reset_money:
                account.setMoney(1000);
                break;
            case R.id.load_image:
                        Log.e("Tag","开始进入线程");
                        loadImage();
                break;
            case R.id.load_image_use_task:
                //一个异步任务只能执行一次
                MyTask task = new MyTask();
                //执行任务
                task.execute("https://www.ibm.com/developerworks/cn/opensource/os-cn-android-actvt/image004.jpg");
                break;
        }
    }

    public void  loadImage(){
        new Thread(){
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"开始进入线程下载",Toast.LENGTH_SHORT).show();
                    }
                });

                HttpURLConnection conn =null;

                try {
                    //http://soubug.net/images/portal/index/top.jpg
                    URL url = new URL("http://soubug.net/images/portal/index/top.jpg");
                    //打开地址的连接 获取连接对象
                    conn=(HttpURLConnection) url.openConnection();
                    //设置网络访问方式 必须全大写字母  默认是get
                    conn.setRequestMethod("GET");
                    //网络读取超时时间  毫秒
                    conn.setReadTimeout(10000);
                    //连接超时时间
                    conn.setConnectTimeout(10000);

                    //如果是上传(网络输出操作)设置为true  但是必须是post请求
                    //conn.setDoOutput(true);
                    //从网络端读取数据 默认是true
                    conn.setDoInput(true);

                    //上传操作时使用 请求参数设置
//        conn.setRequestProperty("Content-Type","text/plain;charset=utf-8");
//        conn.setRequestProperty("Keep-Alive","true");

                    //获取网络访问 响应吗   200 ,404 505
                    int code = conn.getResponseCode();
                    Log.e("Tag","=========响应码"+code);
                    if (code==200){
                        //成功
                        //获取访问数据读取流
                        InputStream is = conn.getInputStream();
                        //通过位图工厂 解码出流中的图片
                        final Bitmap bitmap= BitmapFactory.decodeStream(is);
                        //寄送到主线程执行
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                img.setImageBitmap(bitmap);
                            }
                        });


                    }else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,"网络请求错误，错误码：",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (conn!=null){
                        //断开网络连接
                        conn.disconnect();
                    }
                }
            }
        }.start();


    }




    //parameter1 String 是doInBackground 的参数 返回值 是parameter3
    class  MyTask extends AsyncTask<String,Integer,Bitmap>{

        /**
         * 运行前初始化操作 param2
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"start ",Toast.LENGTH_SHORT).show();
        }

        /**
         * 后台执行玩的额结果
         * @param bitmap DoInbackground的结果
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap!=null){
                image.setImageBitmap(bitmap);
            }else{
                image.setImageResource(R.mipmap.ic_launcher);
            }
            pb.setIndeterminate(false);
            pb.setProgress(100);

        }

        /***
         * 后台操作
         * @param strings 与parameter 1 对应
         * @return parameter3
         */
        @Override
        protected Bitmap doInBackground(String... strings) {
            //通知 进度变化操作,
          //  publishProgress();//调用 onProgressUpdate方法
            HttpURLConnection connection = null;
            try {
                URL url = new URL(strings[0]);
                connection=(HttpURLConnection) url.openConnection();

                int code = connection.getResponseCode();

                if (code == 200){
                    //获取下载的流
                //   Bitmap bmp = BitmapFactory.decodeStream(connection.getInputStream());
                    InputStream is = connection.getInputStream();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    int length = -1;
                    int currentLen =0;      //当前读取的长度
                    int totalLen =connection.getContentLength();   //获取内容长度
                    byte [] b =new byte[5];
                    while ((length=is.read(b))!=-1){
                        currentLen+=length;         //累计读取的长度
                        if (totalLen == 0){
                            publishProgress(-1);
                        }else{
                            publishProgress((int)((float)currentLen/totalLen*100));
                        }

                        Thread.sleep(50);

                        bos.write(b,0,length);
                    }
                    return BitmapFactory.decodeByteArray(bos.toByteArray(),0,bos.size());
                }
            }catch (Exception e){

            } finally {
                if (connection!=null){
                    connection.disconnect();
                }
            }


            return null;
        }

        /**
         * 后台执行中，进度更新操作 ，不会自己调用
         * @param progress parameter2对应
         */
        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            if (progress[0]!=-1){
                pb.setIndeterminate(false);
                pb.setProgress(progress[0]);
            }else{
                //等于-1 设置为 不确定长度
                pb.setIndeterminate(true);
            }
            Toast.makeText(MainActivity.this,"start download in background",Toast.LENGTH_SHORT).show();
        }
    }


}





//直接 new Thread 重写其run方法
       /* new Thread(){
            @Override
            public void run() {
                for (int i =0;i<30;i++) {
                    Log.e("Tag", "=========new Thread==============");
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();*/


//第二种 传入 Runnable接口对象
      /*  new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i =0;i<30;i++) {
                    Log.e("Tag", "=========Runnable==============");
                    try {
                       Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();*/

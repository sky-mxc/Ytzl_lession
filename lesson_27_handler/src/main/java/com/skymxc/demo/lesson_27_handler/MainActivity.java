package com.skymxc.demo.lesson_27_handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //接受发送过来的数据
            switch (msg.what){
                case 1:
                    tv.setText(msg.obj.toString());
       //             tv2.setText(msg.getData().getString("str"));
                    break;
            }
        }
    };
    private TextView tv;
    private TextView tv2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv= (TextView) findViewById(R.id.text);
        tv2 = (TextView) findViewById(R.id.text2);
    }

    @Override
    public void onClick(View v) {
        new Thread(){
            @Override
            public void run() {
                try {
                    HttpURLConnection connection=null;
                    InputStream is=null;
                    ByteArrayOutputStream bos = null;
                    URL url = new URL("http://www.toolsmi.com/starclass/ver?ver=1");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(2000);
                    int code = connection.getResponseCode();
                    if(code == 200){

//                        //读取字符
//                        StringBuffer sb =new StringBuffer("");
//                        InputStreamReader reader = new InputStreamReader(is,"utf-8");
//                        BufferedReader br = new BufferedReader(reader);
//                        String line ;
//                        while ((line=br.readLine())!=null){
//                            sb.append(line);
//                        }

                        //方式
                       is= connection.getInputStream();
                        bos = new ByteArrayOutputStream();
                        byte [] bs = new byte[1024];
                        int len ;
                        while ((len=is.read(bs))!=-1){
                            bos.write(bs,0,len);
                        }
                        bs = bos.toByteArray();

                        Message msg = handler.obtainMessage();
                        msg.what=1 ;//标识操作
                        msg.obj= new String(bs);//封装 数据
                        Bundle bundle = new Bundle();
                      //  bundle.putString("str",sb.toString());
                        msg.setData(bundle);
                        handler.sendMessage(msg);   //发送给handler
                    }else{

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }.start();
    }
}

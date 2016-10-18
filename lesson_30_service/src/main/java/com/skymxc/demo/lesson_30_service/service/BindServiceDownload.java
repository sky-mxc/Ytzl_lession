package com.skymxc.demo.lesson_30_service.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.skymxc.demo.lesson_30_service.R;

/**
 * Created by sky-mxc
 */

public class BindServiceDownload extends AppCompatActivity implements View.OnClickListener{

    private ImageView image;
    private ProgressBar pb;
    private TextView tv;
    private String TAG ="BindServiceDownload";

    private MyService myService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        image = (ImageView) findViewById(R.id.image_download);
        pb = (ProgressBar) findViewById(R.id.download_progress);
        tv = (TextView) findViewById(R.id.file);
        Intent in = new Intent(this,MyService.class);
        bindService(in,conn,BIND_AUTO_CREATE);


    }


    private ServiceConnection conn = new ServiceConnection() {
        /**
         *
         * @param name
         * @param service Service bind时返回的 IBinder
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG,"=====onServiceConnected()==========ComponentName:"+name);
            myService=((MyService.MyBinder)service).getServiceInstance();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onClick(View v) {
        final String originUrl ="http://192.168.10.105:8080/tomcat.png";
        if (myService!=null){
            myService.download(originUrl, new MyService.OnDownloadListener() {
                @Override
                public void onBegin(String url) {
                    if (url.equals(originUrl)){
                        pb.setVisibility(View.VISIBLE);
                        Toast.makeText(BindServiceDownload.this,"开始加载",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFinished(String url, Bitmap bmp) {
                    if (url.equals(originUrl)){
                        pb.setVisibility(View.INVISIBLE);
                        Toast.makeText(BindServiceDownload.this,"加载完毕",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onProgressUpdate(String url, int progress) {
                    if (url.equals(originUrl)){
                        pb.setProgress(progress);
                    }
                }
            });
        }
    }



    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();

    }
}



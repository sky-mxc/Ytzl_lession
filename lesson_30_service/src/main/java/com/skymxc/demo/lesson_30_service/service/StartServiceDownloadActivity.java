package com.skymxc.demo.lesson_30_service.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.skymxc.demo.lesson_30_service.R;

import java.io.File;

public class StartServiceDownloadActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView image;
    private ProgressBar pb;
    private TextView tv;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        private static final String TAG = "  BroadcastReceiver tag";
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG,"===action:"+intent.getAction());
            if (intent.getAction().equals("com.mxc.example.download.begin")){
                //开始下载
                pb.setVisibility(View.VISIBLE);
                Toast.makeText(context,"开始下载",Toast.LENGTH_SHORT).show();
            }else if (intent.getAction().equals("com.mxc.example.download.update.progress")){
                //download
                int progress = intent.getIntExtra("progress",0);
                pb.setProgress(progress);

            }else if (intent.getAction().equals("com.mxc.example.download.over")){
                //download over
                pb.setVisibility(View.INVISIBLE);
                Toast.makeText(context,"下载完毕",Toast.LENGTH_SHORT).show();

                Bitmap bmp = intent.getParcelableExtra("bitmap");
                if (bmp!=null){
                    image.setImageBitmap(bmp);
                }
                File file = intent.getParcelableExtra("downloadFile");
                if (file!=null)
                    tv.setText(file.getPath());

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        image = (ImageView) findViewById(R.id.image_download);
        pb = (ProgressBar) findViewById(R.id.download_progress);
        tv = (TextView) findViewById(R.id.file);

        IntentFilter filter = new IntentFilter("com.mxc.example.download.begin");
        filter.addAction("com.mxc.example.download.update.progress");
        filter.addAction("com.mxc.example.download.over");

        registerReceiver(receiver,filter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.download:
                //start Service begin download
                Intent in = new Intent(this,MyService.class);
                in.putExtra("url","http://192.168.10.105:8080/tomcat.png") ;
                startService(in);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();

    }
}

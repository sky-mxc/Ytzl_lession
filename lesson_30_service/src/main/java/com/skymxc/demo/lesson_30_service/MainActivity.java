package com.skymxc.demo.lesson_30_service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.skymxc.demo.lesson_30_service.service.BindServiceDownload;
import com.skymxc.demo.lesson_30_service.service.MyService;
import com.skymxc.demo.lesson_30_service.service.StartServiceDownloadActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        Intent in = new Intent(this, MyService.class);
        switch (v.getId()){
            case R.id.start_Service:

                startService(in);
                break;
            case R.id.bind_Service:
                bindService(in, conn, Service.BIND_AUTO_CREATE);
                break;
            case R.id.unbind_Service:
                unbindService(conn);
                break;
            case R.id.stop_Service:
                stopService(in);
                break;
            case R.id.to_download:
                Intent intent = new Intent(this, StartServiceDownloadActivity.class);
                startActivity(intent);
                break;
            case R.id.to_bind_download:
                intent = new Intent(this, BindServiceDownload.class);
                startActivity(intent);
                break;
        }
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG,"=====onServiceConnected()==========ComponentName:"+name);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}

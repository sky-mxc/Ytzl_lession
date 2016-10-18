package com.skymxc.demo.lesson_29_notification;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class BindServiceDowloadActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG ="29 DowloadActivity tag";

    private ImageView image;
    private ProgressBar pb;
    private Messenger serviceMsg;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    //update progress
                    pb.setProgress(msg.arg1);
                    pb.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    //over
                    Bitmap bmp = (Bitmap) msg.obj;
                    image.setImageBitmap(bmp);
                    pb.setVisibility(View.INVISIBLE);
                    break;
            }
            return true;
        }
    });
    private Messenger clientMsg = new Messenger(handler);
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG,"====onServiceConnected==service:"+service+"==name:"+name);
            try {
                //建立连接后将客户端发送到 服务端  这样 双方都持有 客户端 和服务端
                serviceMsg = new Messenger(service);
                Message msg = Message.obtain();
                msg.what=0;
                msg.replyTo = clientMsg;
                serviceMsg.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_service_dowload);
        image = (ImageView) findViewById(R.id.image_download);
        pb = (ProgressBar) findViewById(R.id.progress);

        Intent intent = new Intent();
        intent.setClassName("com.skymxc.demo.lesson_30_service","com.skymxc.demo.lesson_30_service.service.MessengerService");

        bindService(intent,conn,BIND_AUTO_CREATE);

    }

    @Override
    public void onClick(View v) {
        //发送消息  开始下载
        try {
            Message msg = new Message();
            msg.what=2;

            Bundle b = new Bundle();
           b.putString("url","http://192.168.31.2:8080/tomcat.png");
            msg.setData(b);
            serviceMsg.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }
}

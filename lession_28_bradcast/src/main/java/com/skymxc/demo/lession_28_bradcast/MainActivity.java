package com.skymxc.demo.lession_28_bradcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private LocalBroadcastManager manager;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG,"====onReceiver=="+intent.getAction());
            Log.i(TAG,"Data:"+intent.getData()+"===type;"+intent.getStringExtra("type"));

            if (intent.getAction().equals("com.mxc.example.broadcast.normal.test")){

            }else if(intent.getAction().equals("com.mxc.example.broadcast.order.test")){
              //  abortBroadcast();//终止广播

                //添加额外的参数结果
                Bundle b = getResultExtras(true);
                b.putString("result","额外结果");
                setResultExtras(b);
            }else if(intent.getAction().equals("com.mxc.example.broadcast.sync.test")){
                Log.i(TAG,"==接受到 本地 同步广播");
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter intentFilter  = new IntentFilter("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("com.mxc.example.broadcast.normal.test");
        intentFilter.addAction("com.mxc.example.broadcast.order.test");
        intentFilter.addAction("com.mxc.example.broadcast.sync.test");
        intentFilter.addDataScheme("file");
        intentFilter.setPriority(999);
        //广播注册
        registerReceiver(receiver,intentFilter);

        /**
         * ===================================
         */
        //本地广播
        manager = LocalBroadcastManager.getInstance(this);
        manager.registerReceiver(receiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        manager.unregisterReceiver(receiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_broadcast:
                Log.d(TAG,"===send_broadcast==");
                Intent intent = new Intent("com.mxc.example.broadcast.normal.test");
                intent.putExtra("type","normal");
                intent.setData(Uri.parse("file:///sdcard/download/top.jpg"));
                sendBroadcast(intent);
                break;
            case R.id.send_order_broadcast:
                Log.d(TAG,"===send_broadcast==");
                 intent = new Intent("com.mxc.example.broadcast.order.test");
                intent.putExtra("type","order");
                intent.setData(Uri.parse("file:///sdcard/download/top.jpg"));
                sendOrderedBroadcast(intent,null);
                break;
            case R.id.send_local_broadcast:
                intent = new Intent("com.mxc.example.broadcast.sync.test");
                intent.putExtra("type","local.sync");
                intent.setData(Uri.parse("file:///sdcard/download/top.jpg"));
                manager.sendBroadcastSync(intent);
                break;
        }
    }
}

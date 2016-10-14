package com.skymxc.demo.lession_28_bradcast.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import static android.R.attr.priority;

/**
 * Created by sky-mxc
 */

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //此栈不允许 存在activity  所以要在新的栈启动
//        Intent in = new Intent(context, MainActivity.class);
//        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //在新的栈中启动
//        context.startActivity(in);

        String action = intent.getAction();
        Log.i("MyReceiver","---接收广播："+action+"==priority:"+priority);
        if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")){
            Log.e("MyReceiver","====开机广播接受到了");
        }else if(intent.getAction().equals("android.intent.action.SCREEN_ON")){
            Log.e("MyReceiver","==屏幕发生变化====");
        }else if(intent.getAction().equals("com.mxc.example.broadcast.order.test")){
            Bundle b = getResultExtras(true);
            Log.i("MyReceiver","====result:"+b.getString("result"));
        }else if(intent.getAction().equals("com.mxc.example.broadcast.sync.test")){
            Log.i("MyReceiver","===接到本地同步广播=");
        }
    }
}

package com.skymxc.lesson_45_jpush;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by sky-mxc
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Bundle b = intent.getExtras();
        switch (action){
            case "cn.jpush.android.intent.REGISTRATION":
                String id =b.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Log.i(TAG, "onReceive: REGISTRATION Id ="+id);
                Toast.makeText(context, "ID:"+id, Toast.LENGTH_SHORT).show();
                break;
            case "cn.jpush.android.intent.MESSAGE_RECEIVED":
                break;
            case "cn.jpush.android.intent.NOTIFICATION_RECEIVED":
                break;
            case "cn.jpush.android.intent.NOTIFICATION_OPENED":
                //上报通知 被打开 ，
                String nId = b.getString(JPushInterface.EXTRA_NOTIFICATION_ID);
                JPushInterface.reportNotificationOpened(context,nId);
                //获取通知标题
                String title = b.getString(JPushInterface.EXTRA_TITLE);
                //获取附加通知消息  json字符串
                String extra = b.getString(JPushInterface.EXTRA_EXTRA);
                Dialog dialog= new AlertDialog.Builder(context)
                        .setTitle(title)
                        .setMessage(extra)
                        .show();
                dialog    .getWindow()
                        .setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);  //这个窗体 当做 系统警告框弹出
                break;
            case "cn.jpush.android.intent.ACTION_RICHPUSH_CALLBACK":
                break;
            case "cn.jpush.android.intent.CONNECTION":
                break;
            default:
                Log.i(TAG, "onReceive: others="+action);
                break;

        }
    }

}

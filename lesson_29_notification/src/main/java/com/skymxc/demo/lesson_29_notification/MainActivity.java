package com.skymxc.demo.lesson_29_notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private  NotificationManager manager;
    private CheckBox cbAllDefault;
    private CheckBox cbSound ;
    private CheckBox cbVibrate;
    private CheckBox cbLight;
    private CheckBox cbFullScreen;
    private CheckBox cbContentIntent;
    private CheckBox cbAutoCancel;
    private CheckBox cbNoClear;
    private CheckBox cbBigContent;
    private CheckBox cbProgress;
    private CheckBox cbIndicator;
    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取 通知服务
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        cbAllDefault = (CheckBox) findViewById(R.id.default_hint);
        cbSound = (CheckBox) findViewById(R.id.sound);
        cbVibrate = (CheckBox) findViewById(R.id.vibrate);
        cbLight = (CheckBox) findViewById(R.id.light);
        cbFullScreen = (CheckBox) findViewById(R.id.fullscreen);
        cbContentIntent = (CheckBox) findViewById(R.id.content_intent);
        cbAutoCancel = (CheckBox) findViewById(R.id.auto_clean);
        cbNoClear = (CheckBox) findViewById(R.id.no_clean);
        cbBigContent  = (CheckBox) findViewById(R.id.big_content);
        cbProgress = (CheckBox) findViewById(R.id.progress);
        cbIndicator = (CheckBox) findViewById(R.id.indicator);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_notification:
                final Notification notification = createNotification();
               // if (cbAutoCancel.isChecked()) notification.flags |= Notification.FLAG_AUTO_CANCEL;
                if (cbNoClear.isChecked()) notification.flags |= Notification.FLAG_NO_CLEAR;
                //不好使
//                if (cbBigContent.isChecked()){
//                    RemoteViews views = new RemoteViews(getPackageName(),R.layout.layout_notification_big);
//                    views.setTextViewText(R.id.text,"文本");
//                    views.setTextColor(R.id.text,Color.YELLOW);
//                    views.setTextViewTextSize(R.id.text, TypedValue.COMPLEX_UNIT_SP,18);
//                    views.setImageViewResource(R.id.icon,R.mipmap.acj);
//
//                    notification.bigContentView=views;
//                }

                //进度条
               if (cbProgress.isChecked()){
                   cbProgress.post(new Runnable() {
                       @Override
                       public void run() {
                         Notification  notification = createNotification();
                          manager.notify(10,notification);
                           if (progress<=100) {
                               progress+=10;
                               cbProgress.postDelayed(this, 500);
                           }else{
                               progress=0;
                           }
                       }
                   });
               }

               manager.notify(10,notification);
                break;
            case R.id.cancel_notification:
                manager.cancel(10);
                break;
        }
    }

    /**
     * 创建 Notification
     * @return
     */
    private Notification createNotification() {
       Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),R.mipmap.acj);
        final Notification.Builder builder=  new Notification.Builder(this)
                .setLargeIcon(largeIcon)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("I'm Ticker")
                .setContentTitle("My Title")
                .setContentText("My content............");



        if (cbAllDefault.isChecked()){
            //设置默认的呼吸灯 震动 声音
            builder.setDefaults(Notification.DEFAULT_ALL);
//            builder.setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND);
        }

        //通知 铃声
        if (cbSound.isChecked()){
            File file = new File(Environment.getExternalStorageDirectory(),"Download/瑾澈.mp3");
            builder.setSound(Uri.fromFile(file));
        }

        if (cbVibrate.isChecked()){
            //震动500毫秒 停止 100毫秒 再震动 500毫秒 ，在停止100毫秒 再震动1000毫秒
            builder.setVibrate(new long[]{500,100,500,100,1000});
        }

        //不好使 呼吸灯
        if (cbLight.isChecked()){
            //参数1 灯的颜色，参数2 亮的时长，参数3 熄灭的时长
            builder.setLights(Color.YELLOW,500,1000);
        }

        //悬挂式
        if (cbFullScreen.isChecked()){
            PendingIntent pi = PendingIntent.getActivity(this,20,new Intent(this,MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);
            //参数2 是否高于优先级
            builder.setFullScreenIntent(pi,true);
        }

        //响应 Intent
        if (cbContentIntent.isChecked()){
            PendingIntent pi = PendingIntent.getActivity(this,30,new Intent(this,MainActivity.class),PendingIntent.FLAG_ONE_SHOT);
            builder.setContentIntent(pi);
        }

        //自动清除 响应后
        if (cbAutoCancel.isChecked()){
            builder.setAutoCancel(true);
        }

        //进度条
        if (cbProgress.isChecked()){

            if (progress<=100){
                builder.setProgress(100,progress,false);

            }

        }

        //指示器
        if (cbIndicator.isChecked()){
            builder.setProgress(0,0,true);
        }

        if (cbBigContent.isChecked()){
            builder.setStyle(new Notification.InboxStyle().setBigContentTitle("this BigContent Title").setSummaryText("BigContent Summary"));
        }




        return  builder.build();
    }
}

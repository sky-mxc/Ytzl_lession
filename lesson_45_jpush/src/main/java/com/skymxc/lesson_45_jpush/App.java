package com.skymxc.lesson_45_jpush;

import android.app.Application;
import android.app.Notification;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by sky-mxc
 */
public class App extends Application {
    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        //程序是否处于调试模式
        JPushInterface.setDebugMode(true);
        //初始话
        JPushInterface.init(this);


        //设置默认的通知样式
        BasicPushNotificationBuilder basieBuilder =  new BasicPushNotificationBuilder(this);
         basieBuilder.notificationDefaults = Notification.DEFAULT_ALL;
        basieBuilder.notificationFlags = Notification.FLAG_NO_CLEAR| Notification.FLAG_AUTO_CANCEL;
        basieBuilder.statusBarDrawable = R.mipmap.w5;       //状态栏图标
        JPushInterface.setDefaultPushNotificationBuilder(basieBuilder);
        //设置定义 通知样式
        JPushInterface.setPushNotificationBuilder(1,basieBuilder);

        CustomPushNotificationBuilder customBuilder = new CustomPushNotificationBuilder(this,R.layout.my_notification,R.id.icon,R.id.title,R.id.content);
        JPushInterface.setPushNotificationBuilder(2,customBuilder);

    }
}

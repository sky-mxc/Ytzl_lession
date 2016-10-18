package com.skymxc.demo.lesson_30_service.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sky-mxc
 */

public class MessengerService extends Service {
    private static final String TAG ="30 MessengerService tag";

    private Messenger serviceMsg;
    private Messenger clientMsg;//客户端Message对象

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return serviceMsg.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        serviceMsg = new Messenger(new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        clientMsg = msg.replyTo; //拿到客户端对象  ；双方都持有客户端和服务端
                        Log.i(TAG,"===handleMessage==拿到客户端对象");
                        break;
                    case 2:
                        Bundle b = msg.getData();
                        String url = b.getString("url");
                        Log.i(TAG,"=handleMessage==url:"+url);
                        download(url);
                        break;

                }
                return true;
            }
        }));
    }


    private void download(final String url){
        Log.i(TAG,"==download=  url:"+url);
        new Thread(){
            @Override
            public void run() {
                HttpURLConnection connection=null;
                InputStream is=null;
                ByteArrayOutputStream bos = null;

                try {
                    connection= (HttpURLConnection) new URL(url).openConnection();
                    connection.setDoOutput(true);
                    Log.i(TAG,"===responseCode:"+connection.getResponseCode());
                    if (connection.getResponseCode()==200){
                        is = connection.getInputStream();
                        //获取返回内容的长度
                        int total = connection.getContentLength();
                        int current = 0;

                        int len;
                        byte[] b = new byte[10];
                        bos = new ByteArrayOutputStream();
                        while ((len=is.read(b))!=-1){
                            current+=len;
                            Log.i(TAG,"=====download progress:"+current*100/total);

                            Message msgProgress = new Message();
                            msgProgress.arg1=current*100/total;
                            msgProgress.what=1;
                            try {
                                clientMsg.send(msgProgress);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }

                            bos.write(b,0,len);

                            Thread.sleep(1000*1);
                        }
                        b = bos.toByteArray();
                        Bitmap bmp = BitmapFactory.decodeByteArray(b,0,b.length);
//                        File file =new File(Environment.getExternalStorageDirectory(),"/temp/down.png");
//                        if (!file.exists()) file.createNewFile();
//                        bos.writeTo(new FileOutputStream(file));

                        Message msgOver = new Message();
                        msgOver.obj= bmp;
                        msgOver.what=2;
                        clientMsg.send(msgOver);


                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (RemoteException e) {
                    e.printStackTrace();
                } finally {

                    try {
                        if (is!=null){
                            is.close();
                        }
                        if (bos!=null){
                            bos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();

                    }


                }
                //下载完毕 停止服务
                stopSelf();
            }
        }.start();
    }

}

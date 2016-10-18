package com.skymxc.demo.lesson_30_service.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sky-mxc
 */

public class MyService extends Service {

    private static final String TAG ="MyService tag";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"=======onCreate()==========");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"=======onStartCommand()==========action:"+intent.getAction()+"=====startId:"+startId);
        String url = intent.getStringExtra("url");
        if (!TextUtils.isEmpty(url)){
            download(url);
        }
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"=======onDestroy()==========");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"=======onBind()==========");
        return new MyBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        //返回 true 才是真正的取消绑定
        Log.i(TAG,"=======onUnbind()==========");
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i(TAG,"=======onRebind()==========");
    }

    public int cal(int a,int b){
        return a+b;
    }

    /**
     * 绑定服务下载 方式
     * @param url 地址
     * @param onDownloadListener 下载监听
     */
    public void download(final String url,final OnDownloadListener onDownloadListener){
        Log.i(TAG,"==download:  url:"+url);

        new Thread(){
            //通过 handler 处理子线程 不能处理 UI的问题
            Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    if (onDownloadListener == null) return false;
                    Bundle data = msg.getData();
                    switch (msg.what){
                        case 0:
                          //开始下载
                            onDownloadListener.onBegin(data.getString("url"));
                            break;
                        case 1:
                            //正在下载
                            onDownloadListener.onProgressUpdate(data.getString("url"),data.getInt("progress"));
                            break;
                        case 2:
                           //下载完成
                            onDownloadListener.onFinished(data.getString("url"), (Bitmap) data.getParcelable("bitmap"));
                            break;
                    }
                    return true;
                }
            });
            @Override
            public void run() {
                HttpURLConnection connection=null;
                InputStream is=null;
                ByteArrayOutputStream bos = null;
                Intent intent = new Intent("com.mxc.example.download.begin");
                try {
                    connection= (HttpURLConnection) new URL(url).openConnection();
                    connection.setDoOutput(true);
                    Log.i(TAG,"===responseCode:"+connection.getResponseCode());
                    if (connection.getResponseCode()==200){
                        is = connection.getInputStream();
                        //获取返回内容的长度
                        int total = connection.getContentLength();
                        int current = 0;

//                        intent.putExtra("progress",current*100/total);
//                        sendBroadcast(intent);

                        //使用handler 发送 消息 完成从 worker线程 不能操作UI的问题
                        Message msg = new Message();
                        msg.what=0;
                        Bundle bd= new Bundle();
                        bd.putString("url",url);
                        msg.setData(bd);
                        handler.sendMessage(msg);


                        int len;
                        byte[] b = new byte[10];
                        bos = new ByteArrayOutputStream();
                        while ((len=is.read(b))!=-1){
                            current+=len;

//                            intent.setAction("com.mxc.example.download.update.progress");
//                            intent.putExtra("progress",current*100/total);
//                            Log.i(TAG,"=====download progress:"+current*100/total);
//                            sendBroadcast(intent);

                            Message msgUpdate = new Message();
                            msg.what=1;
                            Bundle bd2 = new Bundle();
                            bd2.putInt("progress",current*100/total);
                            bd2.putString("url",url);
                            msgUpdate.setData(bd2);
                            handler.sendMessage(msgUpdate);

                            bos.write(b,0,len);

                            Thread.sleep(300*1);
                        }
                        b = bos.toByteArray();
                        Bitmap bmp = BitmapFactory.decodeByteArray(b,0,b.length);
//                        File file =new File(Environment.getExternalStorageDirectory(),"/temp/down.png");
//                        if (!file.exists()) file.createNewFile();
//                        bos.writeTo(new FileOutputStream(file));

//                        intent.setAction("com.mxc.example.download.over");
//                        //如果bitmap过大 就无法传输 无法发送广播  可以考虑传输 字节byte[]
//                        intent.putExtra("bitmap",bmp);
//                        //    intent.putExtra("downloadFile",file);
//                        sendBroadcast(intent);

                        Message msgOver = new Message();
                        msgOver.what=2;
                        Bundle bd3 = new Bundle();
                        bd3.putParcelable("bitmap",bmp);
                        bd3.putString("url",url);
                        msgOver.setData(bd3);
                        handler.sendMessage(msgOver);

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
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

    /**
     * 下载
     * @param url
     */
    private void download(final String url){
        Log.i(TAG,"==download:  url:"+url);
        new Thread(){
            @Override
            public void run() {
                HttpURLConnection connection=null;
                InputStream is=null;
                ByteArrayOutputStream bos = null;
                Intent intent = new Intent("com.mxc.example.download.begin");
                try {
                    connection= (HttpURLConnection) new URL(url).openConnection();
                    connection.setDoOutput(true);
                    Log.i(TAG,"===responseCode:"+connection.getResponseCode());
                    if (connection.getResponseCode()==200){
                        is = connection.getInputStream();
                        //获取返回内容的长度
                        int total = connection.getContentLength();
                        int current = 0;

                        intent.putExtra("progress",current*100/total);
                        sendBroadcast(intent);


                        int len;
                        byte[] b = new byte[10];
                       bos = new ByteArrayOutputStream();
                        while ((len=is.read(b))!=-1){
                            current+=len;

                            intent.setAction("com.mxc.example.download.update.progress");
                            intent.putExtra("progress",current*100/total);
                            Log.i(TAG,"=====download progress:"+current*100/total);
                            sendBroadcast(intent);

                            bos.write(b,0,len);

                            Thread.sleep(1000*1);
                        }
                        b = bos.toByteArray();
                        Bitmap bmp = BitmapFactory.decodeByteArray(b,0,b.length);
//                        File file =new File(Environment.getExternalStorageDirectory(),"/temp/down.png");
//                        if (!file.exists()) file.createNewFile();
//                        bos.writeTo(new FileOutputStream(file));

                        intent.setAction("com.mxc.example.download.over");
                        //如果bitmap过大 就无法传输 无法发送广播  可以考虑传输 字节byte[]
                        intent.putExtra("bitmap",bmp);
                    //    intent.putExtra("downloadFile",file);
                        sendBroadcast(intent);

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
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


    public class MyBinder extends Binder{
        public MyService getServiceInstance(){
            return  MyService.this;
        }
    }

    /**
     * 下载监听
     */
    public interface OnDownloadListener {
        /**
         * 开始下载
         * @param url 下载地址
         */
        void onBegin(String url);

        /**
         * 完成下载
         * @param url 下载地址
         * @param bmp 下载的 Bitmap
         */
        void onFinished(String url, Bitmap bmp);

        /**
         * 下载进度
         * @param url 下载地址
         * @param progress 下载的进度
         */
        void onProgressUpdate(String url,int progress);
    }
}

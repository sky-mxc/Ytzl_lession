package com.skymxc.demo.lession_16_sqlite;

import android.os.AsyncTask;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sky-mxc
 */
public class HttpTask<T> extends AsyncTask<String,Void,T >{

    private Type type;//解析json时使用
    private OnResponseLintener<T> l;   //相应监听

    public HttpTask( TypeReference<T> type) {
        this.type =type.getType();
    }

    @Override
    protected T doInBackground(String... strings) {
        Log.e("Tag","url:"+strings[0]);
     //   String[] ary = strings[0].split("\\?");
        HttpURLConnection connection =null;
        OutputStream os =null;    //上传参数
        InputStream is =null;       //得到的输入流
        ByteArrayOutputStream bos =null;//字节数组
        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
//            connection.setDoOutput(true);
//            os =connection.getOutputStream();
//            os.write(ary[1].getBytes("utf-8"));
            //os.close();
            int code = connection.getResponseCode();
            Log.e("Tag","======code:"+code);
            if (code==200){
                is = connection.getInputStream();
                int len =-1;
                byte[] b = new byte[1024];
                bos = new ByteArrayOutputStream();
                while ((len=is.read(b))!=-1) {
                    bos.write(b,0,len);
                }
                bos.flush();
                bos.close();
                is.close();
                String json = bos.toString("utf-8");
                Log.e("Tag","json:"+json);
                T t = JSON.parseObject(json,type);
                return t;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (connection!=null){
                connection.disconnect();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(T t) {
        Log.e("Tag","=====t:"+t);
        if (l!=null){
            l.Response(t);
        }
    }


    public void setL(OnResponseLintener l) {
        this.l = l;
    }

    /**
     * 响应监听 接口
     * @param <T>
     */
    public interface  OnResponseLintener<T>{
        void Response(T t);
    }




}


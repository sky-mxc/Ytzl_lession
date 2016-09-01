package com.skymxc.demo.lession_12_practice;

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
public class HttpTask<T> extends AsyncTask<String,Void,T> {

    private Type type;

    private OnResponseLinstener<T> l;

    public HttpTask(Type type) {
        this.type=type;
    }

    public HttpTask(TypeReference<T> typeReference){
        this.type = typeReference.getType();
    }

    @Override
    protected T doInBackground(String... strings) {
        Log.e("Tag","======================url"+strings[0]);
        String[] strArr = strings[0].split("\\?");
        HttpURLConnection connection = null;
        OutputStream os = null;
        InputStream is =null;
        ByteArrayOutputStream bos =null;

        try {
            connection = (HttpURLConnection) new URL(strArr[0]).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            os= connection.getOutputStream();
            os.write(strArr[1].getBytes("utf-8"));//将参数通过流的方式写入 以utf-8的编码格式转换为 字节
            os.close();
            int code = connection.getResponseCode();
            if (code == 200){
                is = connection.getInputStream();
                byte[] b = new byte[1024];
                int len =-1;
                bos = new ByteArrayOutputStream();
                while ((len=is.read(b))!=-1){
                    bos.write(b,0,len);
                }
                bos.flush();
                String json = bos.toString("utf-8");
               T t= JSON.parseObject(json,type);
                Log.e("Tag","=====doInBackground==========="+t);
                return t;
            }else{
                Log.e("Tag","======getResponseCode() faild========"+code);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
             try {
                 if (bos!=null) bos.close();
                 if (is!=null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (connection != null){
                connection.disconnect();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);
        Log.e("Tag","=====onPostExecute==========="+t);
        if (l !=null) l.onResponse(t);

    }

    public void setOnResponseLinstener(OnResponseLinstener<T> l) {
        this.l = l;
    }

    public interface  OnResponseLinstener<T>{
        void onResponse(T t);
    }
}

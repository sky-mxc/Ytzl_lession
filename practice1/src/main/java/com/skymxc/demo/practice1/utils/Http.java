package com.skymxc.demo.practice1.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by sky-mxc
 */
public class Http {
    private RequestQueue queue;
    private Context mContext;
    private static Http http;

    private Http(Context context){
        this.mContext =context;
        this.queue = Volley.newRequestQueue(context);
    }

    /**
     * 获取实例
     * @param context
     * @return
     */
    public synchronized static Http getInstance(Context context){
        if (http==null){
            http = new Http(context);
        }
        return http;
    }

    /**
     * get请求
     * @param url
     * @param successListener
     * @param errorListener
     */
    public void get(String url,Response.Listener successListener,Response.ErrorListener errorListener){
        Log.e("Tag","====访问："+url);
        StringRequest request = new StringRequest(url,successListener,errorListener );
        queue.add(request);
    }


}

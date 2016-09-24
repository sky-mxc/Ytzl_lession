package com.skymxc.demo.lesson_24_tab.practice.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by sky-mxc
 * Volley 帮助类
 */

public class HttpHelper {

    private static final String TAG ="network.HttpHelper";

    private RequestQueue queue;
    private static HttpHelper httpHelper;

    private HttpHelper(Context context){
        queue = Volley.newRequestQueue(context);
    }

    public synchronized static HttpHelper getInstance(Context context){
        if (httpHelper==null){
            httpHelper = new HttpHelper(context);
        }
        return httpHelper;
    }

    /**
     * 执行 StringRequest
     * @param url 地址
     * @param successListtener 成功监听
     * @param errorListener 失败监听
     */
    public void executeStringRequest(String url, Response.Listener<String> successListtener, Response.ErrorListener errorListener){
        Log.e(TAG,"====url:"+url);
        StringRequest request = new StringRequest(url,successListtener,errorListener);
        queue.add(request);
    }
}

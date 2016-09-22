package com.skymxc.demo.lesson_23_viewpager.practice;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by sky-mxc
 */

public class HttpHelper {

    private RequestQueue queue;

    private Context mContext;

    private static HttpHelper httpHelper;

    public HttpHelper(Context context){
        this.mContext = context;
        queue = Volley.newRequestQueue(context);
    }

    public static synchronized  HttpHelper getInstance(Context context){
        if (httpHelper==null){
            httpHelper = new HttpHelper(context);
        }
        return  httpHelper;
    }

    public void executeRequest(String url, Response.Listener<String> resultListener, Response.ErrorListener errorListener){
        Log.e("HttpHelper","==executeRequest url:"+url);
        StringRequest request = new StringRequest(url,resultListener,errorListener);
        queue.add(request);
    }


}

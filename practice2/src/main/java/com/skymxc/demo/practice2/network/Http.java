package com.skymxc.demo.practice2.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by sky-mxc
 */
public class Http {
    private RequestQueue queue;
    private static Http http;
    private Http(Context context) {
        this.queue = Volley.newRequestQueue(context);
    }

    public static Http getInstance(Context context){
        if (http == null){
            http = new Http(context);
        }
        return  http;
    }

    public void post(String url, Response.Listener<String> successListener){
        StringRequest request = new StringRequest(Request.Method.POST, url, successListener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("Tag","=========onErrorResponse========="+volleyError);
            }
        });

        queue.add(request);
    }

}

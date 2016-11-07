package com.skymxc.lesson_42_http;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

public class VolleyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "url",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                    }
                }
                , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }

        });

        // 是否应该缓存
        stringRequest.setShouldCache(true);
        queue.add(stringRequest);
    }


    class  MyStringRequest extends StringRequest {

        private Map<String,String> params;

        public MyStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener,Map<String,String> params) {
            super(method, url, listener, errorListener);
            this.params = params;
        }

        public MyStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(url, listener, errorListener);
        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }
    }
}

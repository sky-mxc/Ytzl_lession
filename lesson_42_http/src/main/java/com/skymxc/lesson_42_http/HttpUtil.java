package com.skymxc.lesson_42_http;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by sky-mxc
 */
public class HttpUtil  {
    private static final String TAG = "HttpUtil";

    private static OkHttpClient client  = new OkHttpClient();

    public static  Call get(String url, Callback callback){
        return  request(url,callback,null);
    }

    public static Call post(String url,Callback callback,RequestBody body){
        return  request(url,callback,body);
    }

    protected static Call request(String url, Callback callback, RequestBody body){
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (body!=null){
            builder.post(body);
        }else{
            builder.get();
        }
       Call call = client.newCall(builder.build());
        call.enqueue(callback);
        return  call;
    }


}

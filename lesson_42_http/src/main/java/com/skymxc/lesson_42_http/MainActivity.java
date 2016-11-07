package com.skymxc.lesson_42_http;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static  final String TAG ="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //okhttp

        //创建网络访问请求
        //get
        //post 无文件上传

        RequestBody body =null;

        body = new FormBody.Builder()
                .add("pageIndex","1")
                .add("pageSize","3")
                .build();

        //post  有文件
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"),new File(""));
        body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)        //文件上传类型
                .addFormDataPart("pageIndex","2")
                .addFormDataPart("pageSize","10")   //普通字段
                .addFormDataPart("photo","a.jpg",fileBody)
                .build();

        Request.Builder builder  = new Request.Builder()
                .url("http://toolsmi.com/starclass/lessons");


        if (body != null){
            builder.post(body);
        }else{
            builder.get();
        }


        // 1.
        OkHttpClient httpClient = new OkHttpClient();

        // 2. 创建 网络调用对象
        Call call= httpClient.newCall(builder.build());

        //3. 执行网络访问
       // Response response = call.execute();  //同步网络访问，需要自己去开启线程
        call.enqueue(callback);     //异步执行
        //取消请求
//        call.cancel();
    }

    //方法运行在另外一个 线程 不能直接进行UI操作
    private Callback  callback = new Callback() {
        //fail
        @Override
        public void onFailure(Call call, IOException e) {
            Log.i(TAG, "onFailure: ");
        }

        //
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            int code =response.code();
            if (code  == 200){
                //返回字符串
                response.body().string();
                //字节数组
                response.body().bytes();
                //字节流
                response.body().byteStream();
                //字符流
                response.body().charStream();
            }
        }
    };
}

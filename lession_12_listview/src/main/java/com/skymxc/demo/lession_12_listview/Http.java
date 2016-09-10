package com.skymxc.demo.lession_12_listview;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by sky-mxc
 */
public class Http {
    private Context context ;


    //请求队列  最好只有一个
   private  RequestQueue queue = null;
    private static Http http;
    /**
     * 本身有缓存
     * 请求地址先经过缓存  是否有缓存数据
     * 如果有地址的缓存数据 直接将缓存返回给结果不会去请求
     * 没有地址的缓存  将请求 添加到 RequestQueun  进行网络请求
     * Volley  会遍历 队列 去完成请求
     * 会有线程限制
     * 有结果之后 先加入 缓存 然后再 显示
     *
     *
     *
     * @param conetxt
     */

    private Http(Context conetxt){
        this.context= conetxt;
        this.queue = Volley.newRequestQueue(conetxt);
    }


    public synchronized static Http getInstance(Context context){
        if (http ==null){
            http= new Http(context);
        }
        return http;
    }

    public  void get(String url, Response.Listener<String> listener ){

        //默认是GET方式访问  参数2 成功是监听 参数3 失败时监听
        StringRequest request = new StringRequest(url, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context,"error ："+volleyError.getMessage(),Toast.LENGTH_SHORT).show();
                //打印堆栈信息
                volleyError.printStackTrace();
                //标准写法
                Log.e("Tag","network error",volleyError);
            }
        });
        //设置是否缓存
        request.setShouldCache(false);
        //将请求添加到对列 等待处理
        queue.add(request);

    }

}

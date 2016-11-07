package com.skymxc.lesson_42_http;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by sky-mxc
 */
public abstract class JsonCallBack<T> implements Callback {
    private static final String TAG = "JsonCallBack";
    private static final int FAILURE = 0;
    private static final int SUCCESS = 10;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case FAILURE:
                    Result<T> result = (Result<T>) message.obj;
                    onFailure(result.call, (IOException) result.e);
                    break;
                case SUCCESS:
                    onSuccess((T) message.obj);
                    break;
            }
            return true;
        }
    });

    @Override
    public void onFailure(Call call, IOException e) {
        Result<T> result = new Result<>();
        result.call =call;
        result.code = 0;
        result.e=e;

        Message message = new Message();
        message.obj=result;
        message.what = FAILURE;
        message.sendToTarget();

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Result<T> result = new Result<>();
        result.code= response.code();
        try {
            if (result.code == 200){
                String json =response.body().string();
                Gson gson = new Gson();
                T t= gson.fromJson(json,new TypeToken<T>(){}.getType());

                Message msg = new Message();
                msg.what=SUCCESS;
                msg.obj=t;
                msg.sendToTarget();

            }else{
                if (result.code == 500){
                    throw new Exception("服务器端出现错误");
                }else if (result.code ==400){
                    throw new Exception("找不到地址");
                }else{
                    throw  new Exception("不知道啥错，反正不正常了");
                }
            }
        } catch (Exception e) {
            result.call =call;
            result.e =e;
            Message message = new Message();
            message.obj=result;
            message.what = FAILURE;
            message.sendToTarget();


        }

    }


    /**
     * 访问成功
     * @param t 结果
     */
    public abstract void onSuccess(T t);

    /**
     * 访问失败
     * @param call 网络执行对象
     * @param code 访问返回码
     * @param e 异常
     */
    public  void onFailure(Call call ,int code,Exception e){

    }

    private class Result<T>{
        public Call call;
        private Exception e;
        public int code;
        public T t;
    }
}

package com.skymxc.lesson_48_retrofit_eventbus;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.skymxc.lesson_48_retrofit_eventbus.entity.Result;
import com.skymxc.lesson_48_retrofit_eventbus.entity.Tab;
import com.skymxc.lesson_48_retrofit_eventbus.factory.MyConvertFactory;
import com.skymxc.lesson_48_retrofit_eventbus.service.TabService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static  final String TAG ="MainActivity";
    private   Retrofit retrofit;
    private   TabService tabService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         retrofit = new Retrofit.Builder()
                .baseUrl("http://mapi.univs.cn/mobile/")        //必须以 “/” 进行结尾
//                 .baseUrl("http://tollsmi.com/starclass/")
                 .addConverterFactory(new MyConvertFactory())
                .build();
      //创建 服务的实例
        tabService = retrofit.create(TabService.class);


        //注册要进行监听事件的类
        EventBus.getDefault().register(this);
    }

    private Callback<String> callback = new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            Log.e(TAG, "onResponse");
            if(response.isSuccessful()){
                Log.e(TAG, "onResponse: onResponsee"+response.body());
            }else{
                Log.e(TAG, "onResponse: code"+response.code());
            }
        }

        @Override
        public void onFailure(Call<String> call, Throwable throwable) {
            Log.e(TAG, "onFailure: ");
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.load:
                //获取网络请求的调用和你
                Call<String> call= tabService.loadJson();
                call.enqueue(callback);
                break;
            case R.id.load_query:
                //app=mobile&type=mobile&controller=content&content=content&action=
               call = tabService.loadJsonByQuery("mobile","mobile","content","category","content");
                call.enqueue(callback);
                    break;
            case R.id.load_map:
                Map<String ,String> map = new HashMap<>();
                map.put("app","mobile");
                map.put("type","mobile");
                map.put("controller","content");
                map.put("action","category");
                map.put("content","content");
                call = tabService.loadJsonByQueryMap(map);
                call.enqueue(callback);
                break;
            case R.id.up_photo:
                File file = new File(Environment.getExternalStorageDirectory(),"download/图片1.png");
                RequestBody uid = RequestBody.create(MediaType.parse("multipart/form-data"),"1");
                RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
                call = tabService.upPhoto(uid,fileBody);
                call.enqueue(callback);
                break;
            default:
                uid = RequestBody.create(MediaType.parse("multipart/form-data"),"1");
                file = new File(Environment.getExternalStorageDirectory(),"download/图片1.png");
                 fileBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("photo","图片1.png",fileBody);
                call = tabService.upPhoto2(uid,part);
                call.enqueue(callback);
                break;
            case R.id.load_tabs:
                Call<Result<List<Tab>>> resultCall = tabService.loadTab();
                resultCall.enqueue(resultCallBack);
                break;
        }
    }


    private Callback<Result<List<Tab>>> resultCallBack = new Callback<Result<List<Tab>>>() {
        @Override
        public void onResponse(Call<Result<List<Tab>>> call, Response<Result<List<Tab>>> response) {
            Log.e(TAG, "onResponse: code"+response.isSuccessful());
            //发送消息
            EventBus.getDefault().post(response.body());

        }

        @Override
        public void onFailure(Call<Result<List<Tab>>> call, Throwable throwable) {
            Log.e(TAG, "onFailure: "+throwable.getMessage());
        }
    };

    //2.创建消息对象 可以是任意的object
    //3. 创建事件方法

    /**
     * 靠注解识别方法
     * 默认和发起人在同一线程 也可以指定线程     * threadMode 指明方法运行的线程模式
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Result<List<Tab>> result){
        Toast.makeText(this, "====="+result.data.get(0).getCatname(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册
        EventBus.getDefault().unregister(this);
    }
}

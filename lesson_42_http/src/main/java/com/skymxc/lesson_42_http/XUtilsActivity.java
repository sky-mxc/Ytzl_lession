package com.skymxc.lesson_42_http;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.HttpManager;
import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.http.body.FileBody;
import org.xutils.http.body.MultipartBody;
import org.xutils.http.body.RequestBody;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XUtilsActivity extends AppCompatActivity {

    @ViewInject(android.R.id.text1)
    private TextView textView ;

    private HttpManager httpManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xutils);
        x.view().inject(this);

        //http
        httpManager = x.http();
        RequestParams params = new RequestParams("url");
        params.setMethod(HttpMethod.GET);
        httpManager.get(params,callback);
        //post
        List<KeyValue> list = new ArrayList<>();
        list.add(new KeyValue("pageIndex","1"));
        list.add(new KeyValue("pageSize","10"));
        try {
            list.add(new KeyValue("photo",new FileBody(new File(""),"image/*")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        RequestBody body = new MultipartBody(list,"utf-8");
        params = new RequestParams("url address");
        params.setMethod(HttpMethod.POST);
        params.setRequestBody(body);
        httpManager.post(params,callback);

        //image

        //图片加载设置
//        ImageOptions options = new ImageOptions.Builder()
//                .setLoadingDrawable()   //记载中的图片
//                .setSize()                  //加载后图片的缩放尺寸
//                .setFailureDrawableId()     //失败图片
//                .setFadeIn(true)            //淡入
//                .setRadius()                //圆角
//                .setAnimation()             //动画
//                .build();

//        x.image().bind(new ImageView(this),"http://",options);
        x.image().bind(new ImageView(this),"file:///android_assets/");
        x.image().bind(new ImageView(this),"file:///sdcard/");


        //DBManager

//        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig();
//        daoConfig.setDbName();
//        daoConfig.setDbVersion();
//        daoConfig.setAllowTransaction(true);
//
//        x.getDb(daoConfig).deleteById();
//        x.getDb(daoConfig).delete();
//        x.getDb(daoConfig).execQuery();
//        x.getDb(daoConfig).findFirst();
//        x.getDb(daoConfig).findById();
//        x.getDb(daoConfig).findAll();
//        x.getDb(daoConfig).selector().limit().findAll();
//        x.getDb(daoConfig).save();
//        x.getDb(daoConfig).update();
//        x.getDb(daoConfig).saveOrUpdate();
    }

    private Callback.CommonCallback<String> callback = new Callback.CommonCallback<String>() {
        @Override
        public void onSuccess(String s) {

        }

        @Override
        public void onError(Throwable throwable, boolean b) {

        }

        @Override
        public void onCancelled(CancelledException e) {

        }

        @Override
        public void onFinished() {

        }
    };

    //修饰符 必须私有
    @Event(value = {android.R.id.text1},type = View.OnClickListener.class)
    private void onClick(View v){

    }
}

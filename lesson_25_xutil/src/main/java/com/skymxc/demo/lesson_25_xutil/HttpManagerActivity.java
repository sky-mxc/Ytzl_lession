package com.skymxc.demo.lesson_25_xutil;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.http.body.FileBody;
import org.xutils.http.body.MultipartBody;
import org.xutils.http.body.RequestBody;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.xutils.x.http;

@ContentView(R.layout.activity_http_manager)
public class HttpManagerActivity extends AppCompatActivity {

    @ViewInject(R.id.text)
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

    }

    @Event(value = {R.id.get,R.id.post,R.id.download})
    private void click(View view){
        switch (view.getId()){
            case R.id.get:
                RequestParams rp = new RequestParams("http://mapi.univs.cn/mobile/index.php?controller=content&action=category&app=mobile&catid=11&page=1");
                rp.setConnectTimeout(10000);
                http().get(rp, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        text.setText(s);
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        text.setText("=====Error:"+throwable.getMessage());
                    }

                    @Override
                    public void onCancelled(CancelledException e) {
                        text.setText("cancel");
                    }

                    @Override
                    public void onFinished() {

                    }
                });
                break;
            case R.id.post:
                try {
                    rp = new RequestParams("http://www.toolsmi.com/starclass/upphoto");
                    rp.setConnectTimeout(20000);
//                    //参数 文件不上传
                    List<KeyValue> kvs = new ArrayList<>();
                    kvs.add(new KeyValue("u.uid","50"));
//                    RequestBody body = new UrlEncodedParamsBody(kvs,"utf-8");
                    //文件上传
                    File file = new File(Environment.getExternalStorageDirectory()+"/Download/dog.jpg");
                    kvs.add(new KeyValue("upphoto",new FileBody(file,"image/*")));
                    RequestBody body = new MultipartBody(kvs,"utf-8");
                    rp.setRequestBody(body);

                    http().post(rp, new Callback.CommonCallback<String>() {
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
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.download:
                //https://codeload.github.com/wyouflf/xUtils3/zip/master
                //https://github.com/wyouflf/xUtils/archive/master.zip
                rp= new RequestParams("https://github.com/wyouflf/xUtils/archive/master.zip");
                rp.setSaveFilePath(Environment.getExternalStorageDirectory()+"/Download/master.zip");
                rp.setAutoRename(true);//下载完成后如果可以得到文件名 则自动更改文件名
                rp.setAutoResume(true);     //断点续传

            Callback.Cancelable cacelable= x.http().get(rp, new Callback.ProgressCallback<File>() {
                @Override
                public void onSuccess(File file) {
                    text.setText("download success");
                }

                @Override
                public void onError(Throwable throwable, boolean b) {
                    text.setText("download error");
                }

                @Override
                public void onCancelled(CancelledException e) {
                    text.setText("download cancel");
                }

                @Override
                public void onFinished() {
                    text.setText("download finish");
                    Log.e("HttpManagerActivity","======download finish:");
                }

                @Override
                public void onWaiting() {
                    text.setText("download wait");
                }

                @Override
                public void onStarted() {
                    text.setText("download start");
                    Log.e("HttpManagerActivity","======download start:");
                }

                /**
                 *
                 * @param l 总尺寸
                 * @param l1 当前尺寸
                 * @param b 是否正在下载
                 */
                @Override
                public void onLoading(long l, long l1, boolean b) {
                   // Log.e("HttpManagerActivity","======loading:"+(l1*100/1));
                    if (b){
                        //loading
                        text.setText("download loading:"+(l1*100/l)+"%");  //当前值*100/总
                        Log.e("HttpManagerActivity","======loading  size:"+l+",,,进度值:"+l1);
                    }else{

                    }

                }
            });
//                if (!cacelable.isCancelled()){//未被取消
//                    cacelable.cancel(); //取消
//                }
                break;
        }
    }
}

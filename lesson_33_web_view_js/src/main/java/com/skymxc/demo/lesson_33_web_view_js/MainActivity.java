package com.skymxc.demo.lesson_33_web_view_js;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity tag";

    private WebView webView;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        webView= (WebView) findViewById(R.id.web_view);
        pb = (ProgressBar) findViewById(R.id.progress);

        //加载网页的方法
        //url
        //  webView.loadUrl("http://192.168.23.58:8080");
         // webView.loadUrl("http://baidu.com");
        //加载网页代码
//        String html ="<h1>title</h1>" +
//                "<center>"+
//                "去往 <a href =\"http://192.168.23.58:8080\">tomcat</a> 首页" +
//                "<br/>" +
//                "</center>" +
//                "<img src=\"docs/images/asf-logo.gif\"/>";
        //  webView.loadData(html,"text/html;charset=utf-8","utf-8");
        String data = getIndex();


        //加载 以url为基准的数据
        webView.loadDataWithBaseURL("file:///android_asset/",data,"text/html","utf-8",null);
        webView.setWebViewClient(viewClient);
        webView.setWebChromeClient(chromeClient);
        //支持js交互
        webView.getSettings().setJavaScriptEnabled(true);

        //设置默认编码方式
        webView.getSettings().setDefaultTextEncodingName("utf-8");

         webView.getSettings().setDefaultFontSize(20);
        //背景色
        webView.setBackgroundColor(Color.GRAY);

        //自动缩放

        //添加JavaScript接口 可以让调用 本地接口
        webView.addJavascriptInterface(new JsInterface(),"android_math");


    }

    //主要是辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等  侧重于 JavaScript 内容的事件处理
    private WebChromeClient chromeClient = new WebChromeClient(){

        //网页加载进度显示
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            Log.i(TAG, "onProgressChanged: progress="+newProgress);
            if (newProgress!=100){
                pb.setVisibility(View.VISIBLE);
                pb.setProgress(newProgress);
            }else{
                pb.setVisibility(View.GONE);
            }
        }
        //处理JavaScript confirm 对话框
        @Override
        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("确认提示")
                    .setMessage(message)
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm();
                        }
                    })
                    .setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.cancel();
                        }
                    })
                    .show();

            return true;
        }



        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            //确认交互完成 提交结果 勿忘
            result.confirm();
            return true;
        }


    };


    //主要处理WebView的各种请求通知 侧重于 对 浏览器本身事件重写
    private WebViewClient viewClient = new WebViewClient(){

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i(TAG, "shouldOverrideUrlLoading: url="+url);
            //当打开一个新的 url 时在本view打开，不跳转至浏览器
            view.loadUrl(url);
            return true;
        }


    };


    /**
     *  加载 assets目录下文件
     * @return
     */
    public String getIndex() {
        InputStream  is =null;
        BufferedReader reader = null;
        try {
             is = getAssets().open("index.html");
             reader = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();
            while (reader.ready()){
                sb.append(reader.readLine().trim());
            }
            Log.i(TAG, "getIndex: html="+sb.toString());
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

                try {
                     if (is!=null){
                         is.close();
                     }
                    if (reader!=null){
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();

            }
        }
        return "";
    }

    @Override
    public void onClick(View v) {
        webView.loadUrl("javascript:haha('蛋蛋')");
    }

    public class  JsInterface{

         //对于 4.0以上必须加上注解
        @JavascriptInterface
        public  int add( int a ,int b){
            return  a+b;
        }
    }
}

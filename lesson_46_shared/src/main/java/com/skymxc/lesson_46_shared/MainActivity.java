package com.skymxc.lesson_46_shared;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qzone.QZone;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
    private String TAG ="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ShareSDK.initSDK(this); //init
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.shared:
                share();
                break;
            case R.id.shared_to_qzone:
                Log.e(TAG, "onClick:  shared_to_qzone");
                //获取指定平台的platform对象
                Platform platform = ShareSDK.getPlatform(QZone.NAME);
                if (platform.isClientValid()) {
                    //设置分享操作的监听
                    platform.setPlatformActionListener(actionLis);
                    //分享
                    platform.share(getQZoneParams());
                }else{
                    Toast.makeText(this, "没有安装客户端", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 分享到 QZone
     */
    private PlatformActionListener actionLis = new PlatformActionListener() {
        //分享成功
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            Toast.makeText(MainActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            Toast.makeText(MainActivity.this, "分享失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(Platform platform, int i) {
            Toast.makeText(MainActivity.this, "取消分享", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 分享
     */
    private void share() {
        OnekeyShare oks = new OnekeyShare();
        //禁用 SSO授权
        oks.disableSSOWhenAuthorize();
        //设置分享内容
        oks.setTitle("分享测试哦。。。");
        oks.setText("what's  love ?  It's a feel;");
        oks.setSite("sky-c");             //分享者的名称
        oks.setSiteUrl("https://sky-mxc.github.io");
        oks.setImageUrl("https://sky-mxc.github.io/images/top.jpg");
        //显示分享平台选择对话框
        oks.show(this);
    }

    /**
     *
     * @return
     */
    public Platform.ShareParams getQZoneParams() {
        QZone.ShareParams sp = new QZone.ShareParams();
        sp.setImageUrl("https://sky-mxc.github.io/images/top.jpg");
        sp.setTitle("love");
        sp.setText("what  love ?  It's a feel;");
        sp.setSiteUrl("https://sky-mxc.github.io");
        sp.setTitleUrl("https://sky-mxc.github.io");
        Log.i(TAG, "getQZoneParams: ");
        return sp;
    }
}

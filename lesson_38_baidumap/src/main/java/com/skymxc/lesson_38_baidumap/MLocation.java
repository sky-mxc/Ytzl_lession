package com.skymxc.lesson_38_baidumap;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * Created by sky-mxc
 */
public class MLocation {
    private static final String TAG = "MLocation";

    public static final String RECEIVER_LOCATION = "com.skymxc.action.location";
    private LocationClient client;
    private Context  mContext;
    public MLocation(Context context){
        this.mContext = context;
        //初始化定位客户端
        client = new LocationClient(context);
        //开始定位
        client.start();
        //注册一个位置监听
        client.registerLocationListener(locLis);
        initOption(client);
    }

    /**
     * 定位客户端初始化
     * @param client
     */
    private void initOption(LocationClient client) {
        //客户端设置对象
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");       //坐标类型  百度的坐标类型
        option.setOpenGps(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);       //定位模式
        option.setScanSpan(1000*5);         //设置扫描间隔  在1000ms以上
        client.setLocOption(option);
    }

    //位置监听
    private BDLocationListener locLis = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            Log.i(TAG, "onReceiveLocation: ");
            //发送广播
            Intent intent = new Intent(RECEIVER_LOCATION);
            intent.putExtra("loc",bdLocation);
            mContext.sendBroadcast(intent);
        }
    };

    /**
     * 停止定位
     */
    public void stop(){
        if (client!=null){
            client.unRegisterLocationListener(locLis);
            client.stop();
        }
    }


}

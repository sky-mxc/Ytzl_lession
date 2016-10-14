package com.skymxc.demo.lession_28_bradcast.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.sql.Date;

/**
 * Created by sky-mxc
 * 接收短信广播
 */

public class MsgReceiver extends BroadcastReceiver {
    private static final String TAG ="MsgReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"==接到短信广播=="+intent.getAction());
      //  intent.getParcelableExtra()
        Bundle bundle = intent.getExtras();
        Toast.makeText(context,"hahahahhaha",Toast.LENGTH_LONG).show();

        Object[] pdus = (Object[]) bundle.get("pdus");
        SmsMessage[] sms = new SmsMessage[pdus.length];
        for(int i=0;i<pdus.length;i++){
            sms[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            Log.e(TAG,"=========Sms body:"+sms[i].getDisplayMessageBody());
            Log.e(TAG,"=========Sms originationAddress (发信人):"+sms[i].getDisplayOriginatingAddress());
            Log.e(TAG,"=========Sms time （时间）："+new Date(sms[0].getTimestampMillis()));
            SmsManager manager = SmsManager.getDefault();
            //短信 参数1收信人，2 短信中心号，3 内容 4. 发送成功通知，5接收成功通知
            manager.sendTextMessage(sms[i].getDisplayOriginatingAddress(),null,"你是不是傻（短信测试）",null,null);

            //彩信
          //  manager.sendDataMessage();
        }


    }
}

package com.skymxc.demo.lession_28_bradcast.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by sky-mxc
 */

public class NetworkReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"网络变化咯",Toast.LENGTH_SHORT).show();
    }
}

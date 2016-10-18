package com.skymxc.demo.lesson_30_service.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by sky-mxc
 */

public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}

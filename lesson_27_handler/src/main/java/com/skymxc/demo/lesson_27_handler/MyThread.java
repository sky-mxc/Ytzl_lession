package com.skymxc.demo.lesson_27_handler;

import android.os.Handler;

/**
 * Created by sky-mxc
 */

public class MyThread extends Thread {

    private Handler handler;
    public MyThread(Handler handler){
        this.handler =handler;
    }


    @Override
    public void run() {
        handler.sendEmptyMessage(1);
    }
}

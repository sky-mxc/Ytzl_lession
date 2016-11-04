package com.skymxc.lesson_41_jni_demo;

/**
 * Created by sky-mxc
 */
public class TestJni {
    private static final String TAG = "TestJni";

    static {
        System.loadLibrary("my");
    }

    public native  int jniAdd(int num1,int num2);


}

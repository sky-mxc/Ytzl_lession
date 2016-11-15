package com.skymxc.lesson_48_retrofit_eventbus.entity;

/**
 * Created by sky-mxc
 */
public class Result<T> {
    public T data;
    public boolean state;
    public  String message;
    public boolean more;
    public long time;
}

package com.skymxc.demo.lesson_24_tab.practice.entity;

/**
 * Created by sky-mxc
 * 请求结果实体类
 */

public class Result<T> {
    private boolean state;
    private boolean more;
    private long time;
    private T data;
    private String message;

    public void setState(boolean state) {
        this.state = state;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isState() {
        return state;
    }

    public boolean isMore() {
        return more;
    }

    public long getTime() {
        return time;
    }

    public T getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {

        return message;
    }
}

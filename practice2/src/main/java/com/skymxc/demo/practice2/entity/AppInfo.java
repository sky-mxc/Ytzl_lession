package com.skymxc.demo.practice2.entity;

/**
 * Created by sky-mxc
 */
public class AppInfo {
    private String appUrl;
    private int versionCode;
    private String versionName;
    private int vid;

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public int getVid() {
        return vid;
    }
}

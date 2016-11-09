package com.skymxc.lesson_44_media;

/**
 * Created by sky-mxc
 */
public class MediaBean {
    private static final String TAG = "MediaBean";

    private int id;
    private String name;
    private String path;

    public MediaBean() {
    }

    public MediaBean(int id, String name, String path) {

        this.id = id;
        this.name = name;
        this.path = path;
    }

    public void setId(int id) {

        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static String getTAG() {

        return TAG;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}

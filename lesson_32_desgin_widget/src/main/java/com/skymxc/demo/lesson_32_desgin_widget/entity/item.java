package com.skymxc.demo.lesson_32_desgin_widget.entity;

/**
 * Created by sky-mxc
 */
public class Item {

    private String icon;
    private String text1;
    private String text2;
    private int type;
    private int progress;

    public Item() {
    }

    public Item(String icon, String text1, String text2, int type) {
        this.icon = icon;
        this.text1 = text1;
        this.text2 = text2;
        this.type = type;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public String getText1() {
        return text1;
    }

    public String getText2() {
        return text2;
    }

    public int getType() {
        return type;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}

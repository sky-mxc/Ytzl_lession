package com.skymxc.demo.lesson_24_tab.practice.entity;

/**
 * Created by sky-mxc
 * 新闻实体类
 */

public class News {
    private int contentid;
    private int modelid;
    private String title;
    private String thumb;
    private String description;
    private int comments;
    private  long sorttime;

    public void setContentid(int contentid) {
        this.contentid = contentid;
    }

    public void setModelid(int modelid) {
        this.modelid = modelid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public void setSorttime(long sorttime) {
        this.sorttime = sorttime;
    }

    public int getContentid() {

        return contentid;
    }

    public int getModelid() {
        return modelid;
    }

    public String getTitle() {
        return title;
    }

    public String getThumb() {
        return thumb;
    }

    public String getDescription() {
        return description;
    }

    public int getComments() {
        return comments;
    }

    public long getSorttime() {
        return sorttime;
    }
}

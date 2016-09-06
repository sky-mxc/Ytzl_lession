package com.skymxc.demo.lession_16_sqlite;

/**
 * Created by sky-mxc
 */
public class News {
    private  int contentid;
    private  int modelid  ;
    private  String title;
    private  String thumb;
    private  String description;
    private  String sorttime;
    private int comments;

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getComments() {
        return comments;
    }

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

    public void setSorttime(String sorttime) {
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

    public String getSorttime() {
        return sorttime;
    }
}

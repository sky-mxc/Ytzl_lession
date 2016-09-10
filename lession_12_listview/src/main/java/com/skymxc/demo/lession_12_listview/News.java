package com.skymxc.demo.lession_12_listview;

/**
 * Created by sky-mxc
 */
public class News {
    private Long contentid;
    private Integer modelid;
    private String title;
    private String thumb;
    private String description;
    private Integer comments;
    private Long sorttime;

    public void setContentid(Long contentid) {
        this.contentid = contentid;
    }

    public void setModelid(Integer modelid) {
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

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public void setSorttime(Long sorttime) {
        this.sorttime = sorttime;
    }

    public Long getContentid() {
        return contentid;
    }

    public Integer getModelid() {
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

    public Integer getComments() {
        return comments;
    }

    public Long getSorttime() {
        return sorttime;
    }
}

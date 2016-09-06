package com.skymxc.demo.lession_16_sqlite;

/**
 * Created by sky-mxc
 * 新闻内容
 */
public class NewsContent {
   private  int contentid;
    private  int modelid  ;
    private  String title;
    private  String thumb;
    private  String description;
    private  String sorttime;


   private  int topicid;
   private  int allowcomment;
   private  long published;
   private  String source ;
   private  String shareurl;
   private  String content;

    public void setContentid(int contentid) {
        this.contentid = contentid;
    }

    public void setModelid(int modelid) {
        this.modelid = modelid;
    }

    public void setTopicid(int topicid) {
        this.topicid = topicid;
    }

    public void setAllowcomment(int allowcomment) {
        this.allowcomment = allowcomment;
    }

    public void setPublished(long published) {
        this.published = published;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setShareurl(String shareurl) {
        this.shareurl = shareurl;
    }

    public void setSorttime(String sorttime) {
        this.sorttime = sorttime;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getContentid() {
        return contentid;
    }

    public int getModelid() {
        return modelid;
    }

    public int getTopicid() {
        return topicid;
    }

    public int getAllowcomment() {
        return allowcomment;
    }

    public long getPublished() {
        return published;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getThumb() {
        return thumb;
    }

    public String getSource() {
        return source;
    }

    public String getShareurl() {
        return shareurl;
    }

    public String getSorttime() {
        return sorttime;
    }

    public String getContent() {
        return content;
    }
}

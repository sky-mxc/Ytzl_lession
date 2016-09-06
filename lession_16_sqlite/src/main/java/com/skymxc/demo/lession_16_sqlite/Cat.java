package com.skymxc.demo.lession_16_sqlite;

/**
 * Created by sky-mxc
 * 类型实体类
 */
public class Cat {
   private  int    catid;
   private  String catname;
   private  String iconurl;
   private  int    sort;
   private  String name ;
   private  String url;

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCatid() {
        return catid;
    }

    public String getCatname() {
        return catname;
    }

    public String getIconurl() {
        return iconurl;
    }

    public int getSort() {
        return sort;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}

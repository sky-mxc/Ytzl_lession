package com.skymxc.demo.lesson_24_tab.practice.entity;

import java.io.Serializable;

/**
 * Created by sky-mxc
 * 标题实体类
 */

public class Tab  implements Serializable{
    private int catid;
    private String  catname;
    private String iconurl;
    private String sort;
    private String name;
    private String url ;

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public void setSort(String sort) {
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

    public String getSort() {
        return sort;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}

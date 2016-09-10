package com.skymxc.demo.lession_12_listview;

/**
 * Created by sky-mxc
 */
public class Tab {
    private String  catid;
    private String catname;
    private String iconurl;
    private String sort;
    private String name;
    private String url;

    public void setCatid(String catid) {
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

    public String getCatid() {
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

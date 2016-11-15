package com.skymxc.lesson_48_retrofit_eventbus.entity;

/**
 * Created by sky-mxc
 */
public class Tab {

  private Long  catid;
  private String  catname;
  private String  iconurl;
  private Integer  sort;
  private String  name;
  private String  url;

    public Tab(Long catid, String catname, String iconurl, Integer sort, String name, String url) {
        this.catid = catid;
        this.catname = catname;
        this.iconurl = iconurl;
        this.sort = sort;
        this.name = name;
        this.url = url;
    }

    public Tab() {
    }

    public void setCatid(Long catid) {
        this.catid = catid;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getCatid() {

        return catid;
    }

    public String getCatname() {
        return catname;
    }

    public String getIconurl() {
        return iconurl;
    }

    public Integer getSort() {
        return sort;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}

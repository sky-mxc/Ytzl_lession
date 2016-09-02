package com.skymxc.demo.lession_14_dialog;

import java.io.Serializable;

/**
 * Created by sky-mxc
 * 实体类
 */
public class Person implements Serializable{
    private long id;
   private String name;
   private String birthDate;
   private String birthTime;

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setBirthTime(String birthTime) {
        this.birthTime = birthTime;
    }

    public String getName() {
        return name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getBirthTime() {
        return birthTime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}

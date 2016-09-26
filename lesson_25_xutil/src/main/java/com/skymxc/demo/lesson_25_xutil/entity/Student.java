package com.skymxc.demo.lesson_25_xutil.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by sky-mxc
 */
@Table(name="student")
public class Student implements Serializable {

    @Column(name = "_id",isId = true,autoGen = true)
    private long id ;
    @Column(name = "name")
    private String name;
    @Column(name = "phone")
    private String phone;


    public Student(long id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public Student(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Student() {
    }

    public void setId(long id) {

        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getId() {

        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}

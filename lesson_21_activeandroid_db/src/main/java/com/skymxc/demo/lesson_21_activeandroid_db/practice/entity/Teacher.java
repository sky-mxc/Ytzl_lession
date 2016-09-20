package com.skymxc.demo.lesson_21_activeandroid_db.practice.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by sky-mxc
 * 教师类
 */
@Table(name="teacher")
public class Teacher  extends Model{
    @Column(name = "name")
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Teacher(String name) {
        this.name = name;
    }

    public Teacher() {
    }
}

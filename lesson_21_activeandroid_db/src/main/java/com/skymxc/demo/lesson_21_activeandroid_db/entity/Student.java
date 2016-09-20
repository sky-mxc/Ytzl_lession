package com.skymxc.demo.lesson_21_activeandroid_db.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by sky-mxc
 * 实体类  需要继承 Model类
 * id在 Model中已经定义 可以通过 注解更改id  ，一般使用默认id 不会更改
 */
@Table(name = "student")
public class Student  extends Model{

    @Column(name = "name")
    private String name;
    @Column(name = "className")
    private String className;
    @Column(name = "age")
    private int age;
    @Column(name = "grade")
    private String grade;


    public Student() {
    }

    public Student(String name, String className, int age, String grade) {
        this.name = name;
        this.className = className;
        this.age = age;
        this.grade = grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public int getAge() {
        return age;
    }

    public String getGrade() {
        return grade;
    }
}

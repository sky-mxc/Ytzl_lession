package com.skymxc.demo.lesson_21_activeandroid_db.practice.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by sky-mxc
 * 学生成绩表
 */
@Table(name="score")
public class Score extends Model {
    @Column(name = "name")
    private String name;
    @Column(name = "tId")
    private long tId;
    @Column(name = "java")
    private int java;
    @Column(name = "ssh")
    private int ssh;
    @Column(name = "html")
    private int html;
    @Column(name = "grade")
    private String grade;


    public void setGrade(){
        int avg = (java+ssh+html)/3;
        if (avg>=90){
            grade="优";
        } else if(avg>=80){
            grade="良";
        }else if(avg>=60){
            grade="中";
        }else {
            grade="差";
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void settId(long tId) {
        this.tId = tId;
    }

    public void setJava(int java) {
        this.java = java;
    }

    public void setSsh(int ssh) {
        this.ssh = ssh;
    }

    public void setHtml(int html) {
        this.html = html;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getName() {

        return name;
    }

    public long gettId() {
        return tId;
    }

    public int getJava() {
        return java;
    }

    public int getSsh() {
        return ssh;
    }

    public int getHtml() {
        return html;
    }

    public String getGrade() {
        return grade;
    }
}

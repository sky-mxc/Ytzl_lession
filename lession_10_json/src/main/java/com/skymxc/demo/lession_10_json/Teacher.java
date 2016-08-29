package com.skymxc.demo.lession_10_json;

import java.util.List;

/**
 * Created by sky-mxc
 */
public class Teacher {
    private String name;
    private List<Student> students;
    private int age ;

    public void setName(String name) {
        this.name = name;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public int getAge() {
        return age;
    }
}

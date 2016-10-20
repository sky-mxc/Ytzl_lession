package com.skymxc.demo.lsson_31_aidl.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sky-mxc
 * 需要序列化才能实现进程间通信
 */
public class Student implements Parcelable{

    private int id;
    private String name;
    private String phone;
    //如果包含其他封装类型 那么包含的封装的类型也必须序列化

    public Student() {
    }

    protected Student(Parcel in) {
        id = in.readInt();
        name = in.readString();
        phone = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(phone);
    }
    public void readFromParcel(Parcel source){
        id =  source.readInt();
        name = source.readString();
        phone = source.readString();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj==this) return  true;

        if (obj instanceof  Student){
            Student stu = (Student) obj;
            if (stu.id==this.id && stu.name.equals(this.name) && stu.phone.equals(stu.phone)){

                return true;
            }
        }
        return super.equals(obj);
    }
}

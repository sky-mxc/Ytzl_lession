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

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        //序列化
//        dest.writeInt(id);
//        dest.writeString(name);
//        dest.writeString(phone);
//    }
//
//    /**
//     * 返序列化
//     * 签名格式必须是以下格式
//     */
//    public static Creator<Student>   CREATOR  = new Creator<Student>() {
//        @Override
//        public Student createFromParcel(Parcel source) {
//            Student student = new Student();
//            //读取顺序要和写入顺序一致
//            student.id = source.readInt();
//            student.name = source.readString();
//            student.phone = source.readString();
//            return student;
//        }
//
//        @Override
//        public Student[] newArray(int size) {
//            return new Student[size];
//        }
//    };
}

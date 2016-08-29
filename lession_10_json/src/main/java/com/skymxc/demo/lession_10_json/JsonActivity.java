package com.skymxc.demo.lession_10_json;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class JsonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        Student stu = new Student();
        stu.setAge(21);
        stu.setClazz("一班");
        stu.setName("张三");
        //包含一些静态方法 常用来解析数据
        String json = JSON.toJSONString(stu);
        Log.e("Tag","==================StudentJson:"+json);

       stu=  JSON.parseObject(json,Student.class);
        Log.e("Tag","==================StudentName:"+stu.getName());

        List<Student> students = new ArrayList<>();
        students.add(stu);
        students.add(stu);
        students.add(stu);
        stu.setClazz("二班");
        students.add(stu);
        //禁用掉循环引用
      /*  json = JSON.toJSONString(students, SerializerFeature.DisableCircularReferenceDetect);
        Log.e("Tag","======过滤前===========StudentListJson:"+json);
        //过滤字段 fastjson 加上属性过滤器
       json = JSON.toJSONString(students, new PropertyFilter() {
            *//**
             *
             * @param o 正在被过滤的对象
             * @param s 被过滤的属性的名字
             * @param o1 被过滤的属性的值
             * @return 是否过滤 返回 false 就过滤掉
             *//*
            @Override
            public boolean apply(Object o, String s, Object o1) {
                Log.e("Tag","=======查看各个参数=====参数1"+o+",====参数2："+s+",=====参数3："+o1);
                if (s.equals("name")){
                    return false;//将name属性过滤
                }
                return true;
            }
        });
        Log.e("Tag","========过滤后==========StudentListJson:"+json);*/


        //将一个集合转换为 json格式
        json =  JSON.toJSONString(students);
       // List list = JSON.parseObject(json,List.class); 返回JSONObject 集合
        List<Student> stus = JSON.parseArray(json,Student.class);//返回Student集合
        Log.e("Tag","========parseArray===========:"+stus.get(0).getName());

        Result<Student> result = new Result<>();
        result.data = stu;
        result.des="学生党";
        json = JSON.toJSONString(result);
        Log.e("Tag","========自己定义的泛型类：=========="+json);
        //解决带有泛型的  凡是带有泛型的都可使用这个 东西
        Result<Student> result1 = JSON.parseObject(json,new TypeReference<Result<Student>>(){});
        Log.e("Tag","============="+result1.data.getName());


        /**
         * Gson 的使用
         */

        Gson g = new Gson();
         json =g.toJson(stu);
        Log.e("Tag","========Gson转换的=========="+json);

        stu = g.fromJson(json,Student.class);
    }


    static class  Result<T> {
        T data ;
        String des;
    }
}

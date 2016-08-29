package com.skymxc.demo.lession_10_json;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Student stu = new Student();
        stu.setAge(21);
        stu.setClazz("一班");
        stu.setName("张三");

        JSONObject jo = new JSONObject();
        //将数据存储到JSONObject
        try {
            jo.put("name",stu.getName());
            jo.put("age",stu.getAge());
            jo.put("clazz",stu.getClazz());



        String stuStr = jo.toString();
        Log.e("Tag","========student========"+stuStr);


        //json数组
        List<Student> stus = new ArrayList<Student>();
        stus.add(stu);
        stus.add(stu);
        stus.add(stu);
        stus.add(stu);

        JSONArray ja = new JSONArray();
        for (Student s :stus){
             jo = new JSONObject();
            //将数据存储到JSONObject
                jo.put("name",stu.getName());
                jo.put("age",stu.getAge());
                jo.put("clazz",stu.getClazz());


            ja.put(jo);
        }

        String stusStr = ja.toString();
        Log.e("Tag","==========JSONArray=====Student===="+stusStr);

            //根据json字符串创建 JSONObject
        jo =new JSONObject(stuStr);
            stu= new Student();
            stu.setName(jo.getString("name"));
            stu.setAge(jo.getInt("age"));
            stu.setClazz(jo.getString("clazz"));


        //将json字符串 转换为 JSONArray
            ja = new JSONArray(stusStr);
            stus = new ArrayList<Student>();
            //遍历 jsonArray
            for (int i=0;i<ja.length();i++){
                 jo = ja.getJSONObject(i);
                stu= new Student();
                stu.setName(jo.getString("name"));
                stu.setAge(jo.getInt("age"));
                stu.setClazz(jo.getString("clazz"));
                stus.add(stu);
            }


             stu = toObject(stuStr,stu.getClass());
            Log.e("Tag","==========success================"+stu.getName()+","+stu.getAge());
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public<T> T toObject(String json ,Class<T> type) throws IllegalAccessException, InstantiationException, JSONException,Exception {
        //反射出一个对象
        T t = type.newInstance();

        Log.e("Tag","======isAssignableFrom======="+t.getClass().isAssignableFrom(Collection.class)+"");
        Log.e("Tag","======instanceof======="+(t instanceof  Collection)+"");
        //如果对象是一个 集合
        if ( t instanceof  Collection){

        }else{
            JSONObject jo = new JSONObject(json);

            //获取所有的key
            Iterator<String> iterator = jo.keys();

            while (iterator.hasNext()){
                String name = iterator.next();
                //取出JSONObject的key对应的值
                Object obj =jo.get(name);

                if (obj instanceof  JSONArray){
                    //数组类型

                }else {
                    if (obj instanceof JSONObject) {
                        //JSONObject类型

                    } else {
                        //基本类型
                        try {
                            //获取到 所有声明的属性
                            Field f = type.getDeclaredField(name);
                            //设置为允许修改
                            f.setAccessible(true);
                            //设置参数1 要修改的对象 ，c参数2 修改为的值
                            f.set(t,obj);
                            Log.e("Tag","=====转换======f:"+f.toString()+",  t:"+t.toString()+",,,,objValue:"+obj.toString());
                        } catch (SecurityException e) {
                            continue;
                        }

                    }
                }
            }



        }

        return  t;
    }
}

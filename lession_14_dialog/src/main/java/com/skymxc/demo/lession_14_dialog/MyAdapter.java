package com.skymxc.demo.lession_14_dialog;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sky-mxc
 */
public class MyAdapter extends BaseAdapter {

    private  List<Person> persons ;

    private LayoutInflater lif;


    public MyAdapter(List<Person> persons, Context context) {
        this.persons = persons;
        this.lif = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        Log.e("Tag","====getCount=====");
        return persons==null?0:persons.size();
    }

    @Override
    public Person getItem(int i) {
        Log.e("Tag","====getItem=====");
        return persons.get(i);
    }

    @Override
    public long getItemId(int i) {
        Log.e("Tag","====getItemId=====");
        return persons.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.e("Tag","====getView=====");
        //获取到数据
        Person person = persons.get(i);
        //加载视图
        View v = lif.inflate(R.layout.layout_person_list_item,null);
        //匹配数据
        ((TextView) v.findViewById(R.id.name)).setText(person.getName());
        ((TextView) v.findViewById(R.id.birth_date)).setText(person.getBirthDate());
        ((TextView) v.findViewById(R.id.birth_time)).setText(person.getBirthTime());
        // 返回数据
        return v;
    }

    /**
     * save item
     * @param p item
     */
    public void addData(Person p){
        persons.add(p);
        notifyDataSetChanged();
        Log.e("Tag","=====新增数据："+p.getName()+"==id:"+p.getId());
    }

    /**
     * remove item
     * @param p item
     */
    public void delData(Person p){
        persons.remove(p);
        notifyDataSetChanged();
        Log.e("Tag","=====remove Data:"+p.getName());
    }

}

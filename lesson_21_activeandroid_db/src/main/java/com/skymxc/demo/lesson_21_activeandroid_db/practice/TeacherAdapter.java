package com.skymxc.demo.lesson_21_activeandroid_db.practice;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.skymxc.demo.lesson_21_activeandroid_db.practice.entity.Teacher;

import java.util.List;

/**
 * Created by sky-mxc
 */
public class TeacherAdapter extends BaseAdapter {
    private List<Teacher> teachers;
    private LayoutInflater lif ;
    public TeacherAdapter(Context context,List<Teacher> teachers){
        this.teachers=teachers;
        lif= LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return teachers==null?0:teachers.size();
    }

    @Override
    public Teacher getItem(int i) {
        return teachers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return teachers.get(i).getId()==null?i:teachers.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView tv;
        if (view==null){
            view= lif.inflate(android.R.layout.simple_list_item_1,null);

            tv = (TextView) view;
        }else{
            tv= (TextView) view;
        }
        Log.e("Tag","==getView=="+tv);
        tv.setText(getItem(i).getName());
        return tv;
    }

    public void addAll(List<Teacher> teachers){
        Log.e("TAg","====addAll=====");
       this.teachers.clear();
        this.teachers.addAll(teachers);
        notifyDataSetChanged();
    }
}

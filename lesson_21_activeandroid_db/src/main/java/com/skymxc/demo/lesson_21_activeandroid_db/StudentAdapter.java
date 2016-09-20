package com.skymxc.demo.lesson_21_activeandroid_db;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.skymxc.demo.lesson_21_activeandroid_db.entity.Student;

import java.util.List;

/**
 * Created by sky-mxc
 */
public class StudentAdapter extends BaseAdapter {

    private List<Student> students;
    private LayoutInflater lif;

    public StudentAdapter(Context context,List<Student> students){
        this.students = students;
        lif = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return students==null?0:students.size();
    }

    @Override
    public Student getItem(int i) {
        return students.get(i);
    }

    @Override
    public long getItemId(int i) {
       return students.get(i).getId();

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view ==null){
            view = lif.inflate(R.layout.layout_listview_item,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        Student student = getItem(i);
        holder.tvName.setText(student.getName());
        holder.tvAge.setText(student.getAge()+"");
        holder.tvClassName .setText(student.getClassName());
        holder.tvGrade .setText(student.getGrade());
        return view;
    }

    class  ViewHolder{
        TextView tvName;
        TextView tvAge;
        TextView tvGrade;
        TextView tvClassName;
        public ViewHolder(View view){
            tvName = (TextView) view.findViewById(R.id.name);
            tvAge = (TextView) view.findViewById(R.id.age);
            tvGrade = (TextView) view.findViewById(R.id.grade);
            tvClassName = (TextView) view.findViewById(R.id.class_name);
        }
    }

    public void add(Student student){
        this.students.add(student);
        notifyDataSetChanged();
    }

    public  void addAll(List<Student> students){
        this.students.clear();
        this.students.addAll(students);
        notifyDataSetChanged();
    }
}

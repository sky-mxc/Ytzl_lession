package com.skymxc.demo.lession_12_listview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sky-mxc
 */
public class MyAdapter extends BaseAdapter {

    private List<Student> students ;

    //布局加载器
    private LayoutInflater lif;

    @Override
    public int getCount() {
        return students==null?0:students.size();
    }

    @Override
    public Student getItem(int i) {
            Log.e("Tag","======取数据：第‘ "+i+" ' 个");
        return students==null?null:students.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * 将视图返回给调用者 然后就是显示咯
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Log.e("Tag","========"+i);
        View v = lif.inflate(R.layout.layout_student_item,null);//将布局解析成一个View返回

        Student s = getItem(i);//取出一个数据项

        //将数据和视图绑定

        //在v视图中查找控件
        ImageView photo = (ImageView) v.findViewById(R.id.photo);
        TextView tvName = (TextView) v.findViewById(R.id.name);
        TextView tvAge = (TextView) v.findViewById(R.id.age);

        photo.setImageResource(s.getPhoto());
        tvName.setText(s.getName());
        tvAge.setText(s.getAge()+"");

        return v;
    }

    public void addStudent(Student student){
        this.students.add(student);
        //数据改变了 更新视图
       // notifyDataSetChanged();
    }

    public void clear (){
        students.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Student> students){
        this.students.addAll(students);
        notifyDataSetChanged();
    }


    public MyAdapter(Context context, List<Student> students) {
        this.students = students;
        //得到布局加载器
        this.lif = LayoutInflater.from(context);

    }


}

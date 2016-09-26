package com.skymxc.demo.lesson_25_xutil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.skymxc.demo.lesson_25_xutil.entity.Student;

import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sky-mxc
 * sqlite 帮助
 */
@ContentView(R.layout.activity_db_manager)
public class DBManagerActivity extends AppCompatActivity {

    private static final String TAG ="DBManagerActivity";

    @ViewInject(R.id.listview)
    private ListView listView;

    private List<Student> students;
    private StudentAdapter adapter;
    private DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        students = new ArrayList<>();
        adapter = new StudentAdapter();
        listView.setAdapter(adapter);
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig();
        //设置数据库名
        daoConfig.setDbName("sky.DB");
        daoConfig.setDbVersion(1);
        //设置是否允许事务
        daoConfig.setAllowTransaction(true);
        dbManager = x.getDb(daoConfig);
        queryData();


        /**
         * 删除
         */

        delete();
//        //列名和值
//        KeyValue kvName = new KeyValue("name","王五");
//        KeyValue kvPhone = new KeyValue("phone","110");
//        //按照条件更新指定列
//        dbManager.update(Student.class,WhereBuilder.b(),kvName,kvPhone);
//        //parameter1 要更新的对象，parameter1+ ：要更新的列名
//        dbManager.update(stu,"name","phone");




    }

    private void delete()  {
        try {
            dbManager.deleteById(Student.class,1);
            dbManager.delete(Student.class);
            dbManager.delete(Student.class, WhereBuilder.b("name","like ","张").and("phone","=","110"));
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Event(value = {R.id.add} ,type = View.OnClickListener.class)
    private void click(View view){
        switch (view.getId()){
            case R.id.add:
                add();
                break;
        }
    }

    /**
     * ListView 的单项点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Event(value = {R.id.listview},type = AdapterView.OnItemClickListener.class)
    private void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        log("onItemClick");
        try {
            dbManager.deleteById(Student.class,id);
            queryData();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    /**
     * 新增
     */
    private void add() {
        Log.e(TAG,"==add()==students:"+students);
        try {
        for (int i=0;i<10;i++){
            Student s = new Student("张"+i,""+i*25+i);
          // students.add(s);
            dbManager.save(s);
        }

            queryData();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询
     *
     */
    private void queryData()  {
        try {
            //没有数据返回 null
            students = dbManager.findAll(Student.class);
            log("queryData: studentSize:"+students.size());

//            dbManager.findAll();
//            dbManager.findById();
//            dbManager.findFirst();
            dbManager.selector(Student.class).and(WhereBuilder.b()).and("name","like","张%").limit(5).offset(10).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }


    class  StudentAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return students==null?0:students.size();
        }

        @Override
        public Student getItem(int position) {
            return students.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.layout_list_item,null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            Student student = getItem(position);
            holder.tvName.setText(student.getName());
            holder.tvId.setText(student.getId()+"");
            holder.tvPhone.setText(student.getPhone());

            return convertView;
        }


        class  ViewHolder{
            @ViewInject(R.id.id)
            TextView tvId;
            @ViewInject(R.id.name)
            TextView tvName;
            @ViewInject(R.id.phone)
            TextView tvPhone;
            public ViewHolder(View view){
               x.view().inject(this,view);
            }
        }
    }


    private void log(String  str){
        Log.e(TAG,"="+str+"=");
    }
}

package com.skymxc.demo.lesson_21_activeandroid_db;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.skymxc.demo.lesson_21_activeandroid_db.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener ,DialogInterface.OnClickListener{

    private ListView lv;
    private StudentAdapter adapter;
    private List<Student> students;
    private Student currentStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        loadData();


    }

    private void loadData() {
        //根据id查询出一个Student的对象
      //  Student student = Model.load(Student.class,1);




        //条件查询  全部是字段名 并不是 类中的属性名
        // students = new Select().from(Student.class).where("Id=? and name like ?",1,"%张%").execute();
        //两边连接
        //  students= new Select().from(Student.class).as("a").join(Student.class).as("b").on("a.tid = b.tid").where("a.Id=? and b.name like ?",1,"%张%").execute();

        //不添加列 查询全部  from（Model）   execute() 查询全部list
       List<Student> students = new Select().from(Student.class).execute();
        Log.e("Tag","=====loadData()=========="+students.size());
        adapter.addAll(students);
    }

    /**
     * 初始化视图
     */
    private void initView() {
        lv = (ListView) findViewById(R.id.lv_students);
        students = new ArrayList<>();
        //students.add(new Student("张三","119",20,"S1"));
        adapter = new StudentAdapter(this,students);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(onItemClickListener);
    }
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            currentStudent = adapter.getItem(i);
            new AlertDialog.Builder(MainActivity.this).setMessage("删除还是更新呢？")
                    .setTitle("选择操作")
                    .setPositiveButton("删除",MainActivity.this)
                    .setNegativeButton("更新",MainActivity.this)
                    .show();
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add:
                startAddDialog();

                break;
        }
    }

    private void addAll() {
        try {
            //begin transaction
            ActiveAndroid.beginTransaction();
            for(int i=0;i<15;i++){
                Student student = new Student("李四"+i,"119",16+i,"S1");
                student.save(); //保存到数据库
            }
            //transaction success
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            //end transaction
            ActiveAndroid.endTransaction();
        }
    }

    /**
     * 对话框增加
     */
    private void startAddDialog() {
        AddDialog dialog = AddDialog.getInstance(this);
        dialog.setAddOk(new AddDialog.OnAddOk() {
            @Override
            public void addStudent(Student student) {
                Toast.makeText(MainActivity.this, student.getName(),Toast.LENGTH_SHORT).show();
                student.save();
                loadData();
            }
        });
        dialog.show();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        switch (i){
            case DialogInterface.BUTTON_POSITIVE:   //删除

                /**
                 * 三种删除方式
                 */
                currentStudent.delete();    //适合一次删除一个对象
               // Model.delete(Student.class,currentStudent.getId());     //知道id 对一条准确数据的删除
                //按条件删除  会将删除的对象返回 ，
               // new Delete().from(Student.class).where("Id=? ",currentStudent.getId()).executeSingle();


                break;
            case DialogInterface.BUTTON_NEGATIVE:   //跟新
                //set() 中参数是列名
                new Update(Student.class).set("name = ? ,age =? ,className =? ","张三",29,"120").where("Id =?",currentStudent.getId()).execute();
                break;
        }
        loadData();
    }
}

package com.skymxc.demo.lesson_21_activeandroid_db.practice;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.skymxc.demo.lesson_21_activeandroid_db.R;
import com.skymxc.demo.lesson_21_activeandroid_db.practice.entity.Score;
import com.skymxc.demo.lesson_21_activeandroid_db.practice.entity.Teacher;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String TAG ="practice.MainActivity";

    private Spinner spTeachers;
    private Spinner spGrades;
    private ListView lv;
    private SharedPreferences sp;
    private List<Teacher> teachers;
    private List<Score> scores;
    private Teacher cureentTeacher;
    private String cureentGrade;
    private TeacherAdapter tAdapter;
    private ScoreAdapter sAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_main);

        initView();
        initData();
        Log.e(TAG,"=======loadScore end=======");
    }

    private void initData() {
        sp = getSharedPreferences("setting",MODE_PRIVATE);
        boolean init= sp.getBoolean("init",false);
        if (!init){
            //插入数据
            try {
                ActiveAndroid.beginTransaction();
                for (int i=0;i<5;i++){
                    Teacher teacher = new Teacher("teacher"+i);
                    Log.e(TAG,"insert teacher："+teacher.getName());
                    teacher.save();
                }
                ActiveAndroid.setTransactionSuccessful();
            } finally {
                ActiveAndroid.endTransaction();
            }
            loadTeacher();
            //插入 成绩
            try {
                ActiveAndroid.beginTransaction();
                for (int i=0;i<20;i++){
                    Score score = new Score();

                  if (i%2==0){
                      score.setName("student"+teachers.get(2).getId());
                      score.settId(teachers.get(2).getId());
                      score.setHtml(30);
                      score.setJava(50);
                      score.setSsh(40);
                      score.setGrade();
                  }else if (i%3==0){
                      score.setName("dandan"+teachers.get(3).getId());
                      score.settId(teachers.get(3).getId());
                      score.setHtml(67);
                      score.setJava(63);
                      score.setSsh(64);
                      score.setGrade();
                  }else if (i%4==0){
                      score.setName("enheng"+teachers.get(4).getId());
                      score.settId(teachers.get(4).getId());
                      score.setHtml(77);
                      score.setJava(83);
                      score.setSsh(70);
                      score.setGrade();
                  }else if(i%5==0){
                      score.setName("haah"+teachers.get(1).getId());
                      score.settId(teachers.get(1).getId());
                      score.setHtml(87);
                      score.setJava(83);
                      score.setSsh(82);
                      score.setGrade();
                  }else{
                      score.setName("score"+teachers.get(5).getId());
                      score.settId(teachers.get(5).getId());
                      score.setHtml(97);
                      score.setJava(93);
                      score.setSsh(92);
                      score.setGrade();
                  }
                    Log.e(TAG,"======insert score====="+score.getName());
                    score.save();
                }
                ActiveAndroid.setTransactionSuccessful();
            } finally {
                ActiveAndroid.endTransaction();
            }

            SharedPreferences.Editor edit = sp.edit();
            edit.putBoolean("init",true);
            edit.commit();
        }
        //读取数据
        loadTeacher();
        //读取教师
        loadScore();
    }

    public void loadScore(){
        From select= new Select().from(Score.class).where(" 1=1 ");
        if (cureentTeacher!=null&& cureentTeacher.getId()!=null){
            select.and(" tId =?",cureentTeacher.getId());
        }
        if (cureentGrade!=null&& !cureentGrade.equals("请选择")){
            select.and(" grade =?",cureentGrade);
        }
       Log.e(TAG,select.toSql()) ;
      List<Score>  scores = select.execute();
        Log.e(TAG,"=====loadScore===size:"+scores.size());
      sAdapter.addAll(scores);

    }

    public void loadTeacher(){
      List<Teacher>    teachers= new Select().from(Teacher.class).execute();
        Log.e(TAG,"====loadTeacher====size:"+teachers.size());
        Teacher teacher = new Teacher();
        teacher.setName("请选择");
        teachers.add(0,teacher);
       tAdapter.addAll(teachers);
    }

    private void initView() {
        spGrades = (Spinner) findViewById(R.id.grades);
        spTeachers = (Spinner) findViewById(R.id.teachers);
        lv = (ListView) findViewById(R.id.lv);
        teachers= new ArrayList<>();
        scores = new ArrayList<>();
        tAdapter = new TeacherAdapter(this,teachers);
        spTeachers.setAdapter(tAdapter);
        sAdapter = new ScoreAdapter(this,scores);
        lv.setAdapter(sAdapter);
        spTeachers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e(TAG,"=====onItemSelected========"+tAdapter.getItem(i).getName());
                cureentTeacher = tAdapter.getItem(i);
                loadScore();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spGrades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cureentGrade = (String) spGrades.getSelectedItem();
                Log.e("Tag","==Grade=onItemSelected======"+cureentGrade);
                loadScore();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}

package com.skymxc.demo.lession_14_dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PersonListActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img ;
    private ListView lv;
    private MyAdapter adapter ;
    private List<Person> persons = new ArrayList<>();

    //请求码
    public static final int REQUEST_ADD =1;
    public static final int REQUEST_SEE =4;
    public static final int REQUEST_UPDATE =2;

    //返回码
    public static final int RESPONSE_UPDATE =2;
    public static final int RESPONSE_DEL =3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);
        img = (ImageView) findViewById(R.id.action_Image);
        lv = (ListView) findViewById(R.id.list);
        initLoad();

    }

    /**
     * 初始化列表 绑定数据
     */
    private void initLoad() {
        TextView tv = (TextView) findViewById(R.id.empty_view);
        lv.setEmptyView(tv);
        adapter = new MyAdapter(persons,this);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(itemClickLit);
    }

    /**
     * 列表项单击事件
     */
    private AdapterView.OnItemClickListener itemClickLit = new AdapterView.OnItemClickListener() {
        /**
         *
         * @param adapterView listView
         * @param view currentView
         * @param position viewIndex
         * @param id persionIndex
         */
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Person person = persons.get((int)id);
            Log.e("Tag","============单击一项========="+person.getName());
            Intent in = new Intent(PersonListActivity.this,PersonOperateActivity.class);
            in.putExtra("person",person);
            in.putExtra("operate",REQUEST_SEE);
            startActivityForResult(in,REQUEST_SEE);
        }
    };

    @Override
    public void onClick(View view) {
        Intent in = new Intent(this,PersonOperateActivity.class);
        switch (view.getId()){
            case R.id.action_Image:
                Log.e("Tag","=========add==============");
                in.putExtra("operate",0);
                startActivityForResult(in,REQUEST_ADD);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_OK || data == null)return;
        Person person = (Person) data.getSerializableExtra("person");
        int operate = data.getIntExtra("operate",RESPONSE_DEL);
        Log.e("Tag","=====REQUEST_Code====="+operate);
        execute(operate,person);
        adapter.notifyDataSetChanged();
    }

    /**
     * 执行 删 改
     * @param operate
     * @param person
     */
    private void execute(int operate, Person person) {
        //获取adapter中的数据
        Person p = adapter.getItem((int)person.getId());
        switch (operate){
            case RESPONSE_DEL:
             //   Log.e("Tag","======DEL==是否包含："+persons.contains(person)+"=======id"+person.getId());
                persons.remove(p);
                break;
            case RESPONSE_UPDATE:
                p.setBirthDate(person.getBirthDate());
                p.setName(person.getName());
                p.setBirthTime(person.getBirthTime());
                Log.e("Tag","=====UPDATE=====");
                break;
            case REQUEST_ADD:
                //新增
                person.setId(persons.size());
                adapter.addData(person);
                break;
        }

    }


}

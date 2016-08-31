package com.skymxc.demo.lession_12_listview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewActivity extends AppCompatActivity {

    private ListView listView;

    private EditText edit;
    private SimpleAdapter adapter;
    private  List<Map<String,String>> data;

    private TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        listView = (ListView) findViewById(R.id.listview);
        edit = (EditText) findViewById(R.id.text);
        tvEmpty = (TextView) findViewById(R.id.tvEmpty);

        data = new ArrayList<>();

        Map<String,String> map1 = new HashMap<>();
        map1.put("text1","第一条");
        data.add(map1);
         adapter = new SimpleAdapter(this,data,android.R.layout.simple_list_item_1,new String[]{"text1"},new int[]{android.R.id.text1});

        listView.setEmptyView(tvEmpty);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * 每项的点击事件处理
             * @param adapterView ListView
             * @param view 你点击的那一项的view
             * @param position  下标 当前view在ListView中的下标
             * @param id  数据id  这一项在数据集合中的下标
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.e("Tag","========onItemClick=========position:"+position);
                Log.e("Tag","========onItemClick=========id:"+id);
                if (id>-1){//除去头视图 点击头视图不显示
                    Toast.makeText(ListViewActivity.this,"点击了第"+(position+1)+"项",Toast.LENGTH_SHORT).show();
                }

            }
        });




    }

    public void click(View v){
        switch (v.getId()){
            case R.id.add:

                String text = edit.getText().toString();
                Log.e("Tag","======刚添加一条数据："+text);
                Map<String,String> d = new HashMap<>();
                d.put("text1",text);
                data.add(d );

                if (listView.getAdapter()==null){
                    listView.setAdapter(adapter);
                }
                adapter.notifyDataSetChanged();


                break;
            case R.id.add_header:

                listView.setAdapter(null);
                //加载头视图
                View headerView = getLayoutInflater().inflate(R.layout.layout_header,null);
                //将布局加载出来之后也可以对其中的控件进行操作
                TextView tv= (TextView) headerView.findViewById(R.id.header_tv);
                tv.setTextColor(Color.RED);
                //为ListView添加头视图
                listView.addHeaderView(headerView);
                listView.setAdapter(adapter);
                break;
        }
    }
}

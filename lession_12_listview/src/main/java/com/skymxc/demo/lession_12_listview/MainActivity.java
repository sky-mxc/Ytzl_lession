package com.skymxc.demo.lession_12_listview;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.listview);
        //数据适配器
      //  ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,new String[]{"item0","item1","item2","item3","item4","item5","item6","item7"});

        List<HashMap<String,String>> data = new ArrayList<>();

        for (int i=0;i<15;i++){
            HashMap<String,String> map = new HashMap<>();

            map.put("text1","name +"+i);
            map.put("text2","text2 +"+i);

            data.add(map);
        }

        //int[] 对应的控件
        //String[] map中的key 如果不对 就不会显示出来
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,data,android.R.layout.simple_list_item_2,new String[]{"text1","text2"},new int[]{android.R.id.text1,android.R.id.text2});

        //设置视图绑定器：定义数据和视图之间的绑定关系； 如果不能自定匹配我们的数据才使用
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            /**
             *
             * @param view 要绑定的视图
             * @param o 数据
             * @param s 文本描述
             * @return false:表示绑定未处理完成 ；true ：处理完成  如果处理完成 就不会 执行 SimpleAdapter 中的setViewBinder
             */
            @Override
            public boolean setViewValue(View view, Object o, String s) {
                if (view.getId() == android.R.id.text1){
                    TextView tv= (TextView)view;
                    //tv.setText(s);
                    tv.setText(o.toString());//和上面一样的
                    tv.setTextColor(Color.RED);
                    return true;
                }
                return false;
            }
        });


        lv.setAdapter(simpleAdapter);

        lv.setDivider(new ColorDrawable(Color.GRAY));
        lv.setDividerHeight(5);

    }
}

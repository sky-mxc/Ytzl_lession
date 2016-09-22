package com.skymxc.demo.lesson_23_viewpager;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by sky-mxc
 * 封装 对每个Pager相应的操作 返回对应的view
 */

public class FirstPager  extends  BasePager{
    private ListView listView;
    public FirstPager(Context context, @LayoutRes int layoutResId) {
        super(context, layoutResId);
    }

    @Override
    protected void initView() {
      listView = (ListView) findViewById(R.id.listview);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,new String[]{"哈哈","呵呵","呵呵","呵呵","呵呵","呵呵","呵呵","呵呵","呵呵"});

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(onItemClickListener);
    }
    AdapterView.OnItemClickListener onItemClickListener =new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(context,parent.getAdapter().getItem(position).toString(),Toast.LENGTH_SHORT).show();
        }
    };

}

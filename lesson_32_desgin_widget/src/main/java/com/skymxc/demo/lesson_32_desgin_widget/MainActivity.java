package com.skymxc.demo.lesson_32_desgin_widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.skymxc.demo.lesson_32_desgin_widget.entity.Item;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private String TAG="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        //设置布局管理器
        LinearLayoutManager lm = new LinearLayoutManager(this);
        //自动测量子控件尺寸   //在24之前的版本 需要些 后来默认true；针对于包裹尺寸下 LinearLayout不显示额情况
        lm.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(lm);

        adapter = new MyAdapter(this,getItems());
        recyclerView.setAdapter(adapter);

        Log.i(TAG, "onCreate: childViewCount=:"+recyclerView.getChildCount());
        Log.i(TAG, "onCreate: dataCount:"+recyclerView.getAdapter().getItemCount());


        //添加子项的装饰
        recyclerView.addItemDecoration(new MyDecoration(this));

        adapter.setOnClickListener(this);
        }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: childViewCount=:"+recyclerView.getLayoutManager().getChildCount());
        Log.i(TAG, "onResume: dataCount:"+recyclerView.getAdapter().getItemCount());
    }

    public List<Item> getItems() {
        List<Item> items =new ArrayList<>();
        for (int i =0;i<20;i++){
            Item item = new Item();
            item.setText1("text1_"+i);
            item.setText2("text2_"+i+" "+i);
            if(i%6==0){
                Log.i(TAG,"========2");
                item.setType(2);
                item.setIcon("dj");
                item.setProgress(i*5);
            }else  if (i%5!=0){
                item.setIcon("d");
                item.setType(0);
            }else  {
                //头
                item.setText1("Group"+i);
                item.setType(1);
            }

            items.add(item);
        }
        return items;
    }

    @Override
    public void onClick(View v) {
        //得到子项下标
        int position=recyclerView.getChildAdapterPosition(v);
        Toast.makeText(this, "==="+position, Toast.LENGTH_SHORT).show();
    }
}

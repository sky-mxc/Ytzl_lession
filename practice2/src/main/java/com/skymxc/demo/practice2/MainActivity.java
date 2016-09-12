package com.skymxc.demo.practice2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.Response;
import com.skymxc.demo.practice2.entity.Lesson;
import com.skymxc.demo.practice2.entity.Result;
import com.skymxc.demo.practice2.network.Http;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private Picasso picasso;
    private LessionAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Tag","===MainActivity========onCreate===========");
        boolean f = getSharedPreferences("setting",MODE_PRIVATE).getBoolean("mode",false);
        if (f){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.listview);
        picasso = Picasso.with(this);
        adapter = new LessionAdapter();
        lv.setAdapter(adapter);
        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE,Menu.FIRST,0,"设置");
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("Tag","=======onRestart=============");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Tag","==========onDestroy=========");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Tag","==========onPause=========");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Tag","============onStop========");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case Menu.FIRST:
                SettingActivity.startActivity(MainActivity.this);
                break;
        }
        return true;
    }

    private void loadData(){
        String url ="http://toolsmi.com/starclass/lessons";
        Http.getInstance(this).post(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("Tag","======="+s);
                Result<List<Lesson>> result=   JSON.parseObject(s,new TypeReference<Result<List<Lesson>>>(){});
                if (result.state==1){
                    adapter.addAll(result.data);
                }
            }
        });
    }

    class  LessionAdapter extends BaseAdapter{

        List<Lesson> lessons = new ArrayList<>();
        @Override
        public int getCount() {
            return lessons.size();
        }

        @Override
        public Lesson getItem(int i) {
            return lessons.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder ;
            if (view ==null){
                view = getLayoutInflater().inflate(R.layout.layout_lession_item,null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }else{
                holder = (ViewHolder) view.getTag();
            }
            Lesson lesson = getItem(i);
            holder.describe.setText(lesson.getLdescribe());
            holder.title.setText(lesson.getLname());
            picasso.load(lesson.getThumbUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(holder.thumb);
            return view;
        }


        public void addAll(List<Lesson> data) {
            lessons.clear();
            lessons.addAll(data);
            notifyDataSetChanged();
        }
    }

    //对视图中的控件的封装
    class ViewHolder{
        ImageView thumb;
        TextView title;
        TextView  describe;

        public ViewHolder(View view){
            thumb = (ImageView) view.findViewById(R.id.thumb);
            title = (TextView) view.findViewById(R.id.title);
            describe = (TextView) view.findViewById(R.id.describe);
        }
    }
}

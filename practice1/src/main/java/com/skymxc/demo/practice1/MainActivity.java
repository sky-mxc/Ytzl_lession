package com.skymxc.demo.practice1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.skymxc.demo.practice1.model.Lession;
import com.skymxc.demo.practice1.model.Result;
import com.skymxc.demo.practice1.utils.Http;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private List<Lession> lessions;
    private Picasso picasso;
    private MyAdapter adapter ;
    private SharedPreferences preferences;
    private boolean checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("setting",MODE_PRIVATE);
        if ((checked=preferences.getBoolean("checked",false))){
            //夜间模式
            setTheme(R.style.AppThemeNight);
        }else{
            //日间模式
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lv);
        picasso = Picasso.with(this);
        lessions = new ArrayList<>();
        adapter = new MyAdapter();
        lv.setAdapter(adapter);
        initData();
    }
    private void initData(){
        //TODO 加载数据
        String url ="http://toolsmi.com/starclass/lessons";
        Http.getInstance(this).get(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String json) {
                Log.e("Tag","==========onResponse==========json:"+json);
                if (!TextUtils.isEmpty(json)){
                 Result<List<Lession>> result=  JSON.parseObject(json,new TypeReference<Result<List<Lession>>>(){});
                lessions.addAll(result.data);
                    adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this,result.describe,Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("Tag","======onErrorResponse========"+volleyError);
                Toast.makeText(MainActivity.this,"加载出错",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("Tag","=============onRestart()============");
        Log.e("Tag","============================"+checked);
        if (checked!=preferences.getBoolean("checked",false)){
            recreate();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       menu.add(Menu.NONE,R.id.setting,0,"设置");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
                Intent in = new Intent(this,SettingActivity.class);
                startActivity(in);
                break;
        }
        return true;
    }

    class MyAdapter extends BaseAdapter{

        private Lession lession;

        @Override
        public int getCount() {
            return lessions==null?0:lessions.size();
        }

        @Override
        public Lession getItem(int i) {
            return lessions.get(i);
        }

        @Override
        public long getItemId(int i) {
            return getItem(i).getTid();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder ;
           if (view == null){
               //TODO 加载 视图绑定数据
               view = getLayoutInflater().inflate(R.layout.layout_listview_item,null);
               ImageView image = (ImageView) view.findViewById(R.id.image);
               TextView title = (TextView) view.findViewById(R.id.title);
               TextView desc = (TextView) view.findViewById(R.id.desc);
               holder = new ViewHolder(image,title,desc);
               view.setTag(holder);
           }else{
               holder = (ViewHolder) view.getTag();
           }
            lession = getItem(i);
            picasso.load(lession.getThumbUrl())
                    .resizeDimen(R.dimen.thumn_w,R.dimen.thumn_h)
                    .error(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .centerInside()
                    .into(holder.image);
            holder.title.setText(lession.getLname());
            holder.desc.setText(lession.getLdescribe());
            return view;
        }


        class ViewHolder{
            ImageView image;
            TextView title;
            TextView desc;
            public ViewHolder(ImageView image, TextView title, TextView desc){
                this.image =image;
                this.title =title;
                this.desc = desc;
            }
        }
    }


}

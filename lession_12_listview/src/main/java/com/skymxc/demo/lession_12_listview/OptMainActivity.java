package com.skymxc.demo.lession_12_listview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.Response;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * ListView性能优化
 */
public class OptMainActivity extends AppCompatActivity {

    private ListView listView;
    private List<News> news;
    private Picasso picasso;
    private MyAdapter adapter;
    private Button prevrios;
    private Button next;
    private int page =1;
    private Spinner spinner;
    private OptTabAdapter tabAdapter;
    private String catid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opt_main);
        listView = (ListView) findViewById(R.id.list_view);
        prevrios = (Button) findViewById(R.id.previous);
        next = (Button) findViewById(R.id.next);
        spinner = (Spinner) findViewById(R.id.spinner);
        news = new ArrayList<>();
        picasso = Picasso.with(this);       //获取到实例
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
        next.setOnClickListener(clickLit);
        prevrios.setOnClickListener(clickLit);
        tabAdapter = new OptTabAdapter(new ArrayList<Tab>(),this);
        spinner.setAdapter(tabAdapter);
        spinner.setOnItemSelectedListener(onItemSelectedListener);
        loadTab();
    }

    /**
     * 加载类型
     */
    private void loadTab() {
        Http.getInstance(this).get("http://mapi.univs.cn/mobile/index.php?app=mobile&type=mobile&controller=content&action=category", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (!TextUtils.isEmpty(s)){
                    OptResult<List<Tab>> result= JSON.parseObject(s,new TypeReference<OptResult<List<Tab>>>(){});
                    if (result.state){
                        tabAdapter.addAll(result.data);
                    }else{
                        Toast.makeText(OptMainActivity.this,"请求失败："+result.message,Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(OptMainActivity.this,"请求失败：",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private  AdapterView.OnItemSelectedListener onItemSelectedListener =new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
           Tab tab= tabAdapter.getItem(i);
            catid =  tab.getCatid();
            loadData();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private  View.OnClickListener clickLit = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.next:
                    page++;
                    break;
                case R.id.previous:
                    page--;
                    break;
            }
            if (page<=1){
                prevrios.setEnabled(false);
            }else if (page >1){
                prevrios.setEnabled(true);
            }
            loadData();
        }
    };

    /**
     * 加载要显示的数据
     */
    private void loadData() {
        Http.getInstance(this).get("http://mapi.univs.cn/mobile/index.php?app=mobile&type=mobile&controller=content&content=index&catid="+catid+"&page="+page,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        Log.e("Tag","json:"+str);
                        if (!TextUtils.isEmpty(str)){
                            OptResult<List<News>> result= JSON.parseObject(str,new TypeReference<OptResult<List<News>>>(){});
                            Log.e("Tag","=====state:"+result.state);
                            //控制下一页的按钮是否可以点击
                            next.setEnabled(result.more);
                            if (result.state){
                                news.clear();
                                news.addAll(result.data);
                                adapter.notifyDataSetChanged();
                            }else{
                                Toast.makeText(OptMainActivity.this,"请求失败1："+result.message,Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(OptMainActivity.this,"请求失败：",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    class  MyAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            Log.e("Tag","======getCount=============");
            return news!=null?news.size():0;
        }

        @Override
        public News getItem(int i) {
            Log.e("Tag","========getItem===========");
            return news.get(i);
        }

        @Override
        public long getItemId(int i) {
            Log.e("Tag","========getItemId===========");
            return getItem(i).getContentid();
        }

        /**
         * 通过复用减少 加载 xml的时间 减少 内存片
         * 使用 一个类 绑定view 减少 变量占用的空间
         * @param i
         * @param v
         * @param viewGroup
         * @return
         */
        @Override
        public View getView(int i, View v, ViewGroup viewGroup) {
            Log.e("Tag","=======getView============");
            ViewHolder holder;
            if (v==null){
                 v = getLayoutInflater().inflate(R.layout.layout_news_item_opt,null);
                holder = new ViewHolder(v);
                v.setTag(holder);
            }else{
                holder = (ViewHolder) v.getTag();
            }

            News news = getItem(i);
            holder.title.setText(news.getTitle());
            holder.summary.setText(news.getDescription());
            String path = news.getThumb();
            if (!TextUtils.isEmpty(path)) {
                //根据地址加载图片  ，自动开线程进行下载 网络请求操作都会自动完成 ;
                picasso.load(news.getThumb())
                        .placeholder(R.mipmap.ic_launcher)              //加载中的占位图
                        .error(R.mipmap.ic_launcher)                    //加载失败的提示图片
                        .resizeDimen(R.dimen.thumn_w, R.dimen.thumn_h)   //从新设置图片加载后的尺寸  7resizeDimen设置宽高的资源id  resize() 直接设置 宽高像素
                        .centerInside()                                 //图片缩放方式 跟image中不同的是 即使图片小 会被放大， 会覆盖 Image的设置
                        .into(holder.image);                            //into  是加载到哪一个控件当中；
            }
         /*   //没有加载出来  在View宽高固定的情况下
            holder.image.getLayoutParams().width;       //像素宽度
            holder.image.getLayoutParams().height;      //像素高度
            //已经显示的情况下：getWidth getHeight
            //什么情况下都可以使用  测量*/
            /**
             *
             * 获取dimens 下定义的 尺寸
             */
            //获取像素尺寸大小
        //    getResources().getDimensionPixelSize(R.dimen.thumn_w);

            return v;
        }

    }

    class  ViewHolder {
        ImageView image;
        TextView title;
        TextView summary;

        public ViewHolder(View v){
            //绑定操作
             image  = (ImageView) v.findViewById(R.id.thumb);
            title   = (TextView) v.findViewById(R.id.title);
            summary = (TextView) v.findViewById(R.id.summary);
        }
    }
}

package com.skymxc.demo.lession_12_listview;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsonListActivity extends AppCompatActivity {

    private ListView listView;

    private MyAdapter myAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_list);
        listView = (ListView) findViewById(R.id.listview);
        myAdapter= new MyAdapter(new ArrayList<Menu>(),this);
        listView.setAdapter(myAdapter);

        loadData();
    }

    /**
     * 加载数据
     */
    private void loadData() {
        MyTask myTask = new MyTask();
        myTask.execute("http://www.toolsmi.com/Veeker/menus?page=1");
    }

    class  MyTask extends AsyncTask<String,Void,List<Menu>>{

        @Override
        protected List<Menu> doInBackground(String... strings) {
            String[] params= strings[0].split("\\?");  //问号作为间隔 拆分参数

            HttpURLConnection connection = null;
            OutputStream os = null;
            InputStream is = null;
            ByteArrayOutputStream bos =null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                os = connection.getOutputStream();//网络输出流
                os.write(params[1].getBytes("utf-8"));  //写入参数  上传参数
                os.close();
                int code =connection.getResponseCode();

                if (code == 200){
                    is= connection.getInputStream();
                    int len =-1;
                    byte[] b = new byte[1024];
                    bos = new ByteArrayOutputStream();
                    while ((len= is.read(b))!=-1){
                        bos.write(b,0,len);
                    }
                    String json = bos.toString("utf-8");
                    Result<List<Menu> >data = JSON.parseObject(json,new TypeReference<Result<List<Menu>>>(){});
                    return data.data;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Menu> menus) {
            if (menus!=null){
                myAdapter.addAll(menus);
            }else{
                Toast.makeText(JsonListActivity.this,"网络访问失败",Toast.LENGTH_SHORT).show();
            }
        }
    }


    class MyAdapter extends BaseAdapter{

        private List<Menu> menus;

        private LayoutInflater lif;

        public MyAdapter(ArrayList<Menu> menus, Context context) {
            lif = LayoutInflater.from(context);
            this.menus = menus;
        }

        @Override
        public int getCount() {
            return menus==null?0:menus.size();
        }

        @Override
        public Menu getItem(int i) {
            return menus.get(i);
        }

        @Override
        public long getItemId(int i) {
            return getItem(i).getId();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            //加载布局
            TextView tv = (TextView) lif.inflate(android.R.layout.simple_list_item_1,null);
            tv.setText(getItem(i).getTitle());
            return tv;
        }

        public void addAll(List<Menu> menus ){
            this.menus.addAll(menus);
            notifyDataSetChanged();
        }
    }
}

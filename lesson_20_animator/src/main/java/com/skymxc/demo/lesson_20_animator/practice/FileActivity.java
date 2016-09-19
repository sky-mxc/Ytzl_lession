package com.skymxc.demo.lesson_20_animator.practice;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.skymxc.demo.lesson_20_animator.R;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sky-mxc
 */
public class FileActivity extends AppCompatActivity {

    private static final String TAG ="FileActivity";

    private ListView fileLv;
    private TextView tvTitle;
    private FileAdapter adapter;
    private List<File> files;
    private File currentFile;
    private  TextView tvBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_main);
        initView();
    }


    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (l<0){
                //back
                backUp();
                return;
            }
            currentFile = adapter.getItem((int)l);
            if (currentFile.isFile()){
                Toast.makeText(FileActivity.this,currentFile.getName(),Toast.LENGTH_SHORT).show();
            }else{
               log("subFiles:"+currentFile.listFiles().length);
                if (fileLv.getHeaderViewsCount()<1){
                    fileLv.setAdapter(null);
                     tvBack = new TextView(FileActivity.this);
                    tvBack.setText("返回上一级");
                    AbsListView.LayoutParams params = new AbsListView.LayoutParams(500,50);
                    params.height=50;
                    tvBack.setLayoutParams(params);
                    tvBack.setPadding(10,10,10,10);
                    tvBack.setGravity(Gravity.CENTER_VERTICAL);
                    fileLv.addHeaderView(tvBack);
                }
                files = Arrays.asList(currentFile.listFiles());
                fileLv.setAdapter(adapter);
                tvTitle.setText(currentFile.getName());
            }
        }
    };

    /**
     * 回到上一级
     */
    private void backUp() {
        if (Environment.getExternalStorageDirectory().getPath().equals(currentFile.getParentFile().getPath())){
            //上一级是根目录
            fileLv.setAdapter(null);
            fileLv.removeHeaderView(tvBack);
            fileLv.setAdapter(adapter);
            tvTitle.setText("SDCard");
        }else{
            tvTitle.setText(currentFile.getParentFile().getName());
        }
        //不是根目录
        files = Arrays.asList(currentFile.getParentFile().listFiles());
        currentFile= currentFile.getParentFile();
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        fileLv = (ListView) findViewById(R.id.file_lv);
        tvTitle = (TextView) findViewById(R.id.title);
        currentFile= Environment.getExternalStorageDirectory();
        files = Arrays.asList(currentFile.listFiles());
        adapter = new FileAdapter();
        fileLv.setAdapter(adapter);
        fileLv.setOnItemClickListener(onItemClickListener);
    }

    @Override
    public void onBackPressed() {
        if ( Environment.getExternalStorageDirectory().getPath().equals(currentFile.getPath())){
            super.onBackPressed();
        }else{
            backUp();
        }


    }

    public static void log(Object str){
        Log.e(TAG,"====="+str+"=====");
    }

    class  FileAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return files.size();
        }

        @Override
        public File getItem(int i) {
            return files.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView tv;
            if (view ==null){
                view= getLayoutInflater().inflate(android.R.layout.simple_list_item_1,null);
                 tv = (TextView) view;

            }else{
                tv = (TextView) view;
            }
            File file = getItem(i);
            tv.setText(file.getName());

            return tv;
        }
    }
}

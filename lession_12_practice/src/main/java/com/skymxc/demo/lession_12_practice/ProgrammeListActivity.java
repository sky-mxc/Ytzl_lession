package com.skymxc.demo.lession_12_practice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sky-mxc
 */
public class ProgrammeListActivity extends AppCompatActivity {

    private ListView listView ;
    private List<Programme> programmes;

    private MyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programme);
        listView = (ListView) findViewById(R.id.listview);
        programmes = new ArrayList<>();
         adapter = new MyAdapter(programmes,this);
        listView.setAdapter(adapter);
        loadData();
    }

    /**
     * 加载数据
     */
    private void loadData() {


        HttpTask<Result<List<Programme>>> task = new HttpTask<Result<List<Programme>>>(new TypeReference<Result<List<Programme>>>(){});
        task.execute("http://toolsmi.com/Veeker/programmes?pageIndex=1&pageSize=10&type=0");
        task.setOnResponseLinstener(new HttpTask.OnResponseLinstener<Result<List<Programme>>>() {
            @Override
            public void onResponse(Result<List<Programme>> listResult) {
                if (listResult.error==Result.ERRORNO.NO_ERROR){
                    programmes.addAll(listResult.data);
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(ProgrammeListActivity.this,listResult.error.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public static void startActivity(Context conetxt) {
        Intent in = new Intent(conetxt,ProgrammeListActivity.class);
        conetxt.startActivity(in);
    }


}

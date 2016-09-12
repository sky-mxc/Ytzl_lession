package com.skymxc.demo.practice1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.skymxc.demo.practice1.model.DataItem;
import com.skymxc.demo.practice1.utils.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class P2MainActivity extends AppCompatActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener {

    private ImageView imgAdd;
    private ListView lv;
    private CheckBox cbAll;
    private DBHelper dbhelper ;
    private List<DataItem> datas;
    private DeataAdapter adapter ;
    private boolean mode ;  //是删除模式还是选择模式
    private List<Integer> states;   //选中的


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p1_main);
        imgAdd = (ImageView) findViewById(R.id.add_show);
        lv = (ListView) findViewById(R.id.lv);
        cbAll = (CheckBox) findViewById(R.id.all);
        cbAll.setOnCheckedChangeListener(this);
        dbhelper = new DBHelper(this);
        datas = new ArrayList<>();
        adapter = new DeataAdapter(this,datas);
        lv.setAdapter(adapter);
        adapter.setOnCheckedChangeListener(this);
        adapter.setOnClickListener(this);
        adapter.setBind(bind);
        dbhelper = new DBHelper(this);
        lv.setOnItemLongClickListener(onItemLongClickListener);
        states = new ArrayList<>();

    }

    AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            mode =true;
            adapter.notifyDataSetChanged();
            imgAdd.setVisibility(View.INVISIBLE);
            cbAll.setVisibility(View.VISIBLE);
            return true;
        }
    };

    /**
     * adapter中绑定视图的操作
     */
    private DeataAdapter.bindView bind = new DeataAdapter.bindView() {
        @Override
        public void bind(DeataAdapter.ViewHolder holder, DataItem item) {

            holder.cbChoose.setTag(item.id);
            holder.cbChoose.setOnCheckedChangeListener(null);
            holder.btDel.setTag(item);
            holder.tvTitle.setText(item.title);
            holder.tvDesc.setText(item.desc);
            if (mode){
                //选择
                holder.btDel.setVisibility(View.INVISIBLE);
                holder.cbChoose.setVisibility(View.VISIBLE);
                holder.cbChoose.setChecked(cbAll.isChecked());
                if(states.contains(new Integer(item.id))){
                    Log.e("Tag","======="+item.title+"========="+states.contains(new Integer(item.id)));
                    holder.cbChoose.setChecked(true);
                }else{
                    holder.cbChoose.setChecked(false);
                }
                holder.cbChoose.setOnCheckedChangeListener(P2MainActivity.this);
            }else{
                //删除
                holder.btDel.setVisibility(View.VISIBLE);
                holder.cbChoose.setVisibility(View.INVISIBLE);
                holder.btDel.setOnClickListener(P2MainActivity.this);
            }
        }
    };

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.all:
                if (b==false){
                    states.clear();
                }else{
                    for(DataItem item :datas){
                        states.add(new Integer(item.id));
                    }
                }
                adapter.notifyDataSetChanged();
                break;
            default:
                cbAll.setOnCheckedChangeListener(null);
                int id = (int) compoundButton.getTag();
                if (b==false){
                    Log.e("Tag","=======remove===="+id);
                    cbAll.setChecked(false);
                    states.remove(new Integer(id));
                }else{
                   if(!states.contains(new Integer(id))){
                        states.add(new Integer(id));
                       Log.e("Tag","=======add===="+id);
                    }
                    if (states.size()==datas.size()){
                        cbAll.setChecked(true);
                    }
                }

                Log.e("Tag","=="+id+"=======states size:"+states.size()+"==="+states);

                cbAll.setOnCheckedChangeListener(P2MainActivity.this);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_show:
                readData();
                break;
            default:
                DataItem item = (DataItem) view.getTag();
                Log.e("Tag","=====execute Del================="+item.id);
                delData(item.id);
                readData();
                break;
        }
    }

    @Override
    public void onBackPressed() {

        if (mode){      //判断当前处于什么模式下
            mode=false;
            adapter.notifyDataSetChanged();
            imgAdd.setVisibility(View.VISIBLE);
            cbAll.setVisibility(View.INVISIBLE);
        }else{
            super.onBackPressed();
        }
    }

    /**
     * 删除
     * @param id
     */
    private void delData(int id){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        db.delete("dataItem","_id =?",new String[]{id+""});
        db.close();
    }


    /**
     * 从数据库读数据并显示
     */
    private void readData(){
        Log.e("Tag","========beginRead============="+datas.size());
        datas.clear();
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from dataItem",null);
        while (cursor!= null && cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            Log.e("Tag","=======readData======="+id);
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String desc = cursor.getString(cursor.getColumnIndex("desc"));
            DataItem item = new DataItem();
            item.id=id;
            item.title =title;
            item.desc =desc+"\t"+item.id;
            datas.add(item);
        }

        cursor.close();
        db.close();
        adapter.notifyDataSetChanged();
        Log.e("Tag","==========endRead==========="+datas.size());
    }
}

package com.skymxc.demo.practice1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.skymxc.demo.practice1.model.DataItem;
import com.skymxc.demo.practice1.utils.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class P1MainActivity extends AppCompatActivity implements View.OnClickListener ,CompoundButton.OnCheckedChangeListener{

    private ImageView imgAdd;
    private ListView lv;
    private CheckBox cbAll;
    private DBHelper dbhelper ;
    private List<DataItem> datas;
    private Map<DataItem,ViewHolder> holders;
    private  MyAdapter adapter;
    private boolean isChoose;

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
        holders = new HashMap<>();
        adapter = new MyAdapter();
        lv.setAdapter(adapter);
      //  initData();
        lv.setOnItemClickListener(itemClickLit);
        lv.setOnItemLongClickListener(onItemLongClickLit);
    }

    /**
     * ListView 的长按 监听
     */
    private AdapterView.OnItemLongClickListener onItemLongClickLit = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
           Log.e("Tag","=======onItemLongClick================"+i);
            isChoose = true;
            choose();
            return true;
        }
    };

    /**
     * 进入复选框开始选择的界面
     */
    private void choose() {
        if (isChoose){
            //进入选择界面
            Set<DataItem> items= holders.keySet();
            for(DataItem item :items){
                ViewHolder holder = holders.get(item);
                holder.cbChoose.setVisibility(View.VISIBLE);
                holder.btDel.setVisibility(View.INVISIBLE);
            }
            imgAdd.setVisibility(View.INVISIBLE);
            cbAll.setVisibility(View.VISIBLE);
        }else{
            //删除界面
            Set<DataItem> items= holders.keySet();
            for(DataItem item :items){
                ViewHolder holder = holders.get(item);
                holder.cbChoose.setVisibility(View.INVISIBLE);
                holder.btDel.setVisibility(View.VISIBLE);
            }
            imgAdd.setVisibility(View.VISIBLE);
            cbAll.setVisibility(View.INVISIBLE);
        }

    }

    private AdapterView.OnItemClickListener itemClickLit = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Log.e("Tag","=======OnItemClickListener================"+i);
        }
    };

    /**
     * 初始化数据 往 数据库添加10条数据
     */
    private void initData() {
        //TODO 往数据库增加15条数据
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        try {

            db.beginTransaction();
            for (int i=0;i<15 ;i++) {
                DataItem item = new DataItem();
                item.title = "title "+i;
                item.desc = "desc "+i;
                db.execSQL("insert into dataItem(title,desc)values('"+item.title+"','"+item.desc+"')");
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    /**
     * 从数据库读数据并显示
     */
    private void readData(){
        Log.e("Tag","========beginRead============="+datas.size());
        datas.clear();
        holders.clear();
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
            item.desc =desc;
            datas.add(item);
        }

        cursor.close();
        db.close();
        adapter.notifyDataSetChanged();
        Log.e("Tag","==========endRead==========="+datas.size());
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


    @Override
    public void onBackPressed() {

        if (isChoose){
            isChoose=false;
            choose();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
    switch (view.getId()){
        case  R.id.add_show:
            //TODO 从数据库读取10条数据显示给 ListView
            readData();
            break;
        case R.id.del:
            DataItem item = (DataItem) view.getTag();

            Log.e("Tag","==========del========"+item.id);
            delData(item.id);
            readData();
            Log.e("Tag","=================是否包含呢："+holders.containsKey(item));
            holders.remove(item);

            break;
    }
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.all:
                //执行
                execCBAll(b);
                break;
            default:
                cbAll.setOnCheckedChangeListener(null);
                if (b){
                    //遍历全部是否都勾选了，如果都勾选了就把全选设置true
                    cbAll.setChecked(isSelecteAll());
                }else{
                    if (cbAll.isChecked()){
                        cbAll.setChecked(false);
                    }
                }

                    cbAll.setOnCheckedChangeListener(P1MainActivity.this);
                break;
        }
    }

    /**
     * 是否全部选中
     * @return
     */
    private boolean isSelecteAll(){
        Set<DataItem> items= holders.keySet();
        boolean checked =true;
        for(DataItem item :items){
            ViewHolder holder = holders.get(item);
           if (!holder.cbChoose.isChecked()){
               checked=false;
               break;
           }
        }
        return  checked;
    }


    /**
     * 对全部 的CheckBox执行操作
     * @param checked
     */
    private void execCBAll(boolean checked){
        Set<DataItem> items= holders.keySet();
        for(DataItem item :items){
            ViewHolder holder = holders.get(item);
            holder.cbChoose.setChecked(checked);
        }
    }

    class MyAdapter extends BaseAdapter {
        private DataItem item;
        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public DataItem getItem(int i) {
            return datas.get(i);
        }

        @Override
        public long getItemId(int i) {
            return getItem(i).id;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder ;
            if (view==null){
                view= getLayoutInflater().inflate(R.layout.layout_listview_data_item,null);
                holder = new ViewHolder(view);
                view.setTag(holder);

            }else{
                holder= (ViewHolder) view.getTag();
            }
            item = getItem(i);
            holder.cbChoose.setOnCheckedChangeListener(P1MainActivity.this);
            holder.cbChoose.setTag(item);
            holder.btDel.setOnClickListener(P1MainActivity.this);
            holder.btDel.setTag(item);
            holder.tvTitle.setText(item.title);
            holder.tvDesc.setText(item.desc);
            holders.put(item,holder);

            return view;
        }


    }
    class  ViewHolder {
        TextView tvTitle;
        TextView tvDesc;
        Button btDel;
        CheckBox cbChoose;
        public ViewHolder(View view){
            tvTitle = (TextView) view.findViewById(R.id.title);
            tvDesc = (TextView) view.findViewById(R.id.desc);
            btDel = (Button) view.findViewById(R.id.del);
            cbChoose = (CheckBox) view.findViewById(R.id.cb_choose);
        }
    }
}

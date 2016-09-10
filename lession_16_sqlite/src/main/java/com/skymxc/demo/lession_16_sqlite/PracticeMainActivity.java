package com.skymxc.demo.lession_16_sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.List;

public class PracticeMainActivity extends AppCompatActivity {

    private Spinner spCat ;
    private ListView lvContent;
    private CatAdapter catAdapter;
    private NewstAdapter  nAdapter;
    private List<Cat> cats;
    private List<News> news;
    private PracticeDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_main);
        spCat = (Spinner) findViewById(R.id.sp_cat);
        lvContent = (ListView) findViewById(R.id.lv_content);
        init();
        initCat();


    }


    /**
     * 初始化控件 DBHelper
     */
    private void init(){
        dbHelper = new PracticeDBHelper(this);

        news = new ArrayList<>();
        cats = new ArrayList<>();
        //TODO  为spinner 和ListView 绑定 adapter 设置监听器
        catAdapter = new CatAdapter(this,cats);

        nAdapter = new NewstAdapter(this,news);
        spCat.setOnItemSelectedListener(onItemSelectedLit);
        lvContent.setOnItemClickListener(null);
    }

    /**
     * 初始话 类型
     */
    private void initCat() {
        //TODO 网络访问获取 类型 使用公用 AsyncTask
        HttpTask<Result<List<Cat>>> task = new HttpTask<>(new TypeReference<Result<List<Cat>>>(){});
        task.execute("http://mapi.univs.cn/mobile/index.php?app=mobile&type=mobile&controller=content&action=category");
        task.setL(new HttpTask.OnResponseLintener<Result<List<Cat>>>() {
            @Override
            public void Response(Result<List<Cat>> result) {
                boolean status = result.state;
                if (status){
                    catAdapter = new CatAdapter(PracticeMainActivity.this,result.data);
                    spCat.setAdapter(catAdapter);
                    catAdapter.notifyDataSetChanged();
                //    catAdapter.setCats(result.data);
                    loadNews(spCat.getSelectedItemPosition());
                    saveCat(result.data);
                }else{
                    Toast.makeText(PracticeMainActivity.this,result.message,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void loadCat() {
        //TODO 从数据库读取cat 类型
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from cat ", new String[]{});
        List<Cat> cats = new ArrayList<>();
        while (cursor!=null && cursor.moveToNext()){
           Cat cat = new Cat();
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int catid = cursor.getInt(cursor.getColumnIndex("catid"));
            String catname = cursor.getString(cursor.getColumnIndex("catname"));
            String iconurl = cursor.getString(cursor.getColumnIndex("iconurl"));
            int    sort  = cursor.getInt(cursor.getColumnIndex("sort"));
            String url = cursor.getString(cursor.getColumnIndex("url"));
            cat.setCatid(catid);
            cat.setName(name);
            cat.setCatname(catname);
            cat.setIconurl(iconurl);
            cat.setUrl(url);
            cat.setSort(sort);
            cats.add(cat);
        }

        catAdapter = new CatAdapter(this,cats);
        spCat.setAdapter(catAdapter);
        catAdapter.notifyDataSetChanged();
        loadNews(spCat.getSelectedItemPosition());
    }

    private void saveCat(List<Cat> cats){
        //TODO 将之前的数据删除   新的类型 存入数据库
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        PracticeDBHelper.clearData("cat",db);
        String sql = "insert into cat values(?,?,?,?,?,?)";
        for (Cat cat :cats){
            db.execSQL(sql,new String[]{cat.getCatid()+"",cat.getCatname(),cat.getIconurl(),cat.getSort()+"",cat.getName(),cat.getUrl()});
        }

    }

    /**
     * 根据 选择的类型加载 新闻列表
     */
    private void loadNews(int index){
        //TODO 获取到spinner的选中项 获取到url 使用共用AsyncTask 加载 新闻列表 显示在ListView
        //TODO 新闻列表是空的时候显示 emptyView
        Cat cat = catAdapter.getItem(index);
        String url ="http://mapi.univs.cn/mobile/index.php?app=mobile&type=mobile&controller=content&content=index&catid="+cat.getCatid()+"&page=1&time=";
        HttpTask<Result<List<News>>> task = new HttpTask<>(new TypeReference<Result<List<News>>>(){});
        task.execute(url);
        task.setL(new HttpTask.OnResponseLintener<Result<List<News>> >() {
            @Override
            public void Response(Result<List<News>> listResult) {
                if (listResult.state){
                    nAdapter.setList(listResult.data);
                    nAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(PracticeMainActivity.this,listResult.message,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //下拉框选中监听
    private AdapterView.OnItemSelectedListener onItemSelectedLit = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            //TODO 根据下拉框的值 加载相应的新闻列表 显示在ListView
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
}

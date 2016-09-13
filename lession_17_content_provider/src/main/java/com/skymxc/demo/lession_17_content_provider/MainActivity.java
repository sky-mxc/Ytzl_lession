package com.skymxc.demo.lession_17_content_provider;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvContent;
    private EditText etName;
    private EditText etAge;
    private EditText etId ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContent = (TextView) findViewById(R.id.content);
        etName = (EditText) findViewById(R.id.name);
        etAge = (EditText) findViewById(R.id.age);
        etId = (EditText) findViewById(R.id._id);
        /**
         * parameter1 观察的uri
         * parameter2 uri的后代是否连带 观察
         * parameter3 observer
         */
        getContentResolver().registerContentObserver(Uri.parse("content://"+MyProvider.authorities+"/student"),true,this.observer);


        /**
         * parameter1 唯一id
         * parameter2  bundle 传递的参数
         * parameter3 LoaderCallbacks 回调
         */
      //  getLoaderManager().initLoader(1,null,callbacks);
    }


    private LoaderManager.LoaderCallbacks<Cursor> callbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        /**
         * loader创建
         * @param id 唯一id
         * @param args 初始化时的一些参数 与 初始化时 的参数对应
         * @return
         */
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    //上下文   ，要查询的地址 uri
            /**
             * parameter 1 context
             * parameter2 uri 需要查询的uri
             * parameter3 String[] 列 需要查询的列
             * parameter4 条件
             * parameter5 条件的值
             */
            return new  CursorLoader(MainActivity.this,Uri.parse("content://"+MyProvider.authorities+"/student"),new String[]{"_id","name","age"},"_id=?",new String[]{"2"},null);
        }

        /**
         * 加载完成  异步查询的 这个是回调的函数
         * @param loader 哪一个 loader
         * @param cursor  结果
         */
        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            StringBuffer sb = new StringBuffer("==========学生==============\n");
            while (cursor != null && cursor.moveToNext()){
                sb.append("id:"+cursor.getInt(cursor.getColumnIndex("_id"))+"\n");
                sb.append("name:"+cursor.getString(cursor.getColumnIndex("name"))+"\n");
                sb.append("age:"+cursor.getInt(cursor.getColumnIndex("age"))+"\n");
                sb.append("========================");
            }
            tvContent.setText(sb.toString());
        }

        /**
         * 加载器 重置时
         * @param loader
         */
        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.query_sms:
                querySms();
                break;
            case R.id.query_picture:
            pickImage();
                break;
            case R.id.query_student:
                queryStudent();
                break;
            case R.id.update_student:
               updateStudent();
                break;
            case R.id.insert:
                insert();
                break;
            case R.id.delete:
                delete();
                break;
        }
    }

    /**
     * 删除
     */
    private void delete() {
        String uri ="content://"+MyProvider.authorities+"/student";
        String id = etId.getText().toString();
        if (id.matches("\\d")){
            uri+="/"+id;
        }

        ContentResolver resolver = getContentResolver();
        resolver.delete(Uri.parse(uri),null,null);
    }

    /**
     * 插入学生
     */
    private void insert() {
        String name = etName.getText().toString();
        String age = etAge.getText().toString();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("age",age);
        getContentResolver().insert(Uri.parse("content://"+MyProvider.authorities+"/student"),values);
    }

    /**
     * 修改学生
     */
    private void updateStudent() {
        ContentResolver resolver = getContentResolver();
        ContentValues values = new ContentValues();
        values.put("name","sky-m");
        int line = resolver.update(Uri.parse("content://"+MyProvider.authorities+"/student"),values,"_id = ?",new String[]{"2"});
        Toast.makeText(MainActivity.this,"受影响的行数："+line,Toast.LENGTH_SHORT).show();
     //   queryStudent();
    }

    /**
     * 查询 学生
     */
    private void queryStudent() {
        String [] projections = new String[]{"_id","name","age"};
        ContentResolver resolver = getContentResolver();
        Cursor  cursor = resolver.query(Uri.parse("content://"+MyProvider.authorities+"/student"),projections,null,null,null);
        StringBuffer sb = new StringBuffer("==========学生==============");
        while (cursor != null && cursor.moveToNext()){
            sb.append("\nid:"+cursor.getInt(cursor.getColumnIndex("_id"))+"\n");
            sb.append("name:"+cursor.getString(cursor.getColumnIndex("name"))+"\n");
            sb.append("age:"+cursor.getInt(cursor.getColumnIndex("age"))+"\n");
            sb.append("========================");
        }
        tvContent.setText(sb.toString());
    }

    /**
     * 查图片
     */
    private void pickImage() {
        Intent in = new Intent(Intent.ACTION_PICK);
        in.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(in,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            queryImage(uri);
        }
    }

    /**
     * 查询图片信息
     * @param uri
     */
    private void queryImage(Uri uri){
        //MediaStore.Images.Media.DATA 获取图片路径
        Log.e("Tag","=====uri===="+uri);
        StringBuffer sb  = new StringBuffer("==========================");
        ContentResolver resolver = getContentResolver();
       Cursor c= resolver.query(uri,new String[]{MediaStore.Images.Media.DATA},null,null,null);
        while ( c !=null  && c.moveToNext()){
            sb.append(c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA))+"\n");
        }

        tvContent.setText(sb.toString());
    }

    /**
     * 短信查询
     */
    private void querySms() {
        String[] projection = new String[]{"_id","address","person","body","type"};
        StringBuffer sb = new StringBuffer("短信数据=============\n");
       ContentResolver resolver= getContentResolver();
       Cursor cursor = resolver.query(Uri.parse("content://sms/"),projection,null,null,null);
        while (cursor != null && cursor.moveToNext()){
            sb.append("id:"+cursor.getInt(cursor.getColumnIndex("_id")));
            sb.append("\naddress:"+cursor.getString(cursor.getColumnIndex("address")));
            sb.append("\nperson:"+cursor.getString(cursor.getColumnIndex("person")));
            sb.append("\nbody:"+cursor.getString(cursor.getColumnIndex("body")));
            sb.append("\ntype:"+cursor.getString(cursor.getColumnIndex("type")));
            sb.append("\n=================================================");
        }

        tvContent.setText(sb.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消observer的注册  cancel  register of observer
        getContentResolver().unregisterContentObserver(observer);
    }

    private ContentObserver observer = new ContentObserver(new Handler()) {
        /**
         *
         * @param selfChange 是否是自动改变的
         */
        @Override
        public void onChange(boolean selfChange) {
            Log.e("Tag","=====onChange============"+selfChange);
            queryStudent();
        }
    };
}

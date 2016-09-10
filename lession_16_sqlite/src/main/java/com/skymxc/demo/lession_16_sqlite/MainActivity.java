package com.skymxc.demo.lession_16_sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private DBHelper dbHelper;
    private EditText etId;
    private EditText etName;
    private EditText etPhone;
    private Spinner spGender;
    private ListView lv;
    private String phone;
    private String name;
    private String gender;
    private String id ;
    private SimpleCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
        etId = (EditText) findViewById(R.id.id);
        etName = (EditText) findViewById(R.id.name);
        etPhone = (EditText) findViewById(R.id.phone);
        spGender = (Spinner) findViewById(R.id.gender);
        lv = (ListView) findViewById(R.id.lv);
        //
        /**
         *
         * 使用cursorAdapter  注意事项
         * 必须查询主键
         * 主键列名 必许是 “_id"
         *
         */
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,null,new String[]{"_id","name"},new int[]{android.R.id.text1,android.R.id.text2});
        lv.setAdapter(cursorAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.delete:
                del();
                query();
                break;
            case R.id.add:
                add();
                query();
                break;
            case R.id.update:
                update();
                query();
                break;
            case R.id.query:
                query();
                break;
        }
    }

    /**
     * 查询
     */
    private void query() {
        getText();
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        List<String> params = new ArrayList();
        StringBuffer sb = new StringBuffer("select * from student where 1=1 ");
        if (!TextUtils.isEmpty(id)){
            sb.append(" and _id = ?");
            params.add(id);
        }
        if(!TextUtils.isEmpty(name)){
            sb.append(" and name = ?");
            params.add(name);
        }
        if(!TextUtils.isEmpty(phone)){
            sb.append(" and phone = ?");
            params.add(phone);
        }
//        if(!TextUtils.isEmpty(gender)){
//            sb.append(" and gender = ?");
//            params.add(gender);
//        }
        Log.e("Tag","query SQL:"+sb.toString());
        String [] projection = new String[params.size()];
        params.toArray(projection);
        //返回值 游标
        Cursor cursor = db.rawQuery(sb.toString(),projection);
        //不使用 CursorAdapter
       /* while (cursor!=null && cursor.moveToNext()){
            //cursor.getColumnIndex("name")  获取列的下标
            //getXxx(index)     获取指定列的值
           String name= cursor.getString(cursor.getColumnIndex("name"));
            int id = cursor.getInt(cursor.getColumnIndex("_id"));

        }*/

        //replace cursor
        cursorAdapter.swapCursor(cursor);
        cursorAdapter.notifyDataSetChanged();
        //注意 cursor 不能关闭 所以DB 也不能关闭
    }

    /**
     * 修改
     */
    private void update() {
        getText();
        StringBuffer sb = new StringBuffer("update student set");
        List params = new ArrayList();
        if(!TextUtils.isEmpty(name)){
          sb.append("  name = ? ,");
            params.add(name);
        }
        if(!TextUtils.isEmpty(phone)){
            sb.append("  phone = ? ,");
            params.add(phone);
        }
        if(!TextUtils.isEmpty(gender)){
            sb.append("   gender = ?,");
            params.add(gender);
        }
        if (params.size()==0){
            sb = new StringBuffer(sb.substring(0,sb.length()-3));
            Log.e("Tag","sbStr:"+sb.toString());
        }

        if (params.size()!=0){
          sb=new StringBuffer(sb.substring(0,sb.length()-1));
            Log.e("Tag","sbStr:"+sb.toString());
        }
        if (TextUtils.isEmpty(id)) {
            Toast.makeText(this,"id 是空的",Toast.LENGTH_SHORT);
            return;
        }else{
            sb.append(" where _id =?");
            params.add(id);
        }
        Log.e("Tag","update SQL："+sb.toString());
        if (params.size()>1){
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(sb.toString(),params.toArray());
            /**
             * 参数1 table
             * 参数2 ContentValues 要修改的数据
             * 参数3 whereClause 修改条件
             * 参数4 whereArgs 修改条件的值
             */
           // db.update("student",cv,"_id =?",new String[]{id});
            Toast.makeText(this,"update  success",Toast.LENGTH_SHORT).show();


        }else{
            Toast.makeText(this,"无修改项，无法修改",Toast.LENGTH_SHORT).show();
        }


        /**
         *  db.beginTransaction();  开始事物
         *  db.setTransactionSuccessful();  事务执行成功
         *  db.endTransaction();     没有设置事务执行成功  在结束时会自动回滚
         */



    }


    /**
     * 删除
     */
    private void del() {
        getText();
        StringBuffer sb = new StringBuffer("delete from student where 1=1 ");
        List params = new ArrayList();
        if (!TextUtils.isEmpty(id)){
            sb.append(" and _id = ?");
            params.add(id);
        }
        if(!TextUtils.isEmpty(name)){
            sb.append(" and name = ?");
            params.add(name);

        }
        if(!TextUtils.isEmpty(phone)){
            sb.append(" and phone = ?");
            params.add(phone);
        }
        if(!TextUtils.isEmpty(gender)){
            sb.append(" and gender = ?");
            params.add(gender);
        }
        Log.e("Tag","DelSQL:"+sb.toString());
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        if (params.size()!=0){

            Object[] paramsAry = new Object[params.size()];
            params.toArray(paramsAry);
            db.execSQL(sb.toString(),paramsAry);
        }else{
            db.execSQL(sb.toString());
        }
        /**
         * 参数1 table  表名
         * 参数2 whereClause 条件语句
         * 参数3 whereArgs 条件中占位符的参数
         */
  //      db.delete("student","_id =? and name =?",new String[]{id,name});
        Toast.makeText(this,"删除完成",Toast.LENGTH_SHORT).show();

    }

    private void getText() {
         id = etId.getText().toString();
        phone    = etPhone.getText().toString();
        name     = etName.getText().toString();
        gender   = spGender.getSelectedItem().toString();
    }

    /**
     * 添加操作
     */
    private void add() {
        getText();
        SQLiteDatabase  db = dbHelper.getWritableDatabase();
        Object [] params = new Object[]{name,phone,gender};
        db.execSQL("insert into student values(null,?,?,?)",params);
        db.close(); //关闭数据库对象
        Log.e("Tag","==========insert================");
//        ContentValues cv = new ContentValues();
//        if (!TextUtils.isEmpty(id)){
//            cv.put("_id",id);
//        }
//        if (!TextUtils.isEmpty(name)){
//            cv.put("name",name);
//        }
//        if (!TextUtils.isEmpty(gender)){
//            cv.put("gender",gender);
//        }
//        /**
//         * insert 方法的参数
//         * 参数1 table
//         * 参数2 不插入的列
//         * 参数3 ContentValues 插入参数
//         */
//        db.insert("student","phone",cv);

    }
}

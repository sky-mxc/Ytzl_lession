package com.skymxc.demo.lession_17_content_provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by sky-mxc
 */
public class MyProvider extends ContentProvider {

    private DBHelper dbHelper ;
    private UriMatcher uriMatcher ;//uri的匹配器
    public static final String authorities ="com.skymxc.demo.lession_17_content_provider";

    public static final int STUDENT=0;
    public static final int STUDENTS=1;
    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);   //code: if  match faild return  this code
        /**
         * 成功失败都会 返回一个 code  失败 就会返回上面写的那个 ，success 返回 adduri中指定的code (STUDENTS)
         *
         */

        //添加能够匹配的uri格式 参数1authorities 匹配住机。  参数2 匹配路径   参数3 code ： match success  return this code；
        uriMatcher.addURI(authorities,"student",STUDENTS);
        /**
         * # :match number
         */
        uriMatcher.addURI(authorities,"student/#",STUDENT);
        return true;
    }

    /**
     *
     * @param uri uri
     * @param prejections  查询的列 数组
     * @param condition  条件
     * @param values  占位的值
     * @param order 排序
     * @return cursor
     */
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] prejections, String condition, String[] values, String order) {
        SQLiteDatabase db = dbHelper.getDB();
        Cursor cursor =null;
        switch (uriMatcher.match(uri)){
            case STUDENT:
                //uri中取出id ContentUris 工具类
                long  id = ContentUris.parseId(uri);
                String  where ="_id = "+id;
                if (!TextUtils.isEmpty(condition)){
                    where+= " and "+condition;
                }
                cursor = db.query(DBHelper.TABLE_NAME,prejections,where,values,null,null,order);
                break;
            case STUDENTS:
                cursor = db.query(DBHelper.TABLE_NAME,prejections,condition,values,null,null,order);
                break;
            default:
                throw new IllegalArgumentException("match faild 。uri:"+uri+"");

        }
        /**
         * 使用 cursorLoader时 使用
         */
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        String  type ="unknow";
        switch (uriMatcher.match(uri)){
            case STUDENT:
                type="vnd.android.cursor.item/student";
                break;
            case STUDENTS:
                type = "vnd.android.curosr.dir/student";
                break;
        }
        return type;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        int line =0;
        switch (uriMatcher.match(uri)){
            case STUDENT:
                break;
            case STUDENTS:
                SQLiteDatabase db = dbHelper.getDB();
                line= (int) db.insert(DBHelper.TABLE_NAME,"_id",contentValues);

                break;
        }
        if (line>0){
            //通知观察者
            getContext().getContentResolver().notifyChange(uri,null);
        }

        return uri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        int line =0;
        SQLiteDatabase db = dbHelper.getDB();
        Log.e("Tag","=delete=S:"+s);
        switch (uriMatcher.match(uri)){
            case STUDENT:
                int id = (int) ContentUris.parseId(uri);
               Log.e("Tag","=delete======id:"+id);
                String where = " _id = "+id;
                if (s!=null){
                    where+= " and "+ s;
                }

              line=  db.delete(DBHelper.TABLE_NAME,where,strings);
                break;
            case STUDENTS:
                line =db.delete(DBHelper.TABLE_NAME,s,strings);
                break;
        }

        if (line>0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return line;
    }

    /**
     *
     * @param uri
     * @param contentValues 列
     * @param condition
     * @param strings
     * @return
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String condition, String[] strings) {
        SQLiteDatabase db = dbHelper.getDB();
        int line =0;
        switch (uriMatcher.match(uri)){
            case STUDENT:
                //uri中取出id ContentUris 工具类
                long  id = ContentUris.parseId(uri);
                String  where ="_id = "+id;
                if (!TextUtils.isEmpty(condition)){
                    where+= " and "+condition;
                }
               line = db.update(DBHelper.TABLE_NAME,contentValues,where,strings);
                break;
            case STUDENTS:
                line = db.update(DBHelper.TABLE_NAME,contentValues,condition,strings);
                break;
            default:
                throw new IllegalArgumentException("match faild 。uri:"+uri+"");

        }
        //数据改变时 notify observer
        if (line>0) {
            /**
             * 首先查找 uri 扫描（手机上）所有的注册的observer 的uri  匹配之后执行 observer的onChange 方法
             */
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return line;
    }
}

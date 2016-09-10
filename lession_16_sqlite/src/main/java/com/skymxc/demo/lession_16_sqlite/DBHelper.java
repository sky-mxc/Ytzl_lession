package com.skymxc.demo.lession_16_sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sky-mxc
 */
public class DBHelper extends SQLiteOpenHelper {
    //数据库的名
    private static final String name ="skyDB";
    //数据库版本
    private static final int version =1;

    /**
     *
     * @param context 上下文
     */
    public DBHelper(Context context) {
        /**
         * name 数据库名
         * null 游标工厂
         * version 数据库版本
         */
        super(context, name, null, version);
    }

    /**
     * 初始化 数据库
     * @param db 操作数据库的对象
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //因为要使用 cursorAdapter  id 必须是_ 开头

        db.execSQL("create table student(_id integer not null primary key autoincrement,name varchar(20),phone varchar(11),gender varchar(2))");
        db.execSQL("insert into student values(null,?,?,?)",new Object[]{"张三","110","男"});

    }

    /**
     * 数据库升级
     * @param db 数据操作对象
     * @param i 旧版本号
     * @param i1 新版本号
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists student");
        onCreate(db);
    }



}

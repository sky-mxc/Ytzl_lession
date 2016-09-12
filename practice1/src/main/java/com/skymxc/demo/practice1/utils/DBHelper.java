package com.skymxc.demo.practice1.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sky-mxc
 */
public class DBHelper extends SQLiteOpenHelper {
    private static  String name = "skyDB";
    private static  int version =1;
    public DBHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //TODO 创建数据库
        sqLiteDatabase.execSQL("create table dataItem (_id integer primary key  autoincrement ,title varchar(20) not null,desc varchar(50) not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

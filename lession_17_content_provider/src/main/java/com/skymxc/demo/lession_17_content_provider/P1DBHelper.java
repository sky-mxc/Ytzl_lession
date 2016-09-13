package com.skymxc.demo.lession_17_content_provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sky-mxc
 */
public class P1DBHelper extends SQLiteOpenHelper {
    private static final String NAME ="sky.db";
    public static final String TABLE_NAME="person";
    private static final int version = 1;
    private SQLiteDatabase db;

    public SQLiteDatabase getDB(){
        if (db==null){
            db = this.getWritableDatabase();
        }
        return  db;
    }
    public P1DBHelper(Context context) {
        super(context, NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+"(_id integer primary key autoincrement ," +
                "path varchar(100)," +
                "title varchar(100)," +
                "describe varchar(100))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

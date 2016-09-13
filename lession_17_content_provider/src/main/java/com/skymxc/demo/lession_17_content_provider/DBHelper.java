package com.skymxc.demo.lession_17_content_provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sky-mxc
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME ="student";

    private SQLiteDatabase db;
    public  SQLiteDatabase getDB(){
        if (db ==null){
            db = this.getWritableDatabase();
        }
        return  db;
    }

    public DBHelper(Context context) {
        super(context, "sky_provider.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+TABLE_NAME+" (_id integer primary key autoincrement," +
                "name varchar(200)," +
                "age integer )");
        sqLiteDatabase.execSQL("insert into "+TABLE_NAME+" values(null,'张三',23)");
        sqLiteDatabase.execSQL("insert into "+TABLE_NAME+" values(null,'李四',24)");
        sqLiteDatabase.execSQL("insert into "+TABLE_NAME+" values(null,'王五',25)");
        sqLiteDatabase.execSQL("insert into "+TABLE_NAME+" values(null,'赵六',26)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

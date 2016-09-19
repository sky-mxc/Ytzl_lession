package com.skymxc.demo.lession_19_animation.practice.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by sky-mxc
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String NAME ="sky.db";
    public static final int version =1;
    public static final String  TABLE_NAME ="user";
    private static Context mContext;
    private static DBHelper dbHelper;
    private SQLiteDatabase database;


    public static void init(Context context){
        mContext = context;
        Log.e("Tag","====init===========");
    }

    public synchronized static DBHelper getInstance(Context context){
       // mContext=context;
        if (dbHelper ==null){
            dbHelper = new DBHelper(context);
        }
       return  dbHelper;
    }

    public  SQLiteDatabase getDB(){
        if (database == null){
            database = getInstance(mContext).getWritableDatabase();
        }
        return  database;
    }

    public DBHelper(Context context) {

        super(context, NAME,null, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+DBHelper.TABLE_NAME+" (" +
                "_id integer primary key autoincrement," +
                "account varchar(20) not null unique," +
                "pwd varchar(20) not null)");

        ContentValues cv = new ContentValues();
        cv.put("account","sky");
        cv.put("pwd","1234");
        db.insert(DBHelper.TABLE_NAME,"_id",cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

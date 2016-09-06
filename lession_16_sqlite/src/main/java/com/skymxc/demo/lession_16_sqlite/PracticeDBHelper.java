package com.skymxc.demo.lession_16_sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sky-mxc
 */
public class PracticeDBHelper extends SQLiteOpenHelper{
    private static final String name="skyDB";
    private static final int version =1;
    public PracticeDBHelper(Context context ) {
        super(context, name,null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sb = new StringBuffer("create table cat (");
        sb.append("catid integer primary key,");
        sb.append("catname varchar(20) ,");
        sb.append("iconurl varchar(20) ,");
        sb.append("sort integer ,");
        sb.append("name varchar(20) ,");
        sb.append("url varchar(20) )");
        db.execSQL(sb.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}

package com.skymxc.demo.lession_17_content_provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by sky-mxc
 */
public class P1Provider extends ContentProvider {
    private UriMatcher uriMatcher ;
    private static final int   PERSON=1;
    private static final int   PERSONS=2;
    private P1DBHelper helper;
    public static final String authority="com.sky.example" ;
    @Override
    public boolean onCreate() {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(authority,"person",PERSONS);
        uriMatcher.addURI(authority,"person/#",PERSON);
        helper = new P1DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projections, String condition, String[] values, String order) {
        SQLiteDatabase db = helper.getDB();

        Cursor cursor =null;
        switch (uriMatcher.match(uri)){
            case PERSONS:
                cursor = db.rawQuery("select * from "+P1DBHelper.TABLE_NAME,null);
                break;
            case PERSON:
                int id = (int) ContentUris.parseId(uri);
                 String sql ="select * from "+P1DBHelper.TABLE_NAME+" where _id ="+id;
                cursor = db.rawQuery(sql,null);
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = helper.getDB();
        int line =0;
        switch (uriMatcher.match(uri)){
            case PERSONS:
                line = (int) db.insert(P1DBHelper.TABLE_NAME,"_id",contentValues);
                break;
        }
        Log.e("Tag","=====insert======="+line);
        if (line>0){
            getContext().getContentResolver().notifyChange(uri,null);
        }

        return uri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}

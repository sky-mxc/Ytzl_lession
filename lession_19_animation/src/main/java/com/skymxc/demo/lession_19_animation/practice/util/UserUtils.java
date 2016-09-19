package com.skymxc.demo.lession_19_animation.practice.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skymxc.demo.lession_19_animation.practice.entity.User;

/**
 * Created by sky-mxc
 */
public class UserUtils {

    /**
     * 插入
     * @param db
     * @param user
     * @return
     */
    public static int insert(SQLiteDatabase db, User user){
        ContentValues cv = new ContentValues();
        cv.put("account",user.account);
        cv.put("pwd",user.pwd);
      return (int)  db.insert(DBHelper.TABLE_NAME,"_id",cv);
    }

    /**
     * 判断 账号是否存在
     * @param account
     * @return
     */
    public static User isExists(String account,SQLiteDatabase db){
        User user =null;
       Cursor cursor = db.rawQuery("select * from "+DBHelper.TABLE_NAME+" where account= ?",new String[]{account});
        if (cursor != null && cursor.moveToFirst()){
            user = new User();
            user.account = cursor.getString(cursor.getColumnIndex("account"));
            user.id = cursor.getInt(cursor.getColumnIndex("_id"));
            user.pwd = cursor.getString(cursor.getColumnIndex("pwd"));
        }
        cursor.close();

        return  user;
    }
}

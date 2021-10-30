package com.pslproject.testexample.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.pslproject.testexample.Model.entity.NoteUserData;

import java.util.ArrayList;

public class UserOpenHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public UserOpenHelper(Context context) {
        super(context, "db_user", null, 1);
        db=getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists user("+
                "_id integer primary key autoincrement,"+
                "username verchar,"+
                "account verchar,"+
                "password verchar,"+
                "imgurl verchar)");
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("drop table if exists user");
        onCreate(sqLiteDatabase);
    }

    public void add(String username,String account,String password){
        db.execSQL("insert into user (username,account,password,imgurl) values(?,?,?,?)",new Object[]{username,account,password,"empty"});
    }

    //只能通过Id删除
    public void delete(String userAccount){
        db.execSQL("delete from user where _id="+getId(userAccount));
    }

    public void updateUsername(String username,String account){
        db.execSQL("update user set username=? where _id=?",new Object[]{username,getId(account)});
    }

    public void updatePassword(String password,String account){
        db.execSQL("update user set password=? where _id=?",new Object[]{password,getId(account)});
    }

    public ArrayList<NoteUserData> getAllUserData(){
        ArrayList<NoteUserData> userData=new ArrayList<NoteUserData>();
        Cursor cursor=db.query("user",null,null,null,null,null,null);
        while (cursor.moveToNext()){
            @SuppressLint("Range") int userId=cursor.getInt(cursor.getColumnIndex("_id"));
            @SuppressLint("Range") String username=cursor.getString(cursor.getColumnIndex("username"));
            @SuppressLint("Range") String account=cursor.getString(cursor.getColumnIndex("account"));
            @SuppressLint("Range") String password=cursor.getString(cursor.getColumnIndex("password"));
            @SuppressLint("Range") String imgurl=cursor.getString(cursor.getColumnIndex("imgurl"));
            userData.add(new NoteUserData(username,account,password,imgurl));
        }
        return userData;
    }


    @SuppressLint("Range")
    public int getId(String userAccount){
        Cursor cursor=db.query("user",null,null,null,null,null,null);
        while(cursor.moveToNext()){
            if(userAccount.equals(cursor.getString(cursor.getColumnIndex("account")))){
                return cursor.getInt(cursor.getColumnIndex("_id"));
            }
        }
        return 9999;
    }
}

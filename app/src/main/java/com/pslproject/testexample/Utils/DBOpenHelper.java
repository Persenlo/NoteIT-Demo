package com.pslproject.testexample.Utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.pslproject.testexample.Model.entity.NoteData;

import java.util.ArrayList;

public class DBOpenHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public DBOpenHelper(Context context) {
        super(context, "db_note", null, 1);
        db = getReadableDatabase();
    }


    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists note("+
                "_id integer primary key autoincrement,"+
                "title verchar,"+
                "date verchar,"+
                "info verchar,"+
                "author verchar,"+
                "authorId verchar,"+
                "category verchar,"+
                "password verchar,"+
                "isLock verchar)");
    }


    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists note");
        onCreate(db);
    }

    public void add(String title,String date,String info,String author,String account,String category,String password,String isLock) {
        db.execSQL("insert into note (title,date,info,author,authorId,category,password,isLock) values(?,?,?,?,?,?,?,?)", new Object[]{title,date,info,author,account,category,password,isLock});
    }

    public void delete(int itemId){
        db.execSQL("delete from note where _id="+itemId);
    }

    public void updateTitle(String title,int itemId){
        db.execSQL("update note set title=? where _id=?",new Object[]{title,itemId});
    }

    public void updateDate(String date,int itemId){
        db.execSQL("update note set date=? where _id=?",new Object[]{date,itemId});
    }

    public void updateInfo(String info,int itemId){
        db.execSQL("update note set info=? where _id=?",new Object[]{info,itemId});
    }

    public void updateAuthor(String author,int itemId){
        db.execSQL("update note set author=? where _id=?",new Object[]{author,itemId});
    }

    public void updateAuthorId(String authorId,int itemId){
        db.execSQL("update note set authorId=? where _id=?",new Object[]{authorId,itemId});
    }

    public void updateCategory(String category,int itemId){
        db.execSQL("update note set category=? where _id=?",new Object[]{category,itemId});
    }

    public void updatePassword(String password,int itemId){
        db.execSQL("update note set password=? where _id=?",new Object[]{password,itemId});
    }

    public void updateIsLock(String isLock,int itemId){
        db.execSQL("update note set isLock=? where _id=?",new Object[]{isLock,itemId});
    }

    public ArrayList<NoteData> getAllData(){
        ArrayList<NoteData> list=new ArrayList<NoteData>();
        Cursor cursor=db.query("note",null,null,null,null,null,null);
        while (cursor.moveToNext()){
            @SuppressLint("Range") int itemId=cursor.getInt(cursor.getColumnIndex("_id"));
            @SuppressLint("Range") String title=cursor.getString(cursor.getColumnIndex("title"));
            @SuppressLint("Range") String date=cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String info=cursor.getString(cursor.getColumnIndex("info"));
            @SuppressLint("Range") String author=cursor.getString(cursor.getColumnIndex("author"));
            @SuppressLint("Range") String authorId=cursor.getString(cursor.getColumnIndex("authorId"));
            @SuppressLint("Range") String category=cursor.getString(cursor.getColumnIndex("category"));
            @SuppressLint("Range") String password=cursor.getString(cursor.getColumnIndex("password"));
            @SuppressLint("Range") String isLock=cursor.getString(cursor.getColumnIndex("isLock"));
            list.add(new NoteData(title,date,info,author,authorId,itemId,category,password,isLock));
        }
        return list;
    }

}

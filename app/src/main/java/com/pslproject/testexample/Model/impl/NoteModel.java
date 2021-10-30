package com.pslproject.testexample.Model.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.service.autofill.UserData;

import com.pslproject.testexample.Model.entity.NoteData;
import com.pslproject.testexample.MvpCallback;
import com.pslproject.testexample.Utils.DBOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class NoteModel {

    private static DBOpenHelper dbOpenHelper;
    private static SharedPreferences tempUserData;

    //获取全部笔记
    public static List<NoteData> getNoteFromDB(Context context, MvpCallback<String> mvpCallback){

        dbOpenHelper=new DBOpenHelper(context);
        List<NoteData> noteDataList=new ArrayList<>();
        noteDataList=dbOpenHelper.getAllData();

        mvpCallback.onSuccess("success");

        return noteDataList;
    }

    //按账号获取笔记
    public static List<NoteData> getNoteByAccount(Context context, String userAccount, MvpCallback<String> mvpCallback){

        tempUserData= context.getSharedPreferences("user.xml",0);
        dbOpenHelper=new DBOpenHelper(context);
        List<NoteData> noteDataList=new ArrayList<>();
        List<NoteData> accountNoteList=new ArrayList<>();
        noteDataList=dbOpenHelper.getAllData();
        for(int i=0;i<noteDataList.size();i++){
            NoteData tempData=noteDataList.get(i);
            if(tempData.getAccount().equals(tempUserData.getString("account",""))){
                accountNoteList.add(tempData);
            }
        }

        mvpCallback.onSuccess("success");

        return accountNoteList;
    }

    //按内容获取笔记
    public static List<NoteData> getNoteByInfo(Context context,String info,MvpCallback<String> mvpCallback){

        tempUserData= context.getSharedPreferences("user.xml",0);
        dbOpenHelper=new DBOpenHelper(context);
        List<NoteData> noteDataList=new ArrayList<>();
        List<NoteData> accountNoteList=new ArrayList<>();
        noteDataList=dbOpenHelper.getAllData();
        for(int i=0;i<noteDataList.size();i++){
            NoteData tempData=noteDataList.get(i);
            if(tempData.getAccount().equals(tempUserData.getString("account",""))){
                if(tempData.getInfo().indexOf(info)!=-1||tempData.getTitle().indexOf(info)!=-1){
                    accountNoteList.add(tempData);
                }
            }
        }

        mvpCallback.onSuccess("success");

        return accountNoteList;
    }

    //按分类获取笔记
    public static List<NoteData> getNoteByCategory(Context context,String category,MvpCallback<String> mvpCallback){

        tempUserData= context.getSharedPreferences("user.xml",0);
        dbOpenHelper=new DBOpenHelper(context);
        List<NoteData> noteDataList=new ArrayList<>();
        List<NoteData> accountNoteList=new ArrayList<>();
        noteDataList=dbOpenHelper.getAllData();
        for(int i=0;i<noteDataList.size();i++){
            NoteData tempData=noteDataList.get(i);
            if(tempData.getAccount().equals(tempUserData.getString("account",""))){
                if(tempData.getCategory().equals(category)){
                    accountNoteList.add(tempData);
                }
            }
        }

        mvpCallback.onSuccess("success");

        return accountNoteList;
    }

    //创建笔记
    public static void createNote(Context context,String title,String date,String info,String author,String account,String category,String password,String isLock,MvpCallback<String> mvpCallback) {

        dbOpenHelper=new DBOpenHelper(context);
        dbOpenHelper.add(title,date,info,author,account,category,password,isLock);

        mvpCallback.onSuccess("success");

    }

    //更新笔记
    public static void updateNote(Context context,String title,String date,String info,String author,int itemId,String category,String password,String isLock,MvpCallback<String> mvpCallback){

        dbOpenHelper=new DBOpenHelper(context);
        dbOpenHelper.updateTitle(title,itemId);
        dbOpenHelper.updateDate(date,itemId);
        dbOpenHelper.updateInfo(info,itemId);
        dbOpenHelper.updateAuthor(author,itemId);
        dbOpenHelper.updateCategory(category,itemId);
        dbOpenHelper.updatePassword(password,itemId);
        dbOpenHelper.updateIsLock(isLock,itemId);

        mvpCallback.onSuccess("success");
    }

    //删除笔记
    public static void deleteNote(Context context,Integer id,MvpCallback<String> mvpCallback){
        dbOpenHelper=new DBOpenHelper(context);
        dbOpenHelper.delete(id);

        mvpCallback.onSuccess("success");
    }


    //获取分类
    public static List<String> getCategory(Context context,MvpCallback<String> mvpCallback){
        List<String> categoryList=new ArrayList<>();
        dbOpenHelper=new DBOpenHelper(context);
        tempUserData= context.getSharedPreferences("user.xml",0);

        List<NoteData> noteDataList=dbOpenHelper.getAllData();
        for(int i=0;i<noteDataList.size();i++){
            NoteData data=noteDataList.get(i);
            if(data.getAccount().equals(tempUserData.getString("account",""))){
                if(!categoryList.contains(data.getCategory())){
                    categoryList.add(data.getCategory());
                }
            }
        }
        mvpCallback.onSuccess("success");
        return categoryList;
    }




}

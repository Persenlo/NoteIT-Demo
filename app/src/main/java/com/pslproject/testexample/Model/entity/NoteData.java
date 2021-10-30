package com.pslproject.testexample.Model.entity;


/**
 * Note数据集
 * 2021.9.23
 * by Persenlo
 */
public class NoteData {
    public String title;
    public String date;
    public String info;
    public String author;//用户名
    public String account;//账号
    public int itemId;//笔记Id
    public String category;//分类
    public String password;//密码
    public String isLock;//是否加密

    public NoteData(String title,String data,String info,String author,String account,int authorId,String category,String password,String isLock){
        this.title=title;
        this.date=data;
        this.info=info;
        this.author=author;
        this.account=account;
        this.itemId=authorId;
        this.category=category;
        this.password=password;
        this.isLock=isLock;
    }

    public String getTitle(){ return this.title; }

    public String getDate(){ return this.date; }

    public String getInfo(){ return this.info; }

    public String getAuthor(){ return this.author; }

    public String getAccount(){ return this.account; }

    public int getItemId(){ return this.itemId;}

    public String getCategory(){ return this.category; }

    public String getLockPassword(){ return this.password; }

    public String getLock(){ return this.isLock; }

}

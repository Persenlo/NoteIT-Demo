package com.pslproject.testexample.Model.entity;

public class NoteUserData {

    private String username;
    private String account;
    private String password;
    private String ImgUrl;

    public NoteUserData (String username,String account,String password,String imgUrl){
        this.username=username;
        this.account=account;
        this.password=password;
        this.ImgUrl=imgUrl;
    }

    public void setImgUrl(String ImgUrl){
        this.ImgUrl=ImgUrl;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public String getUsername(){
        return username;
    }

    public String getAccount(){
        return account;
    }

    public String getPassword(){
        return password;
    }

    public String getImgUrl(){
        return ImgUrl;
    }
}

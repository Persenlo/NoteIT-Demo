package com.pslproject.testexample.Model.impl;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.renderscript.Short4;
import android.util.Log;

import com.pslproject.testexample.Model.entity.NoteUserData;
import com.pslproject.testexample.MvpCallback;
import com.pslproject.testexample.R;
import com.pslproject.testexample.Utils.UserOpenHelper;

import java.util.ArrayList;

public class AccountModel {


    private static UserOpenHelper userOpenHelper;


    //处理注册数据
    public static void checkRegister(String username, String account, String password, Context context, final MvpCallback<String> callback){

        boolean isOccupied=false;



        userOpenHelper=new UserOpenHelper(context);
        ArrayList<NoteUserData> data=userOpenHelper.getAllUserData();
        //判断账号是否重复
        for(int i=0;i<data.size();i++){
            NoteUserData user=data.get(i);
            String tempAcc=user.getAccount();
            if(tempAcc.equals(account)){
                isOccupied=true;
            }
        }

        //执行注册
        if(!isOccupied){
            userOpenHelper.add(username,account,password);
            callback.onSuccess(context.getString(R.string.login_register_success));//注册成功
        }else callback.onError(context.getString(R.string.login_register_account_occupy));//注册失败（账号被占用）

        callback.onComplete();

    }




    //处理登录数据
    public static void checkLogin(String account,String password,Context context,final MvpCallback<String> callback){

        boolean accountExist=false;
        boolean loginSuccess=false;

        int locate=0;

        userOpenHelper=new UserOpenHelper(context);
        ArrayList<NoteUserData> data=userOpenHelper.getAllUserData();
        //获取匹配的账号
        for(int i=0;i<data.size();i++){
            NoteUserData user=data.get(i);
            if (user.getAccount().equals(account)){
                accountExist=true;
                if (user.getPassword().equals(password)){
                    loginSuccess=true;
                    locate=i;
                    break;
                }else break;
            }
        }
        if(loginSuccess){

            NoteUserData user=data.get(locate);
            //持久化登录
            SharedPreferences sharedPreferences=context.getSharedPreferences("user.xml",MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();

            editor.putString("username",user.getUsername());
            editor.putString("account", user.getAccount());
            editor.putString("password",user.getPassword());
            editor.putString("imgurl",user.getImgUrl());
            editor.commit();



            callback.onSuccess("success");
            callback.onComplete();//登录成功

        }else if(accountExist){

            callback.onError(context.getString(R.string.login_login_password_error));
            callback.onFailure();

        }//登录失败
        else {

            callback.onError(context.getString(R.string.login_login_account_error));
            callback.onFailure();

        }

    }

    //使用公共账户登录
    public static void usePublicToLogin(Context context,MvpCallback<String> mvpCallback){
        SharedPreferences sharedPreferences=context.getSharedPreferences("user.xml",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putString("username","public");
        editor.putString("account", "public");
        editor.putString("password","public");
        editor.putString("imgurl","empty");
        editor.commit();

        mvpCallback.onSuccess("success");
    }

    //更改用户名
    public static void changeUsername(String username,String account,Context context,MvpCallback<String> mvpCallback){
        SharedPreferences sharedPreferences=context.getSharedPreferences("user.xml",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putString("username",username);
        editor.commit();

        userOpenHelper=new UserOpenHelper(context);
        userOpenHelper.updateUsername(username,account);

        mvpCallback.onSuccess("success");
    }

    //更改密码
    public static void changePassword(String password,String account,Context context,MvpCallback<String> mvpCallback){
        SharedPreferences sharedPreferences=context.getSharedPreferences("user.xml",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putString("password",password);
        editor.commit();

        userOpenHelper=new UserOpenHelper(context);
        userOpenHelper.updatePassword(password,account);

        mvpCallback.onSuccess("success");
    }


    //删除账户
    public static void deleteAccount(String account,Context context,MvpCallback<String> mvpCallback){

        SharedPreferences sharedPreferences=context.getSharedPreferences("user.xml",MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();

        editor.putString("username","");
        editor.putString("account","");
        editor.putString("password","");
        editor.putString("imgurl","");
        editor.commit();

        userOpenHelper=new UserOpenHelper(context);
        userOpenHelper.delete(account);

        mvpCallback.onSuccess("success");
    }
}

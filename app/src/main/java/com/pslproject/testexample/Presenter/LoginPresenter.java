package com.pslproject.testexample.Presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.pslproject.testexample.Model.entity.NoteUserData;
import com.pslproject.testexample.Model.impl.AccountModel;
import com.pslproject.testexample.MvpCallback;
import com.pslproject.testexample.Utils.UserOpenHelper;
import com.pslproject.testexample.View.LoginView;

import java.util.ArrayList;

public class LoginPresenter extends BasePresenter<LoginView>{

    //登录，让Model处理登录数据
    public void startLogin(String account, String password){

        if(!isAttached()){
            return;
        }

        getView().showLoading();

        //调用Model处理数据
        AccountModel.checkLogin(account, password, getContext(), new MvpCallback<String>() {
            @Override
            public void onSuccess(String data) {

                //发送全局广播，重载当前用户
                Intent BCIntent=new Intent();
                BCIntent.setAction("com.pslproject.textexample.reloadUser");
                getContext().sendBroadcast(BCIntent);

                getView().loginSuccess();

            }

            @Override
            public void onError(String error) {
                getView().loginFailure(error);
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    //使用公共账号，让Model修改账户数据
    public void usePublic(){

        if(!isAttached())
            return;

        AccountModel.usePublicToLogin(getContext(), new MvpCallback<String>() {
            @Override
            public void onSuccess(String data) {

                //发送全局广播，重载当前用户
                Intent BCIntent=new Intent();
                BCIntent.setAction("com.pslproject.textexample.reloadUser");
                getContext().sendBroadcast(BCIntent);

                getView().loginSuccess();
            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}

package com.pslproject.testexample.Presenter;

import android.content.Intent;
import android.util.Log;

import com.pslproject.testexample.Model.impl.AccountModel;
import com.pslproject.testexample.MvpCallback;
import com.pslproject.testexample.View.UserView;

public class UserPresenter extends BasePresenter<UserView>{

    public void getNoteCount(){}

    //修改用户名
    public void changeUsername(String username,String account){
        if(!isAttached())
            return;

        AccountModel.changeUsername(username, account, getContext(), new MvpCallback<String>() {
            @Override
            public void onSuccess(String data) {
                //发送全局广播，重载当前用户
                Intent BCIntent=new Intent();
                BCIntent.setAction("com.pslproject.textexample.reloadUser");
                getContext().sendBroadcast(BCIntent);

                getView().changeUsernameComplete();
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

    //修改密码
    public void changePassword(String password,String account){
        if(!isAttached())
            return;

        AccountModel.changePassword(password, account, getContext(), new MvpCallback<String>() {
            @Override
            public void onSuccess(String data) {
                //发送全局广播，重载当前用户
                Intent BCIntent=new Intent();
                BCIntent.setAction("com.pslproject.textexample.reloadUser");
                getContext().sendBroadcast(BCIntent);

                getView().changePasswordComplete();
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

    //注销
    public void logout(){

        if(!isAttached()) {
            return;
        }

        AccountModel.usePublicToLogin(getContext(), new MvpCallback<String>() {
            @Override
            public void onSuccess(String data) {

                //发送全局广播，重载当前用户
                Intent BCIntent=new Intent();
                BCIntent.setAction("com.pslproject.textexample.reloadUser");
                getContext().sendBroadcast(BCIntent);

                getView().logoutComplete();
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



    //删除账号
    public void deleteAccount(String account){

        if(!isAttached()){
            Log.e("error","1");
            return;
        }

        AccountModel.deleteAccount(account, getContext(), new MvpCallback<String>() {
            @Override
            public void onSuccess(String data) {

                //发送全局广播，重载当前用户
                Intent BCIntent=new Intent();
                BCIntent.setAction("com.pslproject.textexample.reloadUser");
                getContext().sendBroadcast(BCIntent);

                getView().deleteComplete();
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

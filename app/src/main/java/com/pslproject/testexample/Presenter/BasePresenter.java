package com.pslproject.testexample.Presenter;

import android.content.Context;

import com.pslproject.testexample.View.BaseView;


/**
 * Presenter基类（负责管理Presenter与View的绑定）
 * last update：2021.10.15
 * author：Persenlo
 */

public class BasePresenter <V extends BaseView>{

    private V mvpView;

    public void attachView(V mvpView){
        this.mvpView=mvpView;
    }

    public void detachView(){
        this.mvpView=null;
    }

    public boolean isAttached(){
        return mvpView!=null;
    }

    public V getView(){
        return this.mvpView;
    }

    public Context getContext(){
        return mvpView.getContext();
    }
}

package com.pslproject.testexample.Presenter;

import android.content.Intent;
import android.util.Log;
import android.view.Display;

import com.pslproject.testexample.Model.entity.NoteData;
import com.pslproject.testexample.Model.impl.NoteModel;
import com.pslproject.testexample.MvpCallback;
import com.pslproject.testexample.View.MainView;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter extends BasePresenter<MainView>{

    public List<NoteData> getNote(){

        List<NoteData> list=new ArrayList<>();

        if(!isAttached())
            return list;

        list=NoteModel.getNoteFromDB(getContext(), new MvpCallback<String>() {
            @Override
            public void onSuccess(String data) {
                //发送全局广播，重载当前列表
                Intent BCIntent=new Intent();
                BCIntent.setAction("com.pslproject.textexample.reloadList");
                getContext().sendBroadcast(BCIntent);

                getView().hideLoading();
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

        return list;
    }

    //按账号获取笔记
    public List<NoteData> getNoteByAccount(String account){
        List<NoteData> noteDataList=new ArrayList<>();
        if(!isAttached())
            return noteDataList;

        noteDataList=NoteModel.getNoteByAccount(getContext(), account, new MvpCallback<String>() {
            @Override
            public void onSuccess(String data) {
                //发送全局广播，重载当前列表
                Intent BCIntent=new Intent();
                BCIntent.setAction("com.pslproject.textexample.reloadList");
                getContext().sendBroadcast(BCIntent);

                getView().hideLoading();
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
        return noteDataList;
    }


    public void deleteNote(List<Integer> list){
        if(!isAttached()){
            return;
        }

        for (Integer i = 0; i<list.size();i++){
            NoteModel.deleteNote(getContext(), list.get(i), new MvpCallback<String>() {
                @Override
                public void onSuccess(String data) {

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
        //发送全局广播，重载列表
        Intent BCIntent1=new Intent();
        BCIntent1.setAction("com.pslproject.textexample.reloadUser");
        getContext().sendBroadcast(BCIntent1);

        getView().deleteSuccess();
        Intent BCIntent2=new Intent();
        BCIntent2.setAction("com.pslproject.textexample.reloadList");
        getContext().sendBroadcast(BCIntent2);

    }

    public List<String> getCategory(){

        List<String> categoryList=new ArrayList<>();

        if(!isAttached())
            return categoryList;

        categoryList=NoteModel.getCategory(getContext(), new MvpCallback<String>() {
            @Override
            public void onSuccess(String data) {

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
        return categoryList;
    }



}

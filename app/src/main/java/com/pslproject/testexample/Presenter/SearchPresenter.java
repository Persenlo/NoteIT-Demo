package com.pslproject.testexample.Presenter;

import android.content.Intent;

import com.pslproject.testexample.Model.entity.NoteData;
import com.pslproject.testexample.Model.impl.NoteModel;
import com.pslproject.testexample.MvpCallback;
import com.pslproject.testexample.View.SearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchPresenter extends BasePresenter<SearchView>{

    public List<NoteData> getNoteByInfo(String info){

        List<NoteData> noteDataList=new ArrayList<>();

        if (!isAttached()){
            return noteDataList;
        }

        noteDataList=NoteModel.getNoteByInfo(getContext(), info, new MvpCallback<String>() {
            @Override
            public void onSuccess(String data) {
                getView().searchFinish();
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


    //按分类获取笔记
    public List<NoteData> getNoteByCategory(String category){

        List<NoteData> noteDataList=new ArrayList<>();

        if (!isAttached())
            return noteDataList;

        noteDataList=NoteModel.getNoteByCategory(getContext(), category, new MvpCallback<String>() {
            @Override
            public void onSuccess(String data) {
                getView().searchFinish();
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


    //删除笔记
    public void deleteNote(Integer id){

        if (!isAttached())
            return;

        NoteModel.deleteNote(getContext(), id, new MvpCallback<String>() {
            @Override
            public void onSuccess(String data) {
                getView().deleteSuccess();

                //发送全局广播，重载主列表
                Intent BCIntent1=new Intent();
                BCIntent1.setAction("com.pslproject.textexample.reloadUser");
                getContext().sendBroadcast(BCIntent1);

                getView().deleteSuccess();
                Intent BCIntent2=new Intent();
                BCIntent2.setAction("com.pslproject.textexample.reloadList");
                getContext().sendBroadcast(BCIntent2);

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

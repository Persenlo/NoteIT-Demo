package com.pslproject.testexample.Presenter;

import android.content.Intent;

import com.pslproject.testexample.Model.impl.NoteModel;
import com.pslproject.testexample.MvpCallback;
import com.pslproject.testexample.Presenter.BasePresenter;
import com.pslproject.testexample.View.EditorView;

public class EditorPresenter extends BasePresenter<EditorView> {

    public void createNote(String title,String date,String info,String author,String account ,String category,String password,String isLock){
        if(!isAttached())
            return;

        NoteModel.createNote(getContext(), title, date, info, author, account , category, password, isLock, new MvpCallback<String>() {
            @Override
            public void onSuccess(String data) {

                //发送全局广播，重载列表
                Intent BCIntent1=new Intent();
                BCIntent1.setAction("com.pslproject.textexample.reloadUser");
                getContext().sendBroadcast(BCIntent1);

                getView().createNoteSuccess();
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
    };

    public void updateNote(String title,String date,String info,String author,int ItemId,String category,String password,String isLock){
        if(!isAttached())
            return;

        NoteModel.updateNote(getContext(), title, date, info, author, ItemId, category, password, isLock, new MvpCallback<String>() {
            @Override
            public void onSuccess(String data) {
                //发送全局广播，重载列表
                Intent BCIntent1=new Intent();
                BCIntent1.setAction("com.pslproject.textexample.reloadUser");
                getContext().sendBroadcast(BCIntent1);

                Intent BCIntent2=new Intent();
                BCIntent2.setAction("com.pslproject.textexample.reloadList");
                getContext().sendBroadcast(BCIntent2);



                getView().updateNoteSuccess();
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
    };
}

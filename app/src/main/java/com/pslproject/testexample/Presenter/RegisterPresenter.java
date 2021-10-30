package com.pslproject.testexample.Presenter;

import com.pslproject.testexample.Model.entity.NoteUserData;
import com.pslproject.testexample.Model.impl.AccountModel;
import com.pslproject.testexample.MvpCallback;
import com.pslproject.testexample.View.RegisterView;

/**
 * RegisterPresenter
 * last update：2021.10.15
 * author：Persenlo
 */

public class RegisterPresenter extends BasePresenter<RegisterView>{



    //让Model处理注册的数据
    public void startRegister(String username,String account,String password){
        //检查是否与View绑定
        if(!isAttached()){
            return;
        }

        getView().showLoading();

        //调用Model处理数据
        AccountModel.checkRegister(username, account, password, getContext(), new MvpCallback<String>() {
            @Override
            public void onSuccess(String info) {
                if(isAttached()){
                    getView().RegisterSuccess(info);
                }
            }

            @Override
            public void onError(String error) {
                if(isAttached()){
                    getView().RegisterFailure(error);
                }
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

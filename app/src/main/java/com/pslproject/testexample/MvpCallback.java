package com.pslproject.testexample;

/**
 * MVP Callback 接口
 * Last Update:2021.10.15
 * author:Persenlo
 */
public interface MvpCallback<T> {

    void onSuccess(String data);

    void onError(String error);

    void onFailure();

    void onComplete();
}

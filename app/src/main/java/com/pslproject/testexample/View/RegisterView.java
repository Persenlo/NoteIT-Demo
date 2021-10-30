package com.pslproject.testexample.View;


/**
 * RegisterView接口
 * last update：2021.10.15
 * author：Persenlo
 */
public interface RegisterView extends BaseView{

    void RegisterSuccess(String info);

    void RegisterFailure(String error);
}

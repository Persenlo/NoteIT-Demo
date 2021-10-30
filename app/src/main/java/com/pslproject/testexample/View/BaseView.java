package com.pslproject.testexample.View;

import android.content.Context;

/**
 * BaseView接口
 * last update：2021.10.15
 * author：Persenlo
 */

public interface BaseView {

    void showLoading();

    void hideLoading();

    void showError();

    Context getContext();
}

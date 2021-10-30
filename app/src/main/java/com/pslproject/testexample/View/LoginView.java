package com.pslproject.testexample.View;

import android.os.Bundle;

public interface LoginView extends BaseView{
    void loginSuccess();

    void loginFailure(String error);
}

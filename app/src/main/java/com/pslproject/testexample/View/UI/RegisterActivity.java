package com.pslproject.testexample.View.UI;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.pslproject.testexample.Presenter.RegisterPresenter;
import com.pslproject.testexample.R;
import com.pslproject.testexample.Utils.BaseActivity;
import com.pslproject.testexample.View.RegisterView;

public class RegisterActivity extends BaseActivity implements RegisterView {

    private ImageButton back;
    private Button register;
    private EditText username;
    private EditText account;
    private EditText password;

    RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }

    @Override
    public void init() {
        back=findViewById(R.id.bt_register_back);
        register=findViewById(R.id.bt_register_register);
        username=findViewById(R.id.et_register_username);
        account=findViewById(R.id.et_register_account);
        password=findViewById(R.id.et_register_password);

        presenter=new RegisterPresenter();
        presenter.attachView(this);


        //设置按钮监听
        //返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //注册
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.startRegister(username.getText().toString(),account.getText().toString(),password.getText().toString());
            }
        });
    }



    //接口功能实现
    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void RegisterSuccess(String info) {
        Toast.makeText(this,info,Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void RegisterFailure(String error) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
    }
}
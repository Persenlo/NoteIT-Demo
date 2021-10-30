package com.pslproject.testexample.View.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.pslproject.testexample.Presenter.LoginPresenter;
import com.pslproject.testexample.R;
import com.pslproject.testexample.Utils.BaseActivity;
import com.pslproject.testexample.View.LoginView;

public class LoginActivity extends BaseActivity implements LoginView {

    private ImageButton back;
    private Button register;
    private Button login;
    private Button usePublic;
    private EditText account;
    private EditText password;

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    @Override
    public void init() {
        back=findViewById(R.id.bt_login_back);
        register=findViewById(R.id.bt_login_register);
        login=findViewById(R.id.bt_login_login);
        usePublic=findViewById(R.id.bt_login_public);

        account=findViewById(R.id.et_login_account);
        password=findViewById(R.id.et_login_password);

        LoginPresenter presenter=new LoginPresenter();
        presenter.attachView(this);

        sharedPreferences=getSharedPreferences("user.xml",0);

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
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        //登录
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.startLogin(account.getText().toString(),password.getText().toString());
            }
        });
        //使用公共账户
        usePublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.usePublic();
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
    public void loginSuccess() {
        Toast.makeText(this,R.string.login_login_success,Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void loginFailure(String error) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
    }
}
package com.pslproject.testexample.View.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pslproject.testexample.Presenter.UserPresenter;
import com.pslproject.testexample.R;
import com.pslproject.testexample.Utils.BaseActivity;
import com.pslproject.testexample.View.UserView;

public class UserActivity extends BaseActivity implements UserView {

    private ImageView icon;
    private TextView username;
    private TextView userAccount;
    private TextView noteCount;

    private Button changePassword;
    private Button changeUsername;
    private Button logout;
    private Button deleteAccount;
    private ImageButton back;

    SharedPreferences sharedPreferences;

    UserPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        init();
        loadInfo();
    }

    //加载界面信息
    private void loadInfo() {
        username.setText(sharedPreferences.getString("username",""));
        userAccount.setText(sharedPreferences.getString("account",""));
    }


    @Override
    public void init() {

        //绑定组件
        icon=findViewById(R.id.iv_user_img);
        username=findViewById(R.id.tv_user_username);
        userAccount=findViewById(R.id.tv_user_account);
        noteCount=findViewById(R.id.tv_user_count);
        changePassword=findViewById(R.id.bt_user_password_change);
        changeUsername=findViewById(R.id.bt_user_username_change);
        logout=findViewById(R.id.bt_user_logout);
        deleteAccount=findViewById(R.id.bt_user_account_delete);
        back=findViewById(R.id.bt_user_back);

        sharedPreferences=getSharedPreferences("user.xml",0);

        presenter=new UserPresenter();
        presenter.attachView(this);
        

        //设置按钮监听
        //修改用户名
        changeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogP dialogP=new DialogP(UserActivity.this,2);
                dialogP.setTitle(getString(R.string.user_username_change))
                        .setHint(getString(R.string.user_username_change_input))
                        .setOnButtonClickListener(new DialogP.onButtonClickListener() {
                            @Override
                            public void onPositiveButtonClick() {
                                presenter.changeUsername(dialogP.getText(),sharedPreferences.getString("account",""));
                                dialogP.dismiss();
                            }

                            @Override
                            public void onNegativeButtonClick() {
                                dialogP.dismiss();
                            }
                        }).show();
            }
        });
        //修改密码
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogP dialogP=new DialogP(UserActivity.this,2);
                dialogP.setTitle(getString(R.string.user_password_change))
                        .setHint(getString(R.string.user_password_change_input))
                        .setOnButtonClickListener(new DialogP.onButtonClickListener() {
                            @Override
                            public void onPositiveButtonClick() {
                                presenter.changePassword(dialogP.getText(),sharedPreferences.getString("account",""));
                                dialogP.dismiss();
                            }

                            @Override
                            public void onNegativeButtonClick() {
                                dialogP.dismiss();
                            }
                        }).show();
            }
        });
        //注销
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogP dialogP=new DialogP(UserActivity.this,1);
                dialogP.setTitle(getString(R.string.user_logout_common))
                        .setMessage(getString(R.string.user_logout_confirm))
                        .setOnButtonClickListener(new DialogP.onButtonClickListener() {
                            @Override
                            public void onPositiveButtonClick() {
                                presenter.logout();
                                dialogP.dismiss();
                                finish();
                            }

                            @Override
                            public void onNegativeButtonClick() {
                                dialogP.dismiss();
                            }
                        }).show();

            }
        });
        //删除账户
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogP dialogP=new DialogP(UserActivity.this,1);
                dialogP.setTitle(getString(R.string.user_delete_common))
                        .setMessage(getString(R.string.user_delete_confirm))
                        .setOnButtonClickListener(new DialogP.onButtonClickListener() {
                            @Override
                            public void onPositiveButtonClick() {
                                presenter.deleteAccount(sharedPreferences.getString("account",""));
                                dialogP.dismiss();
                                finish();
                            }

                            @Override
                            public void onNegativeButtonClick() {
                                dialogP.dismiss();
                            }
                        }).show();
            }
        });
        //返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
    public void noteLoadComplete() {

    }

    @Override
    public void changeUsernameComplete() {
        username.setText(sharedPreferences.getString("username",""));
        Toast.makeText(UserActivity.this,R.string.user_username_change_success,Toast.LENGTH_SHORT).show();
    }

    public void changePasswordComplete(){
        Toast.makeText(UserActivity.this,R.string.user_password_change_success,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void logoutComplete() {
        Toast.makeText(this, R.string.user_logout_success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteComplete() {
        Toast.makeText(this, R.string.user_delete_success, Toast.LENGTH_SHORT).show();
    }
}
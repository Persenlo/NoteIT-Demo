package com.pslproject.testexample.Utils;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import java.util.Calendar;

/**
 * App基类
 * 2021.9.23
 * by Persenlo
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 添加到Activity工具类
        ActivityUtil.getInstance().addActivity(this);

        //沉浸效果
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){

            /**
             * 透明导航栏
             * 状态栏遮挡UI可在最外层layout中使用“android:fitsSystemWindows="true”“属性来解决
             */
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            //状态栏黑色字体
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.white));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        }

        //保持竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    //初始化方法（抽象）
    public abstract void init();

    //防止系统字体影响应用字体大小
    public void onResume() {
        super.onResume();
        Resources resources=this.getResources();
        Configuration configuration=resources.getConfiguration();
        //“1”为系统标准字体大小
        configuration.fontScale=1;
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
    }

    //销毁时回收Activity
    public void onDestroy() {
        ActivityUtil.getInstance().removeActivity(this);
        super.onDestroy();

    }

    //返回键销毁Activity
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==event.KEYCODE_BACK){
            this.finish();
            ActivityUtil.getInstance().removeActivity(this);
        }
        return super.onKeyDown(keyCode,event);
    }

    //获取系统日期
    public String getDate(){

        String date;
        int month;
        String minute;

        Calendar calendar=Calendar.getInstance();

        month=calendar.get(Calendar.MONTH)+1;

        if(calendar.get(Calendar.MINUTE)<10){
            minute= "0"+calendar.get(Calendar.MINUTE);
        }else
            minute=String.valueOf(calendar.get(Calendar.MINUTE));

        date= calendar.get(Calendar.YEAR) +"."
                + month +"."
                + calendar.get(Calendar.DAY_OF_MONTH)+"——"
                +calendar.get(Calendar.HOUR_OF_DAY)+":"
                +minute+":"
                +calendar.get(Calendar.SECOND);
        return date;
    }



}


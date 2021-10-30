package com.pslproject.testexample.View.UI;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pslproject.testexample.Adapter.CategoryAdapter;
import com.pslproject.testexample.Adapter.MainAdapter;
import com.pslproject.testexample.R;
import com.pslproject.testexample.Utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * DialogP（自定义Dialog）
 * Last update:2021.10.20
 * author:Persenlo
 */
public class DialogP extends Dialog {

    /**
     * DialogP显示模式
     * 0:一个按钮
     * 1：两个按钮
     * 2：可编辑文本
     * 3：启用RecyclerView
     */
    private static final int DIALOG_P_EASY=0;
    private static final int DIALOG_P_NORMAL=1;
    private static final int DIALOG_P_EDIT=2;
    private static final int DIALOG_P_LIST=3;

    private int dialogType=0;

    //组件
    private TextView title;
    private TextView message;
    private Button positiveButton;
    private Button negativeButton;
    private RecyclerView recyclerView;
    private TextInputLayout textInputLayout;
    private TextInputEditText textInputEditText;

    public onButtonClickListener onButtonClickListener;

    List<String> categoryList=new ArrayList<>();

    //可编辑内容
    private String titleP;
    private String messageP;
    private String posBtnP;
    private String negBtnP;
    private String hintP;

    private CategoryAdapter adapter;
    Context context;

    SharedPreferences state;
    SharedPreferences.Editor stateEditor;

    //构造方法，绑定style
    public DialogP(@NonNull Context context,int dialogType) {
        super(context,R.style.DialogP);
        this.context=context;
        this.dialogType=dialogType;
    }
    public DialogP(@NonNull Context context,int dialogType,List<String> list) {
        super(context,R.style.DialogP);
        this.categoryList=list;
        this.context=context;
        this.dialogType=dialogType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_p);

        state = context.getSharedPreferences("state.xml",0);
        stateEditor=state.edit();
        //可从空白处关闭
        setCanceledOnTouchOutside(true);
        initView();
        refreshView();
        initEvent();
    }





    private void initView() {
        title=findViewById(R.id.tv_dialog_p_title);
        message=findViewById(R.id.tv_dialog_p_message);
        positiveButton=findViewById(R.id.bt_dialog_p_confirm);
        negativeButton=findViewById(R.id.bt_dialog_p_cancel);
        recyclerView=findViewById(R.id.rv_dialog_p_main);
        textInputLayout=findViewById(R.id.et_dialog_p_layout);
        textInputEditText=findViewById(R.id.et_dialog_p_text);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter=new CategoryAdapter(context,categoryList);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent BCIntent1=new Intent();
                BCIntent1.setAction("com.pslproject.textexample.dialogp.click");
                stateEditor.putString("dialogP_Click",categoryList.get(position));
                stateEditor.commit();
                stateEditor.clear();
                context.sendBroadcast(BCIntent1);
                dismiss();
            }

            @Override
            public void onLongItemClick(View view, int position) {
                Intent BCIntent1=new Intent();
                BCIntent1.setAction("com.pslproject.textexample.dialogp.longclick");
                stateEditor.putString("dialogP_LongClick",categoryList.get(position));
                stateEditor.commit();
                stateEditor.clear();
                context.sendBroadcast(BCIntent1);

            }
        }));

    }

    private void refreshView() {
        if(dialogType==0){

            negativeButton.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            textInputLayout.setVisibility(View.GONE);
            textInputEditText.setVisibility(View.GONE);
            if(!TextUtils.isEmpty(titleP))
                title.setText(titleP);
            if(!TextUtils.isEmpty(messageP))
                message.setText(messageP);
            if (!TextUtils.isEmpty(posBtnP))
                positiveButton.setText(posBtnP);

        }else if(dialogType==1){

            recyclerView.setVisibility(View.GONE);
            textInputLayout.setVisibility(View.GONE);
            textInputEditText.setVisibility(View.GONE);
            if(!TextUtils.isEmpty(titleP))
                title.setText(titleP);
            if(!TextUtils.isEmpty(messageP))
                message.setText(messageP);
            if (!TextUtils.isEmpty(posBtnP))
                positiveButton.setText(posBtnP);
            if(!TextUtils.isEmpty(negBtnP))
                negativeButton.setText(negBtnP);

        }else if(dialogType==2){

            message.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            if(!TextUtils.isEmpty(titleP))
                title.setText(titleP);
            if(!TextUtils.isEmpty(hintP))
                textInputLayout.setHint(hintP);
            if (!TextUtils.isEmpty(posBtnP))
                positiveButton.setText(posBtnP);
            if(!TextUtils.isEmpty(negBtnP))
                negativeButton.setText(negBtnP);

        }else if(dialogType==3){

            message.setVisibility(View.GONE);
            textInputLayout.setVisibility(View.GONE);
            textInputEditText.setVisibility(View.GONE);
            if(!TextUtils.isEmpty(titleP))
                title.setText(titleP);
            if (!TextUtils.isEmpty(posBtnP))
                positiveButton.setText(posBtnP);
            if(!TextUtils.isEmpty(negBtnP))
                negativeButton.setText(negBtnP);


        }else return;
    }

    public void show(){
        super.show();
        refreshView();
    }

    //处理按钮事件
    private void initEvent() {
        //确定
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onButtonClickListener!=null)
                    onButtonClickListener.onPositiveButtonClick();
            }
        });
        //取消
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onButtonClickListener!=null)
                    onButtonClickListener.onNegativeButtonClick();
            }
        });
    }

    public interface onButtonClickListener{

        public void onPositiveButtonClick();

        public void onNegativeButtonClick();
    }

    public DialogP setOnButtonClickListener(onButtonClickListener onButtonClickListener){
        this.onButtonClickListener=onButtonClickListener;
        return this;
    }

    public DialogP setTitle(String title){
        this.titleP=title;
        return this;
    }

    public DialogP setMessage(String message){
        this.messageP=message;
        return this;
    }

    public DialogP setPosBtnText(String text){
        this.posBtnP=text;
        return this;
    }

    public DialogP setNegBtnText(String text){
        this.negBtnP=text;
        return this;
    }

    public DialogP setHint(String hint){
        this.hintP=hint;
        return this;
    }



    public String getText(){
        return textInputEditText.getText().toString();
    }
}

package com.pslproject.testexample.View.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.pslproject.testexample.R;
import com.pslproject.testexample.Utils.BaseActivity;
import com.pslproject.testexample.Presenter.EditorPresenter;
import com.pslproject.testexample.View.EditorView;

/**
 * 编辑界面
 * Last update:2021.10.23
 * author:Persenlo
 */
public class EditorActivity extends BaseActivity implements EditorView {

    public TextView activityTitle;
    public EditText EtTitle;
    public EditText EtInfo;
    public TextView date;
    public Button save;
    public ImageButton back;
    public Button category;
    public Button lock;
    public Button update;

    public String CreateDate;
    public String editorState;

    private SharedPreferences state;
    private SharedPreferences tempNote;
    private SharedPreferences sharedPreferences;

    private String tTitle;
    private String tDate;
    private String tInfo;
    private String tAuthor;
    private String tAccount;
    private String tCategory;
    private String tLockPassword;
    private String tLock;
    private int tItemId;

    private EditorPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        presenter = new EditorPresenter();
        presenter.attachView(this);

        init();
    }

    @Override
    public void init() {
        activityTitle = findViewById(R.id.tv_editor_avtivity_title);
        EtTitle = findViewById(R.id.et_editor_title);
        EtInfo = findViewById(R.id.et_editor_info);
        date = findViewById(R.id.tv_editor_date);
        save = findViewById(R.id.bt_editor_save);
        back = findViewById(R.id.bt_editor_back);
        category = findViewById(R.id.bt_editor_category);
        lock = findViewById(R.id.bt_editor_lock);
        update = findViewById(R.id.bt_editor_update);

        sharedPreferences = getSharedPreferences("user.xml", 0);
        state = getSharedPreferences("state.xml", 0);
        tempNote = getSharedPreferences("tempNote.xml", 0);

        editorState = state.getString("editorState", "");

        tCategory = category.getText().toString();

        tAccount=sharedPreferences.getString("account","");



        //保存
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editorState.equals("create")){
                    tTitle = EtTitle.getText().toString();
                    tDate = date.getText().toString();
                    tInfo = EtInfo.getText().toString();
                    tAuthor = sharedPreferences.getString("username", "");
                    if (TextUtils.isEmpty(tLockPassword)){
                        tLockPassword = "";
                        tLock = "unlock";
                    } else tLock = "lock";
                    presenter.createNote(tTitle,tDate,tInfo,tAuthor,tAccount,tCategory,tLockPassword,tLock);
                }//新建笔记
                else if(editorState.equals("edit")){
                    tTitle = EtTitle.getText().toString();
                    tDate = date.getText().toString();
                    tInfo = EtInfo.getText().toString();
                    tCategory = category.getText().toString();
                    tAuthor = sharedPreferences.getString("username", "");
                    presenter.updateNote(tTitle,tDate,tInfo,tAuthor,tItemId,tCategory,tLockPassword,tLock);
                }//编辑笔记
                else {
                    Toast.makeText(EditorActivity.this,R.string.editor_save_error,Toast.LENGTH_SHORT).show();
                    finish();
                }//编辑器发生错误
            }
        });
        //类别
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogP dialogP = new DialogP(EditorActivity.this, 2);
                dialogP.setTitle(getString(R.string.main_category_common))
                        .setHint(getString(R.string.main_category_input))
                        .setOnButtonClickListener(new DialogP.onButtonClickListener() {
                            @Override
                            public void onPositiveButtonClick() {
                                tCategory=dialogP.getText();
                                category.setText(tCategory);
                                dialogP.dismiss();
                            }

                            @Override
                            public void onNegativeButtonClick() {
                                dialogP.dismiss();
                            }
                        }).show();
            }
        });
        //加密
        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tLock.equals("unlock")) {
                    DialogP dialogP = new DialogP(EditorActivity.this, 2);
                    dialogP.setTitle(getString(R.string.editor_lock_common))
                            .setHint(getString(R.string.editor_lock_input))
                            .setOnButtonClickListener(new DialogP.onButtonClickListener() {
                                @Override
                                public void onPositiveButtonClick() {
                                    tLockPassword = dialogP.getText();
                                    tLock="lock";
                                    lock.setText(R.string.editor_lock_unlock);
                                    dialogP.dismiss();
                                }

                                @Override
                                public void onNegativeButtonClick() {
                                    dialogP.dismiss();
                                }
                            }).show();
                }else if(tLock.equals("lock")){
                    lock.setText(R.string.editor_lock_common);
                    tLockPassword="";
                    tLock="unlock";
                }
            }
        });
        //更新日期
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date.setText(getDate());
                Toast.makeText(EditorActivity.this,R.string.editor_date_update_finish,Toast.LENGTH_SHORT).show();
            }
        });
        //返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        if(editorState.equals("create")) {
            setViewWithCreate();
        }else if(editorState.equals("edit")){
            //从缓存中获取数据
            tTitle = tempNote.getString("title", "");
            tDate = tempNote.getString("date", "");
            tInfo = tempNote.getString("info", "");
            tAuthor = tempNote.getString("author", "");
            tCategory = tempNote.getString("category", "");
            tLockPassword = tempNote.getString("password", "");
            tLock = tempNote.getString("lock", "");
            tItemId=tempNote.getInt("id",0);

            if(tLock.equals("lock"))
                lock.setText(R.string.editor_lock_unlock);

            activityTitle.setText(getString(R.string.editor_editor_common));
            EtTitle.setText(tTitle);
            date.setText(tDate);
            EtInfo.setText(tInfo);
            category.setText(tCategory);
        }else {
            Toast.makeText(EditorActivity.this,R.string.editor_save_error,Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    public void setViewWithCreate() {
        tLock="unlock";
        CreateDate = getDate();
        date.setText(CreateDate);
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
        return EditorActivity.this;
    }

    @Override
    public void createNoteSuccess() {
        Toast.makeText(EditorActivity.this,R.string.editor_create_success,Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void updateNoteSuccess() {
        Toast.makeText(EditorActivity.this,R.string.editor_update_success,Toast.LENGTH_SHORT).show();
        finish();
    }
}

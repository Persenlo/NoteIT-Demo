package com.pslproject.testexample.View.UI;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pslproject.testexample.Adapter.MainAdapter;
import com.pslproject.testexample.Model.entity.NoteData;
import com.pslproject.testexample.Presenter.MainPresenter;
import com.pslproject.testexample.R;
import com.pslproject.testexample.Utils.BaseActivity;
import com.pslproject.testexample.Utils.RecyclerItemClickListener;
import com.pslproject.testexample.View.MainView;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面
 * Last Update:2021.10.22
 * author:Persenlo
 */

public class MainActivity extends BaseActivity implements MainView {

    private Toolbar toolbar;
    private ExtendedFloatingActionButton search;
    private FloatingActionButton add;
    private FloatingActionButton delete;
    private FloatingActionButton close;
    private Button account;
    private Button category;
    private RecyclerView recyclerView;
    private MainAdapter adapter;

    private String username;
    private String userAccount;
    List<NoteData> noteDataList;
    List<Integer> deleteList;

    private SharedPreferences sharedPreferences;
    private SharedPreferences state;
    ReloadUserReceiver reloadUserReceiver;
    ReloadListReceiver reloadListReceiver;
    DialogPClickReceiver dialogPClickReceiver;

    MainPresenter presenter;

    boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    public void init() {

        toolbar = findViewById(R.id.tb_main);
        search = findViewById(R.id.eFab_main_search);
        add = findViewById(R.id.fb_main_add);
        delete = findViewById(R.id.fb_main_delete);
        close = findViewById(R.id.fb_main_close);
        account = findViewById(R.id.bt_main_account);
        category = findViewById(R.id.bt_main_category);
        setSupportActionBar(toolbar);

        deleteList=new ArrayList<>();

        recyclerView=findViewById(R.id.rv_main);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        sharedPreferences = getSharedPreferences("user.xml", 0);
        state=getSharedPreferences("state.xml",0);
        SharedPreferences.Editor stateEditor=state.edit();

        presenter=new MainPresenter();
        presenter.attachView(this);


        //动态注册广播接收器
        reloadUserReceiver = new ReloadUserReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.pslproject.textexample.reloadUser");
        registerReceiver(reloadUserReceiver,intentFilter);

        reloadListReceiver = new ReloadListReceiver();
        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction("com.pslproject.textexample.reloadList");
        registerReceiver(reloadListReceiver,intentFilter1);

        dialogPClickReceiver = new DialogPClickReceiver();
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("com.pslproject.textexample.dialogp.click");
        registerReceiver(dialogPClickReceiver,intentFilter2);

        //按钮监听
        //分类
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> categoryList=new ArrayList<>();
                categoryList=presenter.getCategory();

                DialogP dialogP=new DialogP(MainActivity.this,3,categoryList);
                dialogP.setTitle(getString(R.string.main_category_common))
                        .setPosBtnText("GitHub")
                        .setOnButtonClickListener(new DialogP.onButtonClickListener() {
                            @Override
                            public void onPositiveButtonClick() {
                                Uri uri=Uri.parse("https://github.com/Persenlo/NoteIT-Demo");
                                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                                startActivity(intent);
                            }

                            @Override
                            public void onNegativeButtonClick() {
                                dialogP.dismiss();
                            }
                        })
                        .show();

            }
        });
        //搜索
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogP dialogP=new DialogP(MainActivity.this,2);
                dialogP.setTitle(getString(R.string.search_main_name))
                        .setHint(getString(R.string.search_main_input))
                        .setOnButtonClickListener(new DialogP.onButtonClickListener() {
                            @Override
                            public void onPositiveButtonClick() {
                                if(!TextUtils.isEmpty(dialogP.getText())){
                                    Intent intent=new Intent(MainActivity.this, SearchActivity.class);
                                    intent.putExtra("info",dialogP.getText());
                                    startActivity(intent);
                                    dialogP.dismiss();
                                }else
                                    Toast.makeText(MainActivity.this, R.string.search_main_empty, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNegativeButtonClick() {
                                dialogP.dismiss();
                            }
                        }).show();
            }
        });
        //新建
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stateEditor.putString("editorState","create");
                stateEditor.commit();
                Intent intent1 = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent1);
            }
        });
        //账户
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
                Intent intent2 = new Intent(MainActivity.this, UserActivity.class);
                if(username.equals(getString(R.string.login_public_common))){
                    startActivity(intent1);
                }else startActivity(intent2);
            }
        });
        //删除
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogP dialogP=new DialogP(MainActivity.this,1);
                dialogP.setTitle(getString(R.string.editor_delete_common))
                        .setMessage(getString(R.string.editor_delete_confirm))
                        .setOnButtonClickListener(new DialogP.onButtonClickListener() {
                            @Override
                            public void onPositiveButtonClick() {
                                presenter.deleteNote(deleteList);
                                //关闭编辑模式
                                editMode=false;
                                reloadEditMode();
                                adapter.closeEditMode();
                                recyclerView.setAdapter(adapter);
                                dialogP.dismiss();
                            }

                            @Override
                            public void onNegativeButtonClick() {
                                dialogP.dismiss();
                            }
                        }).show();
            }
        });
        //关闭编辑模式
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMode=false;
                Toast.makeText(MainActivity.this, R.string.main_edit_mode_off, Toast.LENGTH_SHORT).show();
                reloadEditMode();
                adapter.closeEditMode();
                recyclerView.setAdapter(adapter);
            }
        });

        //加载界面信息
        reloadUser();
        reloadList();
        getList();
        reloadEditMode();

        //recycler点击监听
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(editMode){//编辑模式
                    if(deleteList.contains(adapter.getId(position))){//已存在
                        adapter.closeHL(position);
                        deleteList.remove(deleteList.indexOf(adapter.getId(position)));

                    }else {//未存在
                        adapter.highLight(position);
                        deleteList.add(adapter.getId(position));
                    }

                }else adapter.openItem(position);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                if (!editMode) {
                    editMode = true;
                    reloadEditMode();
                    Toast.makeText(MainActivity.this, R.string.main_edit_mode_on, Toast.LENGTH_SHORT).show();
                }else return;
            }
        }));
    }

    public void getList(){
        noteDataList = presenter.getNoteByAccount(sharedPreferences.getString("account",""));
        adapter.closeEditMode();
    }

    //编辑模式
    public void reloadEditMode(){
        if(editMode){
            delete.setVisibility(View.VISIBLE);
            close.setVisibility(View.VISIBLE);
        }else {
            delete.setVisibility(View.INVISIBLE);
            close.setVisibility(View.INVISIBLE);
        }
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
    public void reloadUser() {

        if(sharedPreferences.getString("username","").equals("")) {
            username = getString(R.string.login_public_common);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("account","public").commit();
        }else if(sharedPreferences.getString("account","").equals("public")){
            username = getString(R.string.login_public_common);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("account","public").commit();
        }else {
            username = sharedPreferences.getString("username", "");
        }


        account.setText(username);
    }

    @Override
    public void reloadList() {
        adapter=new MainAdapter(noteDataList,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void deleteSuccess(){
        Toast.makeText(MainActivity.this,R.string.editor_delete_success,Toast.LENGTH_SHORT).show();
    }



    //广播接收器
    public class ReloadUserReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            reloadUser();
            getList();
        }
    }

    public class ReloadListReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            reloadList();
        }
    }

    public class DialogPClickReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent dialogPClick=new Intent(MainActivity.this, SearchActivity.class);
            String category=state.getString("dialogP_Click","");
            dialogPClick.putExtra("category",category);
            startActivity(dialogPClick);
        }
    }

    public class DialogPLongClockReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            return;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(reloadUserReceiver);
        unregisterReceiver(reloadListReceiver);
        unregisterReceiver(dialogPClickReceiver);
    }
}
package com.pslproject.testexample.View.UI;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pslproject.testexample.Adapter.MainAdapter;
import com.pslproject.testexample.Model.entity.NoteData;
import com.pslproject.testexample.Presenter.SearchPresenter;
import com.pslproject.testexample.R;
import com.pslproject.testexample.Utils.BaseActivity;
import com.pslproject.testexample.Utils.RecyclerItemClickListener;
import com.pslproject.testexample.View.SearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity implements SearchView {

    private TextView title;
    private ImageButton back;
    private RecyclerView recyclerView;

    private SearchPresenter presenter;
    private MainAdapter adapter;

    private String searchInfo;
    private String categoryInfo;
    private List<NoteData> noteDataList;

    ReloadListReceiver reloadListReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        Intent intent=getIntent();
        searchInfo=intent.getStringExtra("info");
        categoryInfo=intent.getStringExtra("category");
        noteDataList=new ArrayList<>();



        init();
    }

    @Override
    public void init() {

        title=findViewById(R.id.tv_search_result);
        back=findViewById(R.id.bt_search_back);
        recyclerView=findViewById(R.id.rv_search_main);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        presenter=new SearchPresenter();
        presenter.attachView(this);

        //设置广播接收器
        reloadListReceiver=new ReloadListReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.pslproject.textexample.reloadList");
        registerReceiver(reloadListReceiver,intentFilter);

        //获取数据
        getList();


        //监听返回按钮
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(SearchActivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            public void onItemClick(View view, int position) {
                adapter.openItem(position);
            }

            public void onLongItemClick(View view, int position) {
                DialogP dialogP=new DialogP(SearchActivity.this,1);
                dialogP.setTitle(getString(R.string.editor_delete_common))
                        .setMessage(getString(R.string.editor_delete_confirm))
                        .setOnButtonClickListener(new DialogP.onButtonClickListener() {
                            @Override
                            public void onPositiveButtonClick() {
                                presenter.deleteNote(adapter.getId(position));
                                dialogP.dismiss();
                            }

                            @Override
                            public void onNegativeButtonClick() {
                                dialogP.dismiss();
                            }
                        }).show();
            }
        }));
    }


    private void getList() {
        if(!TextUtils.isEmpty(searchInfo)){
            noteDataList=presenter.getNoteByInfo(searchInfo);
            adapter=new MainAdapter(noteDataList,this);
            recyclerView.setAdapter(adapter);
        }
        if(!TextUtils.isEmpty(categoryInfo)){
            noteDataList=presenter.getNoteByCategory(categoryInfo);
            adapter=new MainAdapter(noteDataList,this);
            recyclerView.setAdapter(adapter);
        }else return;
    }










    //接口实现
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
    public void deleteSuccess() {
        getList();
        Toast.makeText(this, R.string.editor_delete_success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void searchFinish() {

    }

    public class ReloadListReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            getList();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(reloadListReceiver);
    }
}
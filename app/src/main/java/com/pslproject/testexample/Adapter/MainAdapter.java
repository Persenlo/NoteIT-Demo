package com.pslproject.testexample.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.pslproject.testexample.Model.entity.NoteData;
import com.pslproject.testexample.R;
import com.pslproject.testexample.View.UI.DialogP;
import com.pslproject.testexample.View.UI.EditorActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面Note的适配器
 * Last update:2021.9.23
 * author:Persenlo
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private SharedPreferences sharedPreferences;
    private SharedPreferences state;

    private ViewHolder viewHolder;

    private List<NoteData> noteDataList=new ArrayList<>();
    private Context context;

    boolean editMode = false;

    public MainAdapter(List<NoteData> noteDataList, Context context){
        this.noteDataList=noteDataList;
        this.context=context;
        sharedPreferences= context.getSharedPreferences("tempNote.xml",0);
        state=context.getSharedPreferences("state.xml",0);
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewHolder=new MainAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.info_card,parent,false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoteData data=noteDataList.get(position);
        if(data.isLock.equals("lock")){
            if(editMode){
                holder.title.setText(data.getTitle());
                holder.date.setText(data.getDate());
                holder.info.setText(context.getString(R.string.editor_lock_locked));
                holder.cardView.setCardBackgroundColor(context.getColor(R.color.gray));
            }else {
                holder.title.setText(data.getTitle());
                holder.date.setText(data.getDate());
                holder.info.setText(context.getString(R.string.editor_lock_locked));
                holder.cardView.setCardBackgroundColor(context.getColor(R.color.white));
            }
        } else {
            if(editMode){
                holder.title.setText(data.getTitle());
                holder.date.setText(data.getDate());
                holder.info.setText(data.getInfo());
                holder.cardView.setCardBackgroundColor(context.getColor(R.color.gray));
            }else {
                holder.title.setText(data.getTitle());
                holder.date.setText(data.getDate());
                holder.info.setText(data.getInfo());
                holder.cardView.setCardBackgroundColor(context.getColor(R.color.white));
            }
        }
    }


    @Override
    public int getItemCount() {
        return noteDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView date;
        private TextView info;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.tv_infocard_title);
            date=itemView.findViewById(R.id.tv_infocard_date);
            info=itemView.findViewById(R.id.tv_infocard_info);
            cardView=itemView.findViewById(R.id.cv_main_info);
        }
    }

    //编辑模式高亮
    public void highLight(int position){
        editMode=true;
        notifyItemChanged(position);
    }

    //取消高亮
    public void closeHL(int position){
        editMode=false;
        notifyItemChanged(position);
    }
    //关闭编辑模式
    public void closeEditMode(){
        editMode=false;
    }
    //获取id
    public int getId(int position){
        NoteData data=noteDataList.get(position);
        return data.getItemId();
    }


    //打开对应的笔记
    public void openItem(int position){
        NoteData data=noteDataList.get(position);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        SharedPreferences.Editor stateEditor=state.edit();
        editor.putString("title",data.getTitle());
        editor.putString("date",data.getDate());
        editor.putString("info",data.getInfo());
        editor.putString("author",data.getAuthor());
        editor.putString("category",data.getCategory());
        editor.putString("password",data.getLockPassword());
        editor.putString("lock",data.getLock());
        editor.putInt("id",data.getItemId());
        editor.commit();

        //加密
        if(sharedPreferences.getString("lock","").equals("lock")){
            DialogP dialogP=new DialogP(context,2);
            dialogP.setTitle(context.getString(R.string.editor_lock_unlock))
                    .setHint(context.getString(R.string.editor_lock_input))
                    .setOnButtonClickListener(new DialogP.onButtonClickListener() {
                        @Override
                        public void onPositiveButtonClick() {

                            if(dialogP.getText().equals(sharedPreferences.getString("password",""))){

                                stateEditor.putString("editorState","edit");
                                stateEditor.commit();

                                Intent intent=new Intent(context, EditorActivity.class);
                                context.startActivity(intent);
                                dialogP.dismiss();
                            }//密码正确
                            else{
                                Toast.makeText(context, R.string.login_login_password_error, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onNegativeButtonClick() {
                            dialogP.dismiss();
                        }
                    }).show();
        }//未加密
        else{

            stateEditor.putString("editorState","edit");
            stateEditor.commit();

            Intent intent=new Intent(context, EditorActivity.class);
            context.startActivity(intent);
        }
    }
}

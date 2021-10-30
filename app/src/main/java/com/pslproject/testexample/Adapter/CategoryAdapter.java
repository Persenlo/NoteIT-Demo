package com.pslproject.testexample.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pslproject.testexample.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    Context context;
    List<String> categoryList=new ArrayList<>();

    ViewHolder viewHolder;

    public CategoryAdapter(Context context,List<String> categoryList){
        this.context=context;
        this.categoryList=categoryList;
    }

    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewHolder=new CategoryAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.info_common,parent,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        holder.category.setText(categoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView category;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category=itemView.findViewById(R.id.tv_info_main);
        }
    }

    public String getCategory(int position){
        return categoryList.get(position);
    }
}

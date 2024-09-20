package com.example.fetchtakehome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private final Context context;
    private final List<Model> listItems;

    public CustomAdapter(Context context, List<Model> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.single_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.textName.setText(listItems.get(position).getName());
        holder.textId.setText(String.valueOf(listItems.get(position).getId()));
        holder.textListId.setText(String.valueOf(listItems.get(position).getListId()));
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}

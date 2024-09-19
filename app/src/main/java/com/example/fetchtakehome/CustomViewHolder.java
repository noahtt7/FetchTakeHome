package com.example.fetchtakehome;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    public TextView textId, textListId, textName;
    private List<Model> listItems;

    public CustomViewHolder(@NonNull View itemView, TextView textId, TextView textListId, TextView textName, List<Model> listItems) {
        super(itemView);
        this.textId = textId;
        this.textListId = textListId;
        this.textName = textName;
        this.listItems = listItems;
    }

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);
        textId = itemView.findViewById(R.id.id);
        textListId = itemView.findViewById(R.id.listId);
        textName = itemView.findViewById(R.id.name);
    }
}

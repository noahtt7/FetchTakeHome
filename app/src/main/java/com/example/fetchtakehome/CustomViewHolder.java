package com.example.fetchtakehome;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * ViewHolder class that references the views used for displaying the data
 * for each item in the RecyclerView.
 */
public class CustomViewHolder extends RecyclerView.ViewHolder {
    public TextView textListId, textName, textId;
    private List<Model> listItems;

    /**
     * Constructor for CustomViewHolder.
     * @param itemView The view representing the individual item layout.
     * @param textListId TextView for displaying list ID.
     * @param textName TextView for displaying the name.
     * @param textId TextView for displaying the ID.
     * @param listItems List of Model objects for each item.
     */
    public CustomViewHolder(@NonNull View itemView, TextView textListId, TextView textName, TextView textId, List<Model> listItems) {
        super(itemView);
        this.textListId = textListId;
        this.textName = textName;
        this.textId = textId;
        this.listItems = listItems;
    }

    /**
     * Constructor for CustomViewHolder, accepting only a View
     * @param itemView The view representing the individual item layout.
     */
    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);
        textListId = itemView.findViewById(R.id.listId);
        textName = itemView.findViewById(R.id.name);
        textId = itemView.findViewById(R.id.id);
    }
}

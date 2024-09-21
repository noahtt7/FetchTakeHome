package com.example.fetchtakehome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter class for creating and updating data for each individual item
 * in the list.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private final Context context;
    private final List<Model> listItems;

    /**
     * Constructor for CustomAdapter
     * @param context Context in which the adapter is used.
     * @param listItems List of Models for each item to be displayed in the RecycleView.
     */
    public CustomAdapter(Context context, List<Model> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    /**
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return New CustomViewHolder that holds a View for each item.
     */
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.single_item, parent, false));
    }

    /**
     * Displays the item data at their specified positions in the list.
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.textListId.setText(String.valueOf(listItems.get(position).getListId()));
        holder.textName.setText(listItems.get(position).getName());
        holder.textId.setText(String.valueOf(listItems.get(position).getId()));
    }

    /**
     * Returns the total number of items in the list.
     * @return The number of items in the list.
     */
    @Override
    public int getItemCount() {
        return listItems.size();
    }
}

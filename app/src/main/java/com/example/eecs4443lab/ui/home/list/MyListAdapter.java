package com.example.eecs4443lab.ui.home.list;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eecs4443lab.R;
import com.example.eecs4443lab.data.list.Book;
import com.example.eecs4443lab.data.list.ListItem;

import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.Lab02VeiwHolder> {

    private final List<ListItem> itemList;
    private static final int TYPE_BOOK = 0;
    private static final int TYPE_MOVIE = 1;

    public MyListAdapter(List<ListItem> itemList) {
            this.itemList = itemList;
    }
    public int getItemType (int position) {
        if (itemList.get(position) instanceof Book) {
            return TYPE_BOOK;
        } else return TYPE_MOVIE;
    }

    @NonNull
    @Override
    public Lab02VeiwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new Lab02VeiwHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Lab02VeiwHolder holder, int position) {
        ListItem item = itemList.get(position);

        if (item == null) return;

        holder.text.setText(item.getTitle());
        if (getItemType(position) == 0) {
            holder.icon.setImageResource(R.drawable.baseline_book_24);
        } else if (getItemType(position) == 1) {
            holder.icon.setImageResource(R.drawable.baseline_movie_24);
        }
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    static class Lab02VeiwHolder extends RecyclerView.ViewHolder {
        final TextView text;
        final ImageView icon;
        Lab02VeiwHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.item_title);
            icon = itemView.findViewById(R.id.item_icon);
        }
    }
}


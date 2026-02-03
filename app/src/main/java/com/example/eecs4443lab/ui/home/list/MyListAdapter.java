package com.example.eecs4443lab.ui.home.list;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eecs4443lab.R;

import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.Lab02VeiwHolder> {

    private final List<String> items;

    public MyListAdapter(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Lab02VeiwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new Lab02VeiwHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Lab02VeiwHolder holder, int position) {
        holder.text.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class Lab02VeiwHolder extends RecyclerView.ViewHolder {
        final TextView text;

        Lab02VeiwHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.list_item);
        }
    }
}


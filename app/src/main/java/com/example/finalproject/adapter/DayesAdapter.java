package com.example.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Classes.Presence;
import com.example.finalproject.R;

import java.util.ArrayList;

public class DayesAdapter extends RecyclerView.Adapter<DayesAdapter.MyHolder>{

    Context context;
    ArrayList<Presence> data;
    onItemClickListener listener;

    public DayesAdapter(Context context, ArrayList<Presence> data, onItemClickListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dayes, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Presence presence = data.get(position);
        holder.name.setText(String.valueOf(presence.getDay()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(String.valueOf(presence.getDay()), holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView name;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_dayes);
        }
    }

    public interface onItemClickListener {
        void onItemClick(String selectedDay, int position);
    }

}

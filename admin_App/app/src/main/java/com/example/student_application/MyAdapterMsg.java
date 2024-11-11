package com.example.student_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapterMsg extends RecyclerView.Adapter<MyAdapterMsg.MyViewHolder> {
    Context context;
    ArrayList<Event> list;

    public MyAdapterMsg(Context context, ArrayList<Event> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.main_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Event event=list.get(position);
        holder.content.setText(event.getContent());
        holder.time.setText(event.getTime());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView content,time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            content=itemView.findViewById(R.id.evCont);
            time=itemView.findViewById(R.id.evTime);
        }
    }
}

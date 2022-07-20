package com.example.rtes;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView stopname,time;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        stopname=itemView.findViewById(R.id.stopnameTV);
        time=itemView.findViewById(R.id.timeTV);
    }
}

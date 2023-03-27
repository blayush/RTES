package com.example.rtes;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder2 extends RecyclerView.ViewHolder {
    TextView stopName,eta,schTime;
    ImageView marker;

    public MyViewHolder2(@NonNull View itemView) {
        super(itemView);
        stopName=itemView.findViewById(R.id.stopnametextView);
        eta=itemView.findViewById(R.id.etaTV);
        schTime=itemView.findViewById(R.id.schTV);
        marker=itemView.findViewById(R.id.markerimageView);

    }
}

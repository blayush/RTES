package com.example.rtes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    CardView busScheduleBtn,spotBusBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        busScheduleBtn=findViewById(R.id.busScheduleCV);
        spotBusBtn=findViewById(R.id.spotBusBtn);

        spotBusBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SpotActivity.class);
            startActivity(intent);
        });

        busScheduleBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
            startActivity(intent);
        });
    }
}
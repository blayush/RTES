package com.example.rtes;

import static com.example.rtes.R.color.colorPurple;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public class SpotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot);
        getWindow().setStatusBarColor(getResources().getColor(colorPurple));
    }
}
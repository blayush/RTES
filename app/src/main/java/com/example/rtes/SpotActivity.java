package com.example.rtes;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rtes.databinding.ActivitySpotBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SpotActivity extends AppCompatActivity {

    private DatabaseReference rootRef;
    private Boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        com.example.rtes.databinding.ActivitySpotBinding binding = ActivitySpotBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        rootRef= FirebaseDatabase.getInstance().getReference();
        flag = false;

        binding.spotBtn.setOnClickListener(view1 -> {

            if(!flag) {
                binding.popLayout.animate().translationYBy(-1520).rotationBy(-360).setDuration(500);
                flag=true;
            }


        });

    }
}
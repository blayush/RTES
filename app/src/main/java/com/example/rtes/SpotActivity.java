package com.example.rtes;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rtes.Fragments.StopListFragment;
import com.example.rtes.Models.StopsModel;
import com.example.rtes.databinding.ActivitySpotBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SpotActivity extends AppCompatActivity {

    private DatabaseReference rootRef;
    private Boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        com.example.rtes.databinding.ActivitySpotBinding binding = ActivitySpotBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getWindow().setStatusBarColor(getColor(R.color.colorPurple));
        rootRef= FirebaseDatabase.getInstance().getReference();
        flag = false;


        binding.spotBtn.setOnClickListener(view1 -> {

            ArrayList<StopsModel> stopsList= new ArrayList<>();
            String busNum=binding.busNumEditText.getText().toString();
            DatabaseReference ref=rootRef.child("schedule").child(busNum);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        binding.busTypeTV.setText(snapshot.child("bustype").getValue().toString());
                        binding.viaTV.setText(String.format("Via - %s", snapshot.child("via").getValue().toString()));
                        binding.fromTV.setText(snapshot.child("from").getValue().toString());
                        binding.toTV.setText(snapshot.child("to").getValue().toString());

                        int i = 0, idx = -1;
                        for (DataSnapshot dataSnapshot : snapshot.child("stops").getChildren()) {
                            StopsModel stopsModel = dataSnapshot.getValue(StopsModel.class);
                            if (stopsModel.getStatus() == 1) idx = i;
                            stopsList.add(stopsModel);
                            i++;
                        }
                        binding.currentStopTV.setText(stopsList.get(idx).getStopname());
                        if (idx != i - 1)
                            binding.nextStopTV.setText(stopsList.get(idx + 1).getStopname());
                        else binding.nextStopTV.setText("Last Stop");

                        String currentTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());
                        long mins = getDiff(stopsList.get(idx).getTime(), currentTime);
                        if (mins == 0) binding.delayTV.setText("On Time");
                        else {
                            if (mins >= 60L) {

                                long hrs = mins / 60;
                                mins = mins % 60;
                                binding.delayTV.setText(String.format("Delayed by %s hours %s minutes", hrs, mins));
                            } else {
                                binding.delayTV.setText(String.format("Delayed by %s minutes", mins));
//                            Toast.makeText(getApplicationContext(),Long.toString(mins),Toast.LENGTH_SHORT).show();
                            }
                        }
                        binding.schTimeTV.setText(stopsList.get(idx).getTime());
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            if(!flag) {
                binding.popLayout.animate().translationYBy(-1520).rotationBy(-360).setDuration(500);
                flag=true;
            }


        });

        binding.showListBtn.setOnClickListener(view1 -> {
            StopListFragment stopListFragment = new StopListFragment();
            stopListFragment.show(getSupportFragmentManager(),"stopListFragment");
        });

    }

    public long getDiff(String t1, String t2){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("h:m a");

        LocalTime time1 = LocalTime.parse(t1.toLowerCase(), format);
        String currentTime = new SimpleDateFormat("h:m a", Locale.getDefault()).format(new Date());
        LocalTime time2 = LocalTime.parse(currentTime, format);

        Duration dur = Duration.between(time1, time2);

        return dur.toMinutes();

    }
}
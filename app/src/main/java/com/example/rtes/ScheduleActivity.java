package com.example.rtes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rtes.Models.StopsModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ScheduleActivity extends AppCompatActivity {

    private RecyclerView scheduleRecyclerView;
    private FirebaseRecyclerOptions<StopsModel> options;
    private FirebaseRecyclerAdapter<StopsModel,MyViewHolder> adapter;
    private TextView busType,from,to;
    private DatabaseReference rootDatabaseRef;
    private TextInputEditText busNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Button checkButton = findViewById(R.id.checkButton);
        busType=findViewById(R.id.bustypeTextView);
        from=findViewById(R.id.fromtextView);
        to=findViewById(R.id.totextView);
        busNum=findViewById(R.id.busNumEditText);
        scheduleRecyclerView=findViewById(R.id.scheduleRecyclerView);
        scheduleRecyclerView.setHasFixedSize(true);
        scheduleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        rootDatabaseRef= FirebaseDatabase.getInstance().getReference().child("schedule");

        checkButton.setOnClickListener(view -> {

            options=new FirebaseRecyclerOptions.Builder<StopsModel>().setQuery(rootDatabaseRef.child(busNum.getText().toString()).child("stops"),StopsModel.class).build();
            adapter=new FirebaseRecyclerAdapter<StopsModel, MyViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull StopsModel model) {
                    holder.stopname.setText(model.getStopname());
                    holder.time.setText(model.getTime());
                    Log.d("stopname", model.getStopname());
                }

                @NonNull
                @Override
                public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                    View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.single_stop_view,parent,false);

                    return new MyViewHolder(view);
                }
            };

            adapter.startListening();
            scheduleRecyclerView.setAdapter(adapter);

            rootDatabaseRef.child(busNum.getText().toString()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        String fromRcvd=snapshot.child("from").getValue().toString();
                        String toRcvd=snapshot.child("to").getValue().toString();
                        String busTypeRcvd=snapshot.child("bustype").getValue().toString();
                        busType.setText(busTypeRcvd);
                        from.setText(fromRcvd);
                        to.setText(toRcvd);

                        //firebase recyclerView implementation from here
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Wrong Bus Number",Toast.LENGTH_SHORT).show();
                        busType.setText("Nill");
                        from.setText("Nill");
                        to.setText("Nill");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });




    }
}
package com.example.rtes.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rtes.Models.StopsModel;
import com.example.rtes.MyViewHolder2;
import com.example.rtes.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StopListFragment extends DialogFragment {
    private TextView busTypeTV;
    private FirebaseRecyclerOptions<StopsModel> options2;
    private FirebaseRecyclerAdapter<StopsModel, MyViewHolder2> adapter2;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stop_list, container, false);
        String busNum = getArguments().getString("busNum");
        Toast.makeText(getActivity().getApplicationContext(), busNum, Toast.LENGTH_SHORT).show();
        TextView busNumTV = view.findViewById(R.id.busnumtv);
        busNumTV.setText(busNum);
        busTypeTV=view.findViewById(R.id.bustypeTV);

        RecyclerView spotRecyclerView = view.findViewById(R.id.stopRecyclerView);
        spotRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DatabaseReference rootDatabaseRef = FirebaseDatabase.getInstance().getReference().child("schedule");
        rootDatabaseRef.child(busNum).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                busTypeTV.setText(snapshot.child("bustype").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        options2 = new FirebaseRecyclerOptions.Builder<StopsModel>().setQuery(rootDatabaseRef.child(busNum).child("stops"), StopsModel.class).build();
        adapter2 = new FirebaseRecyclerAdapter<StopsModel, MyViewHolder2>(options2) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder2 holder2, int position, @NonNull StopsModel model) {
                TextView stopName,eta,schTime;
                ImageView marker;
                stopName=holder2.itemView.findViewById(R.id.stopnametextView);
                eta=holder2.itemView.findViewById(R.id.etaTV);
                schTime=holder2.itemView.findViewById(R.id.schTV);
                marker=holder2.itemView.findViewById(R.id.markerimageView);
                stopName.setText(model.getStopname());
                eta.setText(model.getTime());
                schTime.setText(model.getTime());
                if(model.getStatus()==0)marker.setImageDrawable(getResources().getDrawable(R.drawable.unvisited));
                else if(model.getStatus()==1) {
                    marker.setImageDrawable(getResources().getDrawable(R.drawable.current_2));
                   LinearLayout upperBarLayout = holder2.itemView.findViewById(R.id.upperBarLayout);
                   upperBarLayout.setBackgroundColor(getResources().getColor(R.color.black));
                   stopName.setTextColor(getResources().getColor(R.color.white));
                }
                else marker.setImageDrawable(getResources().getDrawable(R.drawable.arrived));

            }

            @NonNull
            @Override
            public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_stop_status_view, parent, false);

                return new MyViewHolder2(view2);
            }
        };
        spotRecyclerView.setAdapter(adapter2);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter2.startListening();
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        adapter2.stopListening();
//    }
}
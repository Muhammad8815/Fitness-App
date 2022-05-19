package workout.fitnessapp.menu_fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import workout.fitnessapp.Exercise_thumbnail;
import workout.fitnessapp.R;
import workout.fitnessapp.all_recycler_adapters.Exercises_Adapter;
import workout.fitnessapp.data_classes.PicNameUrl;
import workout.fitnessapp.workout.Aerobic_RecyclerView;

public class Workout_Fragment extends Fragment {


    RecyclerView Aerobic_RecyclerView,RV_exercises;
    public static DatabaseReference Aerobic_db;
//    public static DatabaseReference Aerobic_db;
//    public static DatabaseReference Aerobic_db;
//    public static DatabaseReference Aerobic_db;

    ArrayList<PicNameUrl> aerobic_data2;
    ArrayList<String> exe;
    public static PicNameUrl aerobic_data;
    FloatingActionButton add_GIFs;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_workout_, container, false);

        Aerobic_db= FirebaseDatabase.getInstance().getReference("FitnessUser").child("Workout").child("Aerobic_data");

        add_GIFs=view.findViewById(R.id.add_GIFs);
        context=getActivity().getApplicationContext();
        aerobic_data2=new ArrayList<>();
        exe=new ArrayList<>();
        Aerobic_RecyclerView=view.findViewById(R.id.Aerobic_RecyclerView);
        Aerobic_RecyclerView.setHasFixedSize(true);

        RV_exercises=view.findViewById(R.id.RV_exercises);
        RV_exercises.setHasFixedSize(true);

        Aerobic_RecyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        RV_exercises.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

        exe.add("Six-Pack");
        exe.add("Pushups");
        exe.add("Planks");
        exe.add("Squats");
        exe.add("Lunges");
        exe.add("Yoga");
        exe.add("Bridge");
        exe.add("Chair-squat");
        exe.add("Knee-pushup");
        exe.add("Stationary-lunge");
        exe.add("Bird-Dog");
        exe.add("Forearm-plank");
        exe.add("Bicycle-crunch");


        add_GIFs.setOnClickListener(view1 ->
        {
            Intent intent=new Intent(getActivity().getApplicationContext(), Exercise_thumbnail.class);
            startActivity(intent);
        });

        AeroRV();
        Exe_RV();

        return view;
    }

    private void Exe_RV() {
        Exercises_Adapter exercises_adapter=new Exercises_Adapter(exe,getActivity().getApplicationContext());
        RV_exercises.setAdapter(exercises_adapter);
    }

    private void AeroRV() {

        Aerobic_db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                aerobic_data2.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    aerobic_data=snapshot1.getValue(PicNameUrl.class);
                    aerobic_data.setPpid(snapshot1.getKey());
                    aerobic_data2.add(aerobic_data);
                }
                Aerobic_RecyclerView aerobic_recyclerView=new Aerobic_RecyclerView(context,aerobic_data2);
                Aerobic_RecyclerView.setAdapter(aerobic_recyclerView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
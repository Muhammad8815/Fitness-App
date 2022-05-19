package workout.fitnessapp.workout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import workout.fitnessapp.R;
import workout.fitnessapp.menu_fragments.Workout_Fragment;

public class Workout_start extends AppCompatActivity {

    TextView end_gif_name,thatmuchtimes;
    ImageView endurance_exer_gif;
    Button ddone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_start);
        end_gif_name=findViewById(R.id.end_gif_name);
        thatmuchtimes=findViewById(R.id.thatmuchtimes);
        endurance_exer_gif=findViewById(R.id.endurance_exer_gif);
        ddone=findViewById(R.id.ddone);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        String idz= getIntent().getStringExtra("gifid");

        Workout_Fragment.Aerobic_db.child(idz).child(idz).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful())
                {
                    if (task.getResult().exists())
                    {
                        DataSnapshot snapshot=task.getResult();

                        String gifName=String.valueOf(snapshot.child("gifName").getValue());
                        end_gif_name.setText(gifName);

                        String giftime=String.valueOf(snapshot.child("gifTime").getValue());
                        thatmuchtimes.setText(giftime);

                        String gifpic=String.valueOf(snapshot.child("gifUrl").getValue());

                        Glide.with(Workout_start.this)
                                .load(gifpic)
                                .centerCrop()
                                .placeholder(R.drawable.fitness_logo)
                                .error(R.drawable.error_pic)
                                .into(endurance_exer_gif);
                    }
                }
            }
        }).addOnFailureListener(fai->
        {
            Toast.makeText(this, "Loading Failed", Toast.LENGTH_SHORT).show();
        });

        ddone.setOnClickListener(view ->
        {
            finish();
        });
    }
}
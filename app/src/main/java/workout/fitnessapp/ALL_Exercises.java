package workout.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import workout.fitnessapp.menu_fragments.Workout_Fragment;
import workout.fitnessapp.workout.Workout_start;

public class ALL_Exercises extends AppCompatActivity {

    TextView end_gif_name;
    ImageView endurance_exer_gif;
    Button ddone;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_exercises);

        end_gif_name=findViewById(R.id.exe_name);
        endurance_exer_gif=findViewById(R.id.exe_gif);
        ddone=findViewById(R.id.ddone);
        reference= FirebaseDatabase.getInstance().getReference("FitnessUser").child("Workout").child("All_Exercises");

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        String exe1= getIntent().getStringExtra("exe");
        end_gif_name.setText(exe1);

        reference.child(exe1).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful())
                {
                    if (task.getResult().exists())
                    {
                        DataSnapshot snapshot=task.getResult();

                        String gifpic=String.valueOf(snapshot.child(exe1).getValue());

                        Glide.with(ALL_Exercises.this)
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
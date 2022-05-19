package workout.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;

import workout.fitnessapp.data_classes.Gif_Play_Time;

public class Gif_Name_Time extends AppCompatActivity {

    EditText add_gifName,add_gifTime,add_gifUrl;
    Button add_gifSAVE;
    Gif_Play_Time gif_play_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_name_time);
        add_gifName=findViewById(R.id.add_gifName);
        add_gifTime=findViewById(R.id.add_gifTime);
        add_gifUrl=findViewById(R.id.add_gifUrl);
        add_gifSAVE=findViewById(R.id.add_gifSAVE);

        add_gifSAVE.setOnClickListener(view ->
        {
            String gifName=add_gifName.getText().toString();
            String gifUrl=add_gifUrl.getText().toString();
            String gifTime=add_gifTime.getText().toString();
            String s= getIntent().getStringExtra("num");

            gif_play_time=new Gif_Play_Time(gifName,gifUrl,gifTime);

            if (add_gifName!=null||add_gifTime!=null||add_gifUrl!=null)
            {
                Exercise_thumbnail.reference.child(s).child(s).setValue(gif_play_time).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Gif_Name_Time.this, "Successfully Inserted", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(fail->
                {
                    Toast.makeText(this, "Failed"+fail.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}
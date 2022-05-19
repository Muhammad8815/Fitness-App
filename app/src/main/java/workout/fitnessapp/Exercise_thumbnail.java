package workout.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import workout.fitnessapp.data_classes.PicNameUrl;

public class Exercise_thumbnail extends AppCompatActivity {
    EditText add_gif_tnail_url,add_gif_tnail_name;
    Button add_gif_tnail_next_btn;
    public static int i =12;
    PicNameUrl picNameUrl;
    public static DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_thumbnail);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        add_gif_tnail_url=findViewById(R.id.add_gif_tnail_url);
        add_gif_tnail_name=findViewById(R.id.add_gif_tnail_name);
        add_gif_tnail_next_btn=findViewById(R.id.add_gif_tnail_next_btn);
        reference=FirebaseDatabase.getInstance().getReference("FitnessUser").child("Workout").child("Aerobic_data");


        add_gif_tnail_next_btn.setOnClickListener(view ->
        {
            String picurl=add_gif_tnail_url.getText().toString();
            String exename=add_gif_tnail_name.getText().toString();

            if (add_gif_tnail_url==null|| add_gif_tnail_name == null)
            {
                Toast.makeText(this, "empty name url!!!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                picNameUrl=new PicNameUrl(exename,picurl);
                String s=Integer.toString(i);
                Toast.makeText(this, picNameUrl.getPicName(), Toast.LENGTH_SHORT).show();
                reference.child(s).setValue(picNameUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        i++;
                        Toast.makeText(Exercise_thumbnail.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Exercise_thumbnail.this,Gif_Name_Time.class);
                        intent.putExtra("num",s);
                        startActivity(intent);
                        add_gif_tnail_name.setText(null);
                        add_gif_tnail_url.setText(null);
                    }
                }).addOnFailureListener(fail->
                {
                    Toast.makeText(this, "failed"+fail.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });

    }
}
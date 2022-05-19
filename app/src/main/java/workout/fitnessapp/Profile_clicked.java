package workout.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import workout.fitnessapp.menu_fragments.Fragment_Profile;
import workout.fitnessapp.menu_fragments.Menu_Home_page;

public class Profile_clicked extends AppCompatActivity {
    ImageView imgclicked,more;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_clicked);

        imgclicked=findViewById(R.id.imgclicked);
        more=findViewById(R.id.more);
        reference= FirebaseDatabase.getInstance().getReference("FitnessUser").child("Accounts");

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        String pid= getIntent().getStringExtra("pf");
        String uid= getIntent().getStringExtra("dp");


        Glide.with(Profile_clicked.this)
                .load(pid)
                .placeholder(R.drawable.fitness_logo)
                .error(R.drawable.error_pic)
                .into(imgclicked);

        if (Searched_Profile.comfrom=="profile")
        {
            more.setVisibility(View.VISIBLE);
            more.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                LayoutInflater inflater2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view2 = inflater2.inflate(R.layout.delete_d, null);
                builder.setView(view2);
                builder.setCancelable(true);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                Button deleteIcon = view2.findViewById(R.id.deleteIcon);

                deleteIcon.setOnClickListener(view1 ->
                {
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                    builder2.setTitle("Delete!!!").setMessage("Are you really want to delete your profile?").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            reference.child(uid).child("Profile").setValue("null").addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(Profile_clicked.this, "Profile Deleted", Toast.LENGTH_SHORT).show();
                                    alertDialog.cancel();
                                    finish();
//                                startActivity(getIntent());
                                }
                            }).addOnFailureListener(fai ->
                            {
                                Toast.makeText(getApplicationContext(), fai.getMessage(), Toast.LENGTH_SHORT).show();
                                alertDialog.cancel();
                            });
                        }
                    }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            alertDialog.cancel();
                        }
                    });

                    AlertDialog alertDialog2 = builder2.create();
                    alertDialog2.show();
                });

            });
        }
        else
        {
            more.setVisibility(View.GONE);
        }
    }
}
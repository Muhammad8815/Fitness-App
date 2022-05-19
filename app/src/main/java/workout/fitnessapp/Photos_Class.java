package workout.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Objects;

import workout.fitnessapp.menu_fragments.Fragment_Profile;
import workout.fitnessapp.menu_fragments.Menu_Home_page;

public class Photos_Class extends AppCompatActivity {
    ImageView imgclicked,more;
    DatabaseReference reference;
    String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photos_class);


        imgclicked=findViewById(R.id.imgclicked);
        more=findViewById(R.id.more);
        reference= FirebaseDatabase.getInstance().getReference("FitnessUser").child("Accounts");

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        String uid= getIntent().getStringExtra("pppid");
        String pushuid= getIntent().getStringExtra("pushid");
        pid= getIntent().getStringExtra("imglink");


        Glide.with(Photos_Class.this)
                .load(pid)
                .placeholder(R.drawable.fitness_logo)
                .error(R.drawable.error_pic)
                .into(imgclicked);

        more.setOnClickListener(view ->
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            LayoutInflater inflater2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view3 = inflater2.inflate(R.layout.set_edit_picture, null);
            builder.setView(view3);
            builder.setCancelable(true);

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            Button setProfile = view3.findViewById(R.id.setprofile);
            Button setCover = view3.findViewById(R.id.setcover);
            Button Share = view3.findViewById(R.id.picshare);
            Button deleted = view3.findViewById(R.id.ddeleted);

            if (Searched_Profile.comfrom=="profile")
            {
                deleted.setVisibility(View.VISIBLE);

                deleted.setOnClickListener(view1 ->
                {
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                    builder2.setTitle("Delete!!!").setMessage("Are you really want to delete your profile?").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            reference.child(uid).child("Images").child(pushuid).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(Photos_Class.this, "Picture Seccessfully Deleted", Toast.LENGTH_SHORT).show();
                                    alertDialog.cancel();
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Photos_Class.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
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
            }
            else
            {
                deleted.setVisibility(View.GONE);
            }

            if (Searched_Profile.comfrom=="profile"){
                setCover.setVisibility(View.VISIBLE);
                setProfile.setVisibility(View.VISIBLE);
                setProfile.setOnClickListener(view1 -> {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("Profile", pid);

                    reference.child(uid).updateChildren(map).addOnSuccessListener(succ ->
                    {
                        Snackbar.make(view, "Profile successfully uploaded", Snackbar.LENGTH_LONG).show();
                        alertDialog.cancel();

                    }).addOnFailureListener(fail ->
                    {
                        Snackbar.make(view, fail.getMessage(), Snackbar.LENGTH_LONG).show();
                    });

                });
            }
            else
            {
                setCover.setVisibility(View.GONE);
                setProfile.setVisibility(View.GONE);
            }

            setCover.setOnClickListener(view1 -> {

                HashMap<String, Object> map = new HashMap<>();
                map.put("cover", pid);
                reference.child(uid).updateChildren(map).addOnSuccessListener(succ ->
                {
                    Snackbar.make(view,"Cover successfully set",Snackbar.LENGTH_LONG).show();
                    alertDialog.cancel();

                }).addOnFailureListener(fail ->
                {
                    Snackbar.make(view,fail.getMessage(),Snackbar.LENGTH_LONG).show();
                });

            });


            Share.setOnClickListener(view1 -> {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Hello there!!\n    I'm sahre Fitness App Post: \n "+ pid);
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.whatsapp")));
                }
            });
        });
    }

    private void UploadProfile2(Uri uri2) {


    }

}
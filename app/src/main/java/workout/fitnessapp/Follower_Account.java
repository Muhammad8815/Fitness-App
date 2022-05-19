package workout.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import workout.fitnessapp.all_recycler_adapters.Followers_Adapter;
import workout.fitnessapp.data_classes.Followers;
import workout.fitnessapp.data_classes.Following;
import workout.fitnessapp.data_classes.Trainner_Followers;
import workout.fitnessapp.data_classes.Trainners;
import workout.fitnessapp.menu_fragments.Menu_Home_page;
import workout.fitnessapp.profile_data.Posts_Only;

public class Follower_Account extends AppCompatActivity {

    DatabaseReference reference;
    ScrollView scrollView2;
    ImageView coverpic;
    TextView textView10,ProfileName,Useris;
    CircleImageView profile_image,profilePhotos;
    Button startfollow,Trainers;
    TextView Following2,Followers2,profileAddress,profileDOB,profileEmail;
    TextView TrainFollowers;

    Button postbtn1,AboutSearch;
    ProgressBar progressBar;
    SwipeRefreshLayout refreshserch;
    public static String Personid;
    int foll=0;
    int trai=0;
    ArrayList<Trainners> isTrainnerList;
    ArrayList<Following> isFollowList;
    ArrayList<Trainner_Followers> isTrainnerfollowList;
    ArrayList<Followers> isFollowerList;


    public static String Pprofile,PName,Pcover;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.follower_account);

        reference= FirebaseDatabase.getInstance().getReference("FitnessUser").child("Accounts");

        isFollowList=new ArrayList<>();
        isTrainnerList=new ArrayList<>();
        isTrainnerfollowList=new ArrayList<>();
        isFollowerList=new ArrayList<>();

        scrollView2=findViewById(R.id.scrollView2);
        coverpic=findViewById(R.id.coverpic);
        TrainFollowers=findViewById(R.id.TrainFollowers);
        ProfileName=findViewById(R.id.ProfileName);
        Useris=findViewById(R.id.UserIs);
        profile_image=findViewById(R.id.profile_image);
        profilePhotos=findViewById(R.id.profilePhotos);
        startfollow=findViewById(R.id.startfollow);
        Trainers=findViewById(R.id.Trainers);
        Following2=findViewById(R.id.Following);
        Followers2=findViewById(R.id.Followers);
        profileAddress=findViewById(R.id.profileAddress);
        profileDOB=findViewById(R.id.profileDOB);
        profileEmail=findViewById(R.id.profileEmail);
        postbtn1=findViewById(R.id.postbtn1);
        AboutSearch=findViewById(R.id.AboutSearch);
        progressBar=findViewById(R.id.profilePBar);
        refreshserch=findViewById(R.id.pulltorefresh3);
        textView10=findViewById(R.id.textView10);
        Personid=Search_Adapter.search_uid;

        scrollView2.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        Searched_Profile.comfrom="Followers";


        FollowingCheck();
        TrainnersCheck();

        TrainnersFollowCheck();

        SearchFollowingCheck();
        SearchFollowersCheck();


        ProfileInformation2(Followers_Adapter.search_uid2);

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pulltorefresh3);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                Refreshprofile();
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefresh.setRefreshing(false);
                    }
                },1000);
            }
        });

        textView10.setVisibility(View.GONE);

        Objects.requireNonNull(getSupportActionBar()).setTitle(Followers_Adapter.Search_name2);
        profile_image.setOnClickListener(view1 -> {

            if (Pprofile.equals("null"))
            {
                Toast.makeText(this, "Empty Profile", Toast.LENGTH_SHORT).show();
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view2 = inflater2.inflate(R.layout.gallery, null);
                builder.setView(view2);
                builder.setCancelable(true);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                ImageView gallery = view2.findViewById(R.id.pic3);
                ImageView viewimg = view2.findViewById(R.id.pic4);
                TextView UploaadImgtext=view2.findViewById(R.id.UploaadImgtext);
                UploaadImgtext.setVisibility(View.GONE);
                gallery.setVisibility(View.GONE);
//                TextView Vtext=view2.findViewById(R.id.VImgtext);
                viewimg.setOnClickListener(view5->{

                    Intent intent=new Intent(this, Profile_clicked.class);
                    intent.putExtra("pf",Pprofile);
                    intent.putExtra("dp",Followers_Adapter.search_uid2);
                    startActivity(intent);
                    alertDialog.cancel();
                });
            }

        });

        coverpic.setOnClickListener(view1 ->
        {
            if (Pcover.equals("null"))
            {
                Toast.makeText(this, "Empty Cover", Toast.LENGTH_SHORT).show();
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view2 = inflater2.inflate(R.layout.gallery, null);
                builder.setView(view2);
                builder.setCancelable(true);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                ImageView gallery = view2.findViewById(R.id.pic3);
                ImageView viewimg = view2.findViewById(R.id.pic4);
                TextView UploaadImgtext=view2.findViewById(R.id.UploaadImgtext);
                UploaadImgtext.setVisibility(View.GONE);
                gallery.setVisibility(View.GONE);
//                TextView Vtext=view2.findViewById(R.id.VImgtext);
                viewimg.setOnClickListener(view5->
                {
                    Intent intent=new Intent(this, Cover_Clicked.class);
                    intent.putExtra("cf",Pcover);
                    intent.putExtra("dc",Followers_Adapter.search_uid2);
                    startActivity(intent);
                    alertDialog.cancel();
                });
            }
        });


        profilePhotos.setOnClickListener(view1 ->
        {

            Intent intent=new Intent(this, Photos_Profile.class);
            intent.putExtra("pname",ProfileName.getText().toString());

            startActivity(intent);
        });

        Trainers.setOnClickListener(view ->
        {
            if (trai!=1)
            {
                Trainners trainners=new Trainners(Followers_Adapter.search_uid2);
                reference.child(Menu_Home_page.CurrentLoginUserId).child("trainner").push().setValue(trainners).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Trainers.setText("Trainner added");
                        Trainers.setBackgroundResource(R.drawable.btn_color_white);
                        trai=1;
                        Toast.makeText(Follower_Account.this, "Trainner added", Toast.LENGTH_SHORT).show();
                        Trainner_Followers trainner_followers=new Trainner_Followers(Menu_Home_page.CurrentLoginUserId);
                        reference.child(Followers_Adapter.search_uid2).child("trainnerFollowers").push().setValue(trainner_followers);
                    }
                }).addOnFailureListener(fail ->
                {
                    Toast.makeText(this, fail.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
            else
            {
                reference.child(Menu_Home_page.CurrentLoginUserId).child("trainner").child(isTrainnerList.get(0).getTrainnerKey()).child("trainner").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Trainers.setText("Add Trainner");
                        Trainers.setBackgroundResource(R.drawable.login_btn_press_state);
                        trai=0;
                        Toast.makeText(Follower_Account.this, "Trainner removed", Toast.LENGTH_SHORT).show();
                        reference.child(Followers_Adapter.search_uid2).child("trainnerFollowers").child(isTrainnerfollowList.get(0).getTrainnerFollowersKey()).child("trainnerFollowers").removeValue();
                    }
                }).addOnFailureListener(fail->
                {
                    Toast.makeText(this, fail.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }

        });

        startfollow.setOnClickListener(view ->
        {
            if (foll!=1)
            {
                Following following=new Following(Followers_Adapter.search_uid2);
                reference.child(Menu_Home_page.CurrentLoginUserId).child("following").push().setValue(following).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        startfollow.setText("Following");
                        startfollow.setBackgroundResource(R.drawable.btn_color_white);
                        foll=1;
                        Toast.makeText(Follower_Account.this, "Following", Toast.LENGTH_SHORT).show();

                        ////////////////////////////////////////////////////////////////
                        Followers followers=new Followers(Menu_Home_page.CurrentLoginUserId);
                        reference.child(Followers_Adapter.search_uid2).child("followers").push().setValue(followers);
                    }
                }).addOnFailureListener(fail ->
                {
                    Toast.makeText(this, fail.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
            else
            {
                reference.child(Menu_Home_page.CurrentLoginUserId).child("following").child(isFollowList.get(0).getFollowingKey()).child("following").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        startfollow.setText("Follow");
                        startfollow.setBackgroundResource(R.drawable.login_btn_press_state);
                        foll=0;
//                        setFollowBackground();
                        Toast.makeText(Follower_Account.this, "Unfollowed", Toast.LENGTH_SHORT).show();
                        //////////////////
                        reference.child(Followers_Adapter.search_uid2).child("followers").child(isFollowerList.get(0).getFollowerKey()).child("followers").removeValue();
                    }
                }).addOnFailureListener(fail->
                {
                    Toast.makeText(this, fail.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });

        postbtn1.setOnClickListener(view ->
        {
            Intent intent=new Intent(this, Posts_Only.class);
            startActivity(intent);
        });
    }

    private void FollowingCheck()
    {
        reference.child(Menu_Home_page.CurrentLoginUserId).child("following").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    Following following=snapshot1.getValue(Following.class);

                    assert following != null;
                    if(Followers_Adapter.search_uid2.equals(following.getFollowing()))
                    {
                        isFollowList.clear();
                        following.setFollowingKey(snapshot1.getKey());
                        foll=1;
                        isFollowList.add(following);
                        startfollow.setBackgroundResource(R.drawable.btn_color_white);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void SearchFollowersCheck()
    {
        reference.child(Followers_Adapter.search_uid2).child("followers").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                int c=0;
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    c++;
                    Followers followers=snapshot1.getValue(Followers.class);

                    assert followers != null;
                    if(Menu_Home_page.CurrentLoginUserId.equals(followers.getFollowers()))
                    {
                        startfollow.setText("following");
                        isFollowerList.clear();
                        followers.setFollowerKey(snapshot1.getKey());
                        isFollowerList.add(followers);
                    }
                }
                Followers2.setText("Followers : "+c);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void SearchFollowingCheck()
    {
        reference.child(Followers_Adapter.search_uid2).child("following").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                int c=0;
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    c++;
                    Following following=snapshot1.getValue(Following.class);
                }
                Following2.setText("Following : "+c);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void TrainnersCheck()
    {
        reference.child(Menu_Home_page.CurrentLoginUserId).child("trainner").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    Trainners trainners =snapshot1.getValue(Trainners.class);

                    assert trainners != null;
                    trainners.setTrainnerKey(snapshot1.getKey());

                    if(Followers_Adapter.search_uid2.equals(trainners.getTrainner()))
                    {
                        Trainers.setText("Trainner added");
                        isTrainnerList.clear();
                        trai=1;
                        isTrainnerList.add(trainners);
                        Trainers.setBackgroundResource(R.drawable.btn_color_white);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void TrainnersFollowCheck()
    {
        reference.child(Followers_Adapter.search_uid2).child("trainnerFollowers").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                int trainfollowerCount=0;
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    trainfollowerCount++;
                    Trainner_Followers trainner_followers =snapshot1.getValue(Trainner_Followers.class);

                    assert trainner_followers != null;
                    if(Menu_Home_page.CurrentLoginUserId.equals(trainner_followers.getTrainnerFollowers()))
                    {
                        isTrainnerfollowList.clear();
                        trainner_followers.setTrainnerFollowersKey(snapshot1.getKey());
                        isTrainnerfollowList.add(trainner_followers);
                    }
                }
                TrainFollowers.setText("pupil : "+trainfollowerCount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void Refreshprofile()
    {
        ProfileInformation2(Followers_Adapter.search_uid2);
        SearchFollowingCheck();
        SearchFollowersCheck();
        FollowingCheck();
        TrainnersCheck();
        TrainnersFollowCheck();
    }


    public void ProfileInformation2(String pid) {
        reference.child(pid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful())
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    scrollView2.setVisibility(View.VISIBLE);

                    if (task.getResult().exists())
                    {
                        DataSnapshot snapshot=task.getResult();

                        String Profilepic=String.valueOf(snapshot.child("Profile").getValue());
                        if (!Profilepic.equals("null"))
                        {
                            Glide.with(Follower_Account.this)
                                    .load(Profilepic)
                                    .centerCrop()
                                    .placeholder(R.drawable.blank_profile)
                                    .error(R.drawable.blank_profile)
                                    .into(profile_image);
                            Pprofile=Profilepic;
//                            textView10.setVisibility(View.GONE);
                        }
                        else
                        {
                            Pprofile="null";
                        }

                        String FullName=String.valueOf(snapshot.child("fullName").getValue());
                        ProfileName.setText(FullName);
                        PName = FullName;

                        String Coverpic=String.valueOf(snapshot.child("cover").getValue());
                        if (!Coverpic.equals("null"))
                        {
                            Glide.with(Follower_Account.this)
                                    .load(Coverpic)
                                    .centerCrop()
                                    .placeholder(R.drawable.fitness_theme)
                                    .error(R.drawable.fitness_theme)
                                    .into(coverpic);
                            Pcover=Coverpic;

                            /////////////////////////////////////////
                            textView10.setVisibility(View.GONE);
                        }
                        else
                        {
                            Pcover="null";
                        }

                        String Email=String.valueOf(snapshot.child("email").getValue());
                        if (!Email.equals("null")) {
                            profileEmail.setVisibility(View.VISIBLE);
                            profileEmail.setText(Email);
                        }
                        else
                        {
                            profileEmail.setVisibility(View.INVISIBLE);
                        }


                        String UserStatus=String.valueOf(snapshot.child("userIs").getValue());
                        if (!UserStatus.equals("null"))
                        {
                            Useris.setVisibility(View.VISIBLE);
                            Useris.setText(UserStatus);
                            if (UserStatus.equals("Trainer"))
                            {
                                Trainers.setVisibility(View.VISIBLE);
                                TrainFollowers.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                Trainers.setVisibility(View.GONE);
                                TrainFollowers.setVisibility(View.GONE);
                            }
                        }
                        else
                        {
                            Useris.setVisibility(View.INVISIBLE);
                        }
                        String Address=String.valueOf(snapshot.child("address").getValue());
                        if (!Address.equals("null"))
                        {
                            profileAddress.setVisibility(View.VISIBLE);
                            profileAddress.setText(Address);
                        }
                        else
                        {
                            profileAddress.setVisibility(View.GONE);
                        }
                        String DOBDay=String.valueOf(snapshot.child("dobDay").getValue());
                        String DOBMonth=String.valueOf(snapshot.child("dobMonth").getValue());
                        String DOBYear=String.valueOf(snapshot.child("dobYear").getValue());
                        if (!DOBDay.equals("null"))
                        {
                            profileDOB.setVisibility(View.VISIBLE);
                            profileDOB.setText(DOBDay + "/" + DOBMonth + "/" + DOBYear);
                        }
                        else
                        {
                            profileDOB.setVisibility(View.INVISIBLE);
                        }
                    }
                }
                else
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(Follower_Account.this, "Loading Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(fail->
        {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(Follower_Account.this, "Loading Failed.... "+fail.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}
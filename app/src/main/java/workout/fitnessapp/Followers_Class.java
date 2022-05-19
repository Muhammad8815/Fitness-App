package workout.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import workout.fitnessapp.all_recycler_adapters.Followers_Adapter;
import workout.fitnessapp.data_classes.F_users;
import workout.fitnessapp.data_classes.Followers;
import workout.fitnessapp.data_classes.Following;
import workout.fitnessapp.data_classes.Trainner_Followers;
import workout.fitnessapp.data_classes.Trainners;
import workout.fitnessapp.data_classes.User_Information;
import workout.fitnessapp.menu_fragments.Fragment_Profile;
import workout.fitnessapp.menu_fragments.Menu_Home_page;

public class Followers_Class extends AppCompatActivity {
    RecyclerView follower_rv;
//    ArrayList<F_users> list;
    TextView f_count;
    ArrayList<User_Information> list;
    Followers_Adapter followers_adapter;
    DatabaseReference reference;
    int i;
    User_Information user_information;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.followers_class);


        list = new ArrayList<>();
        follower_rv = findViewById(R.id.follower_rv);
        follower_rv.setHasFixedSize(true);
        f_count=findViewById(R.id.f_count);
        reference = FirebaseDatabase.getInstance().getReference("FitnessUser").child("Accounts");

        i=0;

        if (Fragment_Profile.FriendList.equals("Followers"))
        {
            getSupportActionBar().setTitle("Followers");

            for (Followers followers : Fragment_Profile.followersArrayList) {
                reference.child(Fragment_Profile.followersArrayList.get(i).getFollowers()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                DataSnapshot snapshot = task.getResult();
                                String Profilepic = String.valueOf(snapshot.child("Profile").getValue());
                                String Name = String.valueOf(snapshot.child("fullName").getValue());
                                String uid = String.valueOf(snapshot.child("pid").getValue());
                                user_information = new User_Information();
                                user_information.setFullName(Name);
                                user_information.setProfile(Profilepic);
                                user_information.setPid(uid);
                                list.add(user_information);
                            }
                        }
                    }
                });
                i++;
            }
            followers_adapter = new Followers_Adapter(list);
            follower_rv.setAdapter(followers_adapter);
            if (i == 0) {
                f_count.setText("Your Followers will show here");
                f_count.setVisibility(View.VISIBLE);
            } else {
                f_count.setVisibility(View.GONE);
            }

        }
        else if (Fragment_Profile.FriendList.equals("Following"))
        {
            getSupportActionBar().setTitle("Followings");
            for (Following following : Fragment_Profile.followingArrayList) {
                reference.child(Fragment_Profile.followingArrayList.get(i).getFollowing()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                DataSnapshot snapshot = task.getResult();
                                String Profilepic = String.valueOf(snapshot.child("Profile").getValue());
                                String Name = String.valueOf(snapshot.child("fullName").getValue());
                                String uid = String.valueOf(snapshot.child("pid").getValue());
                                user_information = new User_Information();
                                user_information.setFullName(Name);
                                user_information.setProfile(Profilepic);
                                user_information.setPid(uid);
                                list.add(user_information);
                            }
                        }
                    }
                });
                i++;
            }
            followers_adapter = new Followers_Adapter(list);
            follower_rv.setAdapter(followers_adapter);
            if (i == 0) {
                f_count.setVisibility(View.VISIBLE);
                f_count.setText("Your Following will show here");
            } else {
                f_count.setVisibility(View.GONE);
            }
        }
        else if (Fragment_Profile.FriendList.equals("Trainer"))
        {
            getSupportActionBar().setTitle("Trainers");
            for (Trainners trainners : Fragment_Profile.trainnersArrayList) {
                reference.child(Fragment_Profile.trainnersArrayList.get(i).getTrainner()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                DataSnapshot snapshot = task.getResult();
                                String Profilepic = String.valueOf(snapshot.child("Profile").getValue());
                                String Name = String.valueOf(snapshot.child("fullName").getValue());
                                String uid = String.valueOf(snapshot.child("pid").getValue());
                                user_information = new User_Information();
                                user_information.setFullName(Name);
                                user_information.setProfile(Profilepic);
                                user_information.setPid(uid);
                                list.add(user_information);
                            }
                        }
                    }
                });
                i++;
            }
            followers_adapter = new Followers_Adapter(list);
            follower_rv.setAdapter(followers_adapter);
            if (i == 0) {
                f_count.setVisibility(View.VISIBLE);
                f_count.setText("Your Trainers will show here");
            } else {
                f_count.setVisibility(View.GONE);
            }
        }
        else if (Fragment_Profile.FriendList.equals("Pupil"))
        {
            getSupportActionBar().setTitle("Pupils");
            for (Trainner_Followers trainner_followers : Fragment_Profile.trainner_followersArrayList) {
                reference.child(Fragment_Profile.trainner_followersArrayList.get(i).getTrainnerFollowers()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                DataSnapshot snapshot = task.getResult();
                                String Profilepic = String.valueOf(snapshot.child("Profile").getValue());
                                String Name = String.valueOf(snapshot.child("fullName").getValue());
                                String uid = String.valueOf(snapshot.child("pid").getValue());
                                user_information = new User_Information();
                                user_information.setFullName(Name);
                                user_information.setProfile(Profilepic);
                                user_information.setPid(uid);
                                list.add(user_information);
                            }
                        }
                    }
                });
                i++;
            }
            followers_adapter = new Followers_Adapter(list);
            follower_rv.setAdapter(followers_adapter);
            if (i == 0) {
                f_count.setVisibility(View.VISIBLE);
                f_count.setText("Your Pupils will show here");
            } else {
                f_count.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Searched_Profile.comfrom="profile";
    }
}
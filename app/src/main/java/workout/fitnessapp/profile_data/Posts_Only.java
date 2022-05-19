package workout.fitnessapp.profile_data;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import workout.fitnessapp.Follower_Account;
import workout.fitnessapp.R;
import workout.fitnessapp.Searched_Profile;
import workout.fitnessapp.all_recycler_adapters.Followers_Adapter;
import workout.fitnessapp.all_recycler_adapters.RecyclerView_Profile;
import workout.fitnessapp.all_recycler_adapters.RecyclerView_TimeLine;
import workout.fitnessapp.data_classes.New_Posts;
import workout.fitnessapp.menu_fragments.Fragment_Profile;

public class Posts_Only extends AppCompatActivity {

    ArrayList<New_Posts> arrayList;
    RecyclerView postsRecyclerView;
    ProgressBar PbarOnPosts;
    RecyclerView_Profile recyclerView_profile;
    TextView post_o;
    public static DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_only);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        post_o=findViewById(R.id.post_o);

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pulltorefresh4);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
//                arrayList.clear();
//                RecyclerAdapter();
                pullToRefresh.setRefreshing(false);
            }
        });

        arrayList=new ArrayList<>();

        PbarOnPosts=findViewById(R.id.PbarOnPosts);
        PbarOnPosts.setVisibility(View.VISIBLE);

        db= FirebaseDatabase.getInstance().getReference("FitnessUser").child("Accounts");

        postsRecyclerView=findViewById(R.id.postsRecyclerView);
        postsRecyclerView.setHasFixedSize(true);

        RecyclerAdapter();

    }

    private void RecyclerAdapter() {
        if (Searched_Profile.comfrom.equals("profile"))
        {
            db = db.child(Fragment_Profile.personID).child("posts");
            Fragment_Profile.dbreference.child(Fragment_Profile.personID).child("posts").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    arrayList.clear();
                    int c=0;
                    for (DataSnapshot snapshot1:snapshot.getChildren())
                    {
                        c++;
                        New_Posts new_posts = snapshot1.getValue(New_Posts.class);
                        assert new_posts != null;
                        new_posts.setUuid(snapshot1.getKey());
                        arrayList.add(new_posts);
                    }
                    if (c==0)
                    {
                        post_o.setVisibility(View.VISIBLE);
                        PbarOnPosts.setVisibility(View.INVISIBLE);
                    }
                    else {
                        post_o.setVisibility(View.GONE);
                        PbarOnPosts.setVisibility(View.INVISIBLE);
                        RecyclerView_TimeLine.setFrom("tt");
                        recyclerView_profile = new RecyclerView_Profile(Posts_Only.this, arrayList);
                        recyclerView_profile.setPprofile(Fragment_Profile.Pprofile);
                        recyclerView_profile.setPName(Fragment_Profile.PName);
                        recyclerView_profile.setPUid(Fragment_Profile.personID);
                        postsRecyclerView.setAdapter(recyclerView_profile);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else if (Searched_Profile.comfrom.equals("Followers"))
        {
            db=db.child(Followers_Adapter.search_uid2).child("posts");

            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    arrayList.clear();
                    int c=0;
                    for (DataSnapshot snapshot1:snapshot.getChildren())
                    {
                        c++;
                        New_Posts new_posts = snapshot1.getValue(New_Posts.class);
                        new_posts.setUuid(snapshot1.getKey());
                        arrayList.add(new_posts);
                    }
                    if (c==0)
                    {
                        post_o.setVisibility(View.VISIBLE);
                        PbarOnPosts.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        post_o.setVisibility(View.INVISIBLE);
                        PbarOnPosts.setVisibility(View.INVISIBLE);
                        RecyclerView_TimeLine.setFrom("tt");
                        recyclerView_profile = new RecyclerView_Profile(Posts_Only.this, arrayList);
                        recyclerView_profile.setPprofile(Follower_Account.Pprofile);
                        recyclerView_profile.setPName(Follower_Account.PName);
                        recyclerView_profile.setPUid(Follower_Account.Personid);
                        postsRecyclerView.setAdapter(recyclerView_profile);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else
        {
            db=db.child(Searched_Profile.Personid).child("posts");

            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    arrayList.clear();
                    int c=0;
                    for (DataSnapshot snapshot1:snapshot.getChildren())
                    {
                        c++;
                        New_Posts new_posts = snapshot1.getValue(New_Posts.class);
                        new_posts.setUuid(snapshot1.getKey());
                        arrayList.add(new_posts);
                    }
                    if (c==0)
                    {
                        post_o.setVisibility(View.VISIBLE);
                        PbarOnPosts.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        post_o.setVisibility(View.INVISIBLE);
                        PbarOnPosts.setVisibility(View.INVISIBLE);
                        RecyclerView_TimeLine.setFrom("tt");
                        recyclerView_profile = new RecyclerView_Profile(Posts_Only.this, arrayList);
                        recyclerView_profile.setPprofile(Searched_Profile.Pprofile);
                        recyclerView_profile.setPName(Searched_Profile.PName);
                        recyclerView_profile.setPUid(Searched_Profile.Personid);
                        postsRecyclerView.setAdapter(recyclerView_profile);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

    }

}

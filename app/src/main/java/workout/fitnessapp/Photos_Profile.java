package workout.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import workout.fitnessapp.all_recycler_adapters.Followers_Adapter;
import workout.fitnessapp.menu_fragments.Fragment_Profile;

public class Photos_Profile extends AppCompatActivity {

    RecyclerView grid_RV;
    ArrayList<AllImages> imgs;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photos_profile);

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pulltorefresh3);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                fullImgs();
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefresh.setRefreshing(false);
                    }
                },1000);
            }
        });


        imgs=new ArrayList<>();
//        pid= getIntent().getStringExtra("ppuid");
        String pname= getIntent().getStringExtra("pname");

        reference = FirebaseDatabase.getInstance().getReference().child("FitnessUser").child("Accounts");

        getSupportActionBar().setTitle(pname);

        fullImgs();

        grid_RV = (RecyclerView) findViewById(R.id.grid_RV);
        grid_RV.setHasFixedSize(true);
    }

    private void fullImgs() {
        if (Searched_Profile.comfrom.equals("profile"))
        {
            reference.child(Fragment_Profile.personID).child("Images").addValueEventListener(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    imgs.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        AllImages allImages = snapshot1.getValue(AllImages.class);
                        allImages.setPushId(snapshot1.getKey());
                        imgs.add(allImages);
                    }
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(Photos_Profile.this, 3);
                    grid_RV.setLayoutManager(gridLayoutManager);
                    GridView_Adapter gridView_adapter = new GridView_Adapter(imgs, Photos_Profile.this);
                    gridView_adapter.setPid(Fragment_Profile.personID);
                    grid_RV.setAdapter(gridView_adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else if (Searched_Profile.comfrom.equals("Followers"))
        {
            reference.child(Followers_Adapter.search_uid2).child("Images").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    imgs.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        AllImages allImages = snapshot1.getValue(AllImages.class);
                        allImages.setPushId(snapshot1.getKey());
                        imgs.add(allImages);
                    }
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(Photos_Profile.this, 3);
                    grid_RV.setLayoutManager(gridLayoutManager);
                    GridView_Adapter gridView_adapter = new GridView_Adapter(imgs, Photos_Profile.this);
                    gridView_adapter.setPid(Followers_Adapter.search_uid2);
                    grid_RV.setAdapter(gridView_adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else
        {
            reference.child(Search_Adapter.search_uid).child("Images").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    imgs.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        AllImages allImages = snapshot1.getValue(AllImages.class);
                        allImages.setPushId(snapshot1.getKey());
                        imgs.add(allImages);
                    }
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(Photos_Profile.this, 3);
                    grid_RV.setLayoutManager(gridLayoutManager);
                    GridView_Adapter gridView_adapter = new GridView_Adapter(imgs, Photos_Profile.this);
                    gridView_adapter.setPid(Search_Adapter.search_uid);
                    grid_RV.setAdapter(gridView_adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}
package workout.fitnessapp.menu_fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import workout.fitnessapp.R;
import workout.fitnessapp.Search_Accounts;
import workout.fitnessapp.all_recycler_adapters.RecyclerView_TimeLine;
import workout.fitnessapp.data_classes.Following;
import workout.fitnessapp.data_classes.User_Information;

public class TimeLine_Fragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<User_Information> accounts;
    DatabaseReference dbreference2
//            ,dbreference
            ;
    ImageView search_bar;
    ArrayList<Following> followingArrayList;
    androidx.appcompat.widget.Toolbar myToolbar;
//    User_Information user_information;

    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_time_line_,container,false);

        myToolbar = view.findViewById(R.id.myToolbar);
        setHasOptionsMenu(true);
        search_bar=view.findViewById(R.id.search_bar);
        search_bar.setOnClickListener(view5->
        {
            Intent intent=new Intent(getActivity().getApplicationContext(),Search_Accounts.class);
            startActivity(intent);

        });

        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.pulltorefresh2);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                reFreshData();
                pullToRefresh.setRefreshing(false);
            }
        });

        accounts=new ArrayList<>();
        followingArrayList=new ArrayList<>();
//        dbreference= FirebaseDatabase.getInstance().getReference("FitnessUser").child("Accounts").child(Menu_Home_page.CurrentLoginUserId).child("following");
        dbreference2= FirebaseDatabase.getInstance().getReference("FitnessUser").child("Accounts");
        context=getActivity().getApplicationContext();
        recyclerView =view.findViewById(R.id.Rv_timeline);
        recyclerView.setHasFixedSize(true);
        MyFollowing();

//        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));

//        dbreference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                int c=0;
//                for (DataSnapshot snapshot1:snapshot.getChildren())
//                {
//                    c++;
//                    Following following=snapshot1.getValue(Following.class);
//                    assert following != null;
//                    following.setFollowingKey(snapshot1.getKey());
//                    followingArrayList.add(following);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//        dbreference2.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot snapshot1:snapshot.getChildren()){
//                    User_Information user_information = snapshot1.getValue(User_Information.class);
//                    user_information.setPid(snapshot1.getKey());
//                    accounts.add(user_information);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
/*
        i=0;
        for (Following following : followingArrayList) {
            dbreference2.child(followingArrayList.get(i).getFollowing()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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
                            accounts.add(user_information);
                        }
                    }
                }
            });
            i++;
        }
*/
//        RecyclerView_TimeLine recyclerView_timeLine=new RecyclerView_TimeLine(context,accounts);
//        recyclerView.setAdapter(recyclerView_timeLine);


        return view;
    }

    private void reFreshData()
    {
        dbreference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                accounts.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    User_Information user_information=snapshot1.getValue(User_Information.class);
                    user_information.setPid(snapshot1.getKey());
                    accounts.add(user_information);
                }
                RecyclerView_TimeLine recyclerView_timeLine=new RecyclerView_TimeLine(context,accounts);
                recyclerView.setAdapter(recyclerView_timeLine);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void MyFollowing()
    {

        dbreference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                accounts.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    User_Information user_information=snapshot1.getValue(User_Information.class);
                    user_information.setPid(snapshot1.getKey());
                    accounts.add(user_information);
                }
                RecyclerView_TimeLine recyclerView_timeLine=new RecyclerView_TimeLine(context,accounts);
                recyclerView.setAdapter(recyclerView_timeLine);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





//        dbreference2.child(Menu_Home_page.CurrentLoginUserId).child("following").addValueEventListener(new ValueEventListener() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot)
//            {
//                followingArrayList.clear();
//                for (DataSnapshot snapshot1:snapshot.getChildren())
//                {
//                    Following following=snapshot1.getValue(Following.class);
//                    assert following != null;
//                    following.setFollowingKey(snapshot1.getKey());
//                    followingArrayList.add(following);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }

}
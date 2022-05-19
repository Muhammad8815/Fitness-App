package workout.fitnessapp.all_recycler_adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import workout.fitnessapp.R;
import workout.fitnessapp.data_classes.New_Posts;
import workout.fitnessapp.data_classes.User_Information;
import workout.fitnessapp.menu_fragments.Fragment_Profile;
import workout.fitnessapp.profile_data.Image_Clicked;
import workout.fitnessapp.profile_data.Posts_Only;


public class RecyclerView_TimeLine extends RecyclerView.Adapter<RecyclerView_TimeLine.MyViewHolder>{

    Context context;
    ArrayList<User_Information> accounts;
    public static String from="t";
//    RecyclerView_Profile recyclerView_profile=new RecyclerView_Profile();


    public static void setFrom(String from) {
        RecyclerView_TimeLine.from = from;
    }

    public RecyclerView_TimeLine(Context context, ArrayList<User_Information> accounts) {
        this.context = context;
        this.accounts = accounts;
    }

    @NonNull
    @Override
    public RecyclerView_TimeLine.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.timeline_list,parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView_TimeLine.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ArrayList<New_Posts> PostList=new ArrayList<>();
//        RecyclerView_Profile recyclerView_profile;

        DatabaseReference dbreference= FirebaseDatabase.getInstance().getReference("FitnessUser").child("Accounts");

        String s=accounts.get(position).getPid();
        String name=accounts.get(position).getFullName();
        String Profile=accounts.get(position).getProfile();
//        Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
        holder.recyclerviwTimeline.setHasFixedSize(true);


//        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        dbreference.child(s).child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PostList.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    New_Posts new_posts = snapshot1.getValue(New_Posts.class);
//                    assert new_posts != null;
                    new_posts.setUuid(snapshot1.getKey());
                    PostList.add(new_posts);
                }
                RecyclerView_Profile recyclerView_profile =new RecyclerView_Profile(context,PostList);
                recyclerView_profile.setPName(name);
                recyclerView_profile.setPprofile(Profile);
//                Toast.makeText(context, Profile, Toast.LENGTH_SHORT).show();
                recyclerView_profile.setPUid(s);

                holder.recyclerviwTimeline.setAdapter(recyclerView_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerviwTimeline;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            from="timeL";

            recyclerviwTimeline=itemView.findViewById(R.id.recyclerviwTimeline);
//            recyclerviwTimeline.setHasFixedSize(true);
        }
    }
}

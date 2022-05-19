package workout.fitnessapp.all_recycler_adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import workout.fitnessapp.Follower_Account;
import workout.fitnessapp.R;
import workout.fitnessapp.Searched_Profile;
import workout.fitnessapp.data_classes.User_Information;

public class Followers_Adapter extends RecyclerView.Adapter<Followers_Adapter.myviewholder>
{
    public static String search_uid2;
    public static String Search_name2;


    ArrayList<User_Information> list;

    public Followers_Adapter(ArrayList<User_Information> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Followers_Adapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_profile,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Followers_Adapter.myviewholder holder, int position) {
        holder.name.setText(list.get(position).getFullName());
        Glide.with(holder.img.getContext())
                .load(list.get(position).getProfile())
                .centerCrop()
                .placeholder(R.drawable.fitness_logo)
                .error(R.drawable.blank_profile)
                .into(holder.img);
        holder.linearSearch.setOnClickListener(view ->
        {
            search_uid2=list.get(position).getPid();
            Search_name2=list.get(position).getFullName();

            Intent intent=new Intent(holder.linearSearch.getContext(), Follower_Account.class);
            holder.linearSearch.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView name;
        LinearLayout linearSearch;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            linearSearch=itemView.findViewById(R.id.linearSearch);
            img=(CircleImageView)itemView.findViewById(R.id.profile_image_search);
            name=(TextView)itemView.findViewById(R.id.search_name);
        }
    }
}
package workout.fitnessapp;


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

import de.hdodenhof.circleimageview.CircleImageView;
import workout.fitnessapp.data_classes.User_Information;

public class Search_Adapter extends FirebaseRecyclerAdapter<User_Information,Search_Adapter.myviewholder>
{
    Context context;
    public static String search_uid;
    public static String Search_name;

    public void setContext(Context context) {
        this.context = context;
    }

    public Search_Adapter(@NonNull FirebaseRecyclerOptions<User_Information> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull User_Information model)
    {
        holder.name.setText(model.getFullName());
        Glide.with(holder.img.getContext())
                .load(model.getProfile())
                .centerCrop()
                .placeholder(R.drawable.fitness_logo)
                .error(R.drawable.blank_profile)
                .into(holder.img);
        holder.linearSearch.setOnClickListener(view ->
        {
            search_uid=model.getPid();
            Search_name=model.getFullName();
            Intent intent=new Intent(holder.linearSearch.getContext(),Searched_Profile.class);
            holder.linearSearch.getContext().startActivity(intent);
        });

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_profile,parent,false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        CircleImageView img;
        TextView name;
        LinearLayout linearSearch;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            linearSearch=itemView.findViewById(R.id.linearSearch);
            img=(CircleImageView)itemView.findViewById(R.id.profile_image_search);
            name=(TextView)itemView.findViewById(R.id.search_name);
        }
    }
}

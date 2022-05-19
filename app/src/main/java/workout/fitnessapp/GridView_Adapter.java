package workout.fitnessapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import workout.fitnessapp.all_recycler_adapters.RecyclerView_Profile;

public class GridView_Adapter extends RecyclerView.Adapter<GridView_Adapter.myViewHolder2> {
    ArrayList<AllImages> imgs;
    Context context;
    String pid;

    public void setPid(String pid) {
        this.pid = pid;
    }

    public GridView_Adapter(ArrayList<AllImages> imgs, Context context) {
        this.imgs = imgs;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder2(LayoutInflater.from(context).inflate(R.layout.profile_rowlayout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder2 holder, int position) {

        Glide.with(context)
                .load(imgs.get(position).getImgs())
                .centerCrop()
                .placeholder(R.drawable.fitness_logo)
                .error(R.drawable.error_pic)
                .into(holder.imageView);
        holder.imageView.setOnClickListener(view ->
        {
            Intent intent=new Intent(holder.imageView.getContext(),Photos_Class.class);
            intent.putExtra("pppid",pid);
//            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("pushid",imgs.get(position).getPushId());
            intent.putExtra("imglink",imgs.get(position).getImgs());
            holder.imageView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return imgs.size();
    }

    public class myViewHolder2 extends RecyclerView.ViewHolder {
        ImageView imageView;
        public myViewHolder2(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.gridItem);
        }
    }
}

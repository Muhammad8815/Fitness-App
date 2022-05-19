package workout.fitnessapp.workout;

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

import java.util.ArrayList;

import workout.fitnessapp.R;
import workout.fitnessapp.data_classes.PicNameUrl;

public class Aerobic_RecyclerView extends RecyclerView.Adapter<Aerobic_RecyclerView.MynewHolder> {

    Context context;
    ArrayList<PicNameUrl> arrayList;

    public Aerobic_RecyclerView(Context context, ArrayList<PicNameUrl> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Aerobic_RecyclerView.MynewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Aerobic_RecyclerView.MynewHolder(LayoutInflater.from(context).inflate(R.layout.workout_aerobic_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Aerobic_RecyclerView.MynewHolder holder, int position) {

        holder.aer_workout_name.setText(arrayList.get(position).getPicName());
        Glide.with(context)
                .load(arrayList.get(position).getPicurl())
                .centerCrop()
                .placeholder(R.drawable.fitness_logo)
                .error(R.drawable.error_pic)
                .into(holder.aer_workout_img);
        holder.aer_workout_name.setOnClickListener(view ->
        {
            Intent intent=new Intent(context,Workout_start.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
        holder.aer_workout_img.setOnClickListener(view ->
        {
            Intent intent=new Intent(view.getContext(),Workout_start.class);
            intent.putExtra("gifid",arrayList.get(position).getPpid());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MynewHolder extends RecyclerView.ViewHolder {
        TextView aer_workout_name;
        ImageView aer_workout_img;
        public MynewHolder(@NonNull View itemView) {
            super(itemView);
            aer_workout_name=itemView.findViewById(R.id.aer_workout_name);
            aer_workout_img=itemView.findViewById(R.id.aer_workout_img);
        }
    }
}

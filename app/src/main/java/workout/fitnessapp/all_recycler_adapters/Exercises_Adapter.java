package workout.fitnessapp.all_recycler_adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import workout.fitnessapp.ALL_Exercises;
import workout.fitnessapp.R;

public class Exercises_Adapter extends RecyclerView.Adapter<Exercises_Adapter.myViewHolder> {
    ArrayList<String> exe;
    Context context;

    public Exercises_Adapter(ArrayList<String> exe, Context context) {
        this.exe = exe;
        this.context = context;
    }

    @NonNull
    @Override
    public Exercises_Adapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(context).inflate(R.layout.exercise,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Exercises_Adapter.myViewHolder holder, int position) {
        holder.exer.setText(exe.get(position));

        String s=holder.exer.getText().toString();
        holder.exer.setOnClickListener(view ->
        {
            Intent intent=new Intent(context, ALL_Exercises.class);
            intent.putExtra("exe",s);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            holder.exer.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return exe.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        Button exer;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            exer=itemView.findViewById(R.id.exe);
        }
    }
}

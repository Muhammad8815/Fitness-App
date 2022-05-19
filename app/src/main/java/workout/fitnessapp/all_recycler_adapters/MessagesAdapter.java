package workout.fitnessapp.all_recycler_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import workout.fitnessapp.data_classes.Message_Class;
import workout.fitnessapp.R;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {

    Context context;
    ArrayList<Message_Class> list;
    String myemail;

    public void setMyemail(String myemail) {
        this.myemail = myemail;
    }

    public MessagesAdapter(Context context, ArrayList<Message_Class> list, String myemail) {
        this.context = context;
        this.list = list;
        this.myemail = myemail;
    }

    public MessagesAdapter() {
    }

    @NonNull
    @Override
    public MessagesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_messages,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesAdapter.MyViewHolder holder, int position) {

        if (list.get(position).getEmail().equals(myemail))
        {
            holder.rmsg.setVisibility(View.GONE);
            holder.remail.setVisibility(View.GONE);
            holder.sendermsg.setVisibility(View.VISIBLE);
            holder.senderemail.setVisibility(View.VISIBLE);
            holder.sendermsg.setText(list.get(position).getText());
            holder.senderemail.setText(list.get(position).getEmail());
        }
        else
        {
            holder.sendermsg.setVisibility(View.GONE);
            holder.senderemail.setVisibility(View.GONE);
            holder.rmsg.setVisibility(View.VISIBLE);
            holder.remail.setVisibility(View.VISIBLE);
            holder.rmsg.setText(list.get(position).getText());
            holder.remail.setText(list.get(position).getEmail());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView sendermsg,senderemail,rmsg,remail;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sendermsg=itemView.findViewById(R.id.sendermessage);
            senderemail=itemView.findViewById(R.id.senderemail);
            rmsg=itemView.findViewById(R.id.recievermessage);
            remail=itemView.findViewById(R.id.recieveremail);

        }
    }
}

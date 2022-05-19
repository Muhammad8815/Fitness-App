package workout.fitnessapp.menu_fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import workout.fitnessapp.data_classes.Message_Class;
import workout.fitnessapp.all_recycler_adapters.MessagesAdapter;
import workout.fitnessapp.R;


public class Messages_Fragment extends Fragment {

    RecyclerView LiveMessagesRecyclerView;
    EditText LiveMessage;
    Button livemessagesend;
    Context context;
    String personID,Email;
    FirebaseAuth firebaseAuth;
    ArrayList<Message_Class> arrayList;
    DatabaseReference reference,dbreference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_messages_, container, false);
        context=getActivity().getApplicationContext();
        LiveMessagesRecyclerView=view.findViewById(R.id.LiveMessagesRecyclerView);
        LiveMessage=view.findViewById(R.id.LiveMessage);
        reference=FirebaseDatabase.getInstance().getReference("FitnessUser").child("Live");
        livemessagesend=view.findViewById(R.id.livemessagesend);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        dbreference= FirebaseDatabase.getInstance().getReference("FitnessUser").child("Accounts");

        arrayList=new ArrayList<>();
        firebaseAuth=FirebaseAuth.getInstance();
        LiveMessagesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        //
        linearLayoutManager.setStackFromEnd(true);
        LiveMessagesRecyclerView.setLayoutManager(linearLayoutManager);

        livemessagesend.setOnClickListener(view1 ->
        {
            String messagetext=LiveMessage.getText().toString();
            if(!messagetext.isEmpty())
            {
                LiveMessage.setText(null);
                Message_Class message_class=new Message_Class(messagetext,personID,Email);
                reference.push().setValue(message_class).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(context, "Message sent", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }


    private void MessageAdapter() {
        MessagesAdapter messagesAdapter=new MessagesAdapter();
        messagesAdapter.setMyemail(Email);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    Message_Class message_class = snapshot1.getValue(Message_Class.class);
                    arrayList.add(message_class);
                }
                MessagesAdapter messagesAdapter=new MessagesAdapter(context,arrayList,Email);
                LiveMessagesRecyclerView.setAdapter(messagesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if (firebaseUser!=null)
        {
            String s = firebaseUser.getUid();
            personID=s;
            ProfileInformation(personID);
//            MessageAdapter();
        }
        else
        {
            SharedPreferences sharedPreferences= getActivity().getSharedPreferences("Google",0);
            String s=sharedPreferences.getString("personid","");
            personID=s;
            ProfileInformation(personID);
//            MessageAdapter();
        }

    }
    public void ProfileInformation(String pid) {
        dbreference.child(pid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful())
                {
                    if (task.getResult().exists())
                    {
                        DataSnapshot snapshot=task.getResult();
                        Email=String.valueOf(snapshot.child("email").getValue());
                        MessageAdapter();
                    }
                }
            }
        });
    }

}
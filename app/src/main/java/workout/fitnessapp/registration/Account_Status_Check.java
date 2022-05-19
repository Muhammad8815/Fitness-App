package workout.fitnessapp.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import workout.fitnessapp.menu_fragments.Menu_Home_page;
import workout.fitnessapp.R;

public class Account_Status_Check extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner AccountStatus;
    Button accStatusnext;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_status_check);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        AccountStatus=findViewById(R.id.AccountStatus);
        accStatusnext=findViewById(R.id.AccStatusNext);
        reference= FirebaseDatabase.getInstance().getReference("FitnessUser").child("Accounts");

        ArrayList<String> ArrayStatus=new ArrayList<>();
        ArrayStatus.add("User");
        ArrayStatus.add("Trainer");

        AccountStatus.setOnItemSelectedListener(this);

        ArrayAdapter arrayAdapter1=new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,ArrayStatus);
        AccountStatus.setAdapter(arrayAdapter1);

        accStatusnext.setOnClickListener(view ->
        {
            String status = AccountStatus.getSelectedItem().toString();

            signup_fragment.user_information.setUserIs(status);
            //ArrayList<User_Information> arrayList=new ArrayList<>();

            if (Home.accountIs.equals("google")){
                Home.user_information.setUserIs(status);
                Home.user_information.setProfile("null");
                Home.user_information.setCover("null");
                reference.child(Home.user_information.getPid()).setValue(Home.user_information).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //userKeyId.setKey(Home.user_information.getPid());
                        Toast.makeText(Account_Status_Check.this, "Account successfylly created", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Account_Status_Check.this, Menu_Home_page.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(e ->
                        Toast.makeText(Account_Status_Check.this, "Account Creating fail : " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
            else if (Home.accountIs.equals("signup"))
            {
                signup_fragment.user_information.setUserIs(status);
                Home.user_information.setProfile("null");
                Home.user_information.setCover("null");

                reference.child(signup_fragment.user_information.getPid()).setValue(signup_fragment.user_information).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Account_Status_Check.this, "Account successfylly created", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Account_Status_Check.this, Menu_Home_page.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(e ->
                        Toast.makeText(Account_Status_Check.this, "Account Creating fail : " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
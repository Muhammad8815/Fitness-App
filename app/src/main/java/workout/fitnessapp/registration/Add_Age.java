package workout.fitnessapp.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import workout.fitnessapp.R;

public class Add_Age extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    Spinner day,month,year;
    Button dobnext;

    ArrayList<String> DaysArray=new ArrayList<>();
    ArrayList<String> MonthArray=new ArrayList<>();
    ArrayList<String> YearArray=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_age);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        day=findViewById(R.id.day);
        month=findViewById(R.id.month);
        year=findViewById(R.id.year);
        dobnext=findViewById(R.id.dobnext);

        ArraylistAdd();


        day.setOnItemSelectedListener(this);
        month.setOnItemSelectedListener(this);
        year.setOnItemSelectedListener(this);

        ArrayAdapter arrayAdapter1=new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,DaysArray);
        day.setAdapter(arrayAdapter1);

        ArrayAdapter arrayAdapter2=new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,MonthArray);
        month.setAdapter(arrayAdapter2);

        ArrayAdapter arrayAdapter3=new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,YearArray);
        year.setAdapter(arrayAdapter3);



       // Toast.makeText(this, ageDay+"/"+ageMonth+"/"+ageYear, Toast.LENGTH_SHORT).show();

        dobnext.setOnClickListener(view ->
        {
            String ageDay = day.getSelectedItem().toString();
            String ageMonth = month.getSelectedItem().toString();
            String ageYear = year.getSelectedItem().toString();

            if (Home.accountIs.equals("google")){
                Home.user_information.setDobDay(ageDay);
                Home.user_information.setDobMonth(ageMonth);
                Home.user_information.setDobYear(ageYear);

                Intent intent=new Intent(Add_Age.this, Account_Status_Check.class);
                startActivity(intent);
                finish();
            }
            else if (Home.accountIs.equals("signup"))
            {
                signup_fragment.user_information.setDobDay(ageDay);
                signup_fragment.user_information.setDobMonth(ageMonth);
                signup_fragment.user_information.setDobYear(ageYear);

                Intent intent=new Intent(Add_Age.this,Account_Status_Check.class);
                startActivity(intent);
                finish();
            }

        });

    }

    private void ArraylistAdd() {
        int m;
        for (int i=0;i<=30;i++)
        {
            if (i<9)
            {
                m=i+1;
                DaysArray.add("0" + m);
            }
            else
            {
                DaysArray.add(String.valueOf(i + 1));
            }
        }
        for (int i=0;i<=11;i++)
        {
            if (i<9)
            {
                m=i+1;
                MonthArray.add("0"+m);
            }
            else{
                MonthArray.add(String.valueOf(i + 1));
            }
        }
        int y=1970;
        for (int i=0;i<=40;i++)
        {
            YearArray.add(String.valueOf(y));
            y++;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
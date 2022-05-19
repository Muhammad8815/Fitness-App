package workout.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import workout.fitnessapp.data_classes.User_Information;
import workout.fitnessapp.menu_fragments.Menu_Home_page;
import workout.fitnessapp.menu_fragments.TimeLine_Fragment;

public class Search_Accounts extends AppCompatActivity {

    RecyclerView recyclerView;
    Search_Adapter search_Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_accounts);
        recyclerView=findViewById(R.id.searchaxxountsRecy);
        recyclerView.setHasFixedSize(true);

        FirebaseRecyclerOptions<User_Information> options =
                new FirebaseRecyclerOptions.Builder<User_Information>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("FitnessUser").child("Accounts"), User_Information.class)
                        .build();

        search_Adapter=new Search_Adapter(options);
        search_Adapter.setContext(this);
        recyclerView.setAdapter(search_Adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.searchmenu,menu);

        MenuItem item=menu.findItem(R.id.search);

        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


     void processsearch(String s)
    {
        FirebaseRecyclerOptions<User_Information> options =
                new FirebaseRecyclerOptions.Builder<User_Information>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("FitnessUser").child("Accounts").orderByChild("fullName").startAt(s).endAt(s+"\uf8ff"), User_Information.class)
                        .build();

        search_Adapter=new Search_Adapter(options);
        search_Adapter.startListening();
        recyclerView.setAdapter(search_Adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        search_Adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        search_Adapter.stopListening();
    }

}
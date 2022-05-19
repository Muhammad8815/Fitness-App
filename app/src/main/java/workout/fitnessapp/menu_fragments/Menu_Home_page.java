package workout.fitnessapp.menu_fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import workout.fitnessapp.R;
import workout.fitnessapp.Search_Adapter;
import workout.fitnessapp.data_classes.User_Information;

public class Menu_Home_page extends AppCompatActivity {

    static TabLayout tabLayout;
    Fragment fragment;
    FirebaseAuth firebaseAuth;

    FragmentManager manager;
    int checkPosition;
    public static String CurrentLoginUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_home_page);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

//                myToolbar = findViewById(R.id.my_toolbar);
//                setSupportActionBar(myToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        firebaseAuth=FirebaseAuth.getInstance();
        tabLayout=findViewById(R.id.tablayout_menu);

        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("Work out"));
//        tabLayout.addTab(tabLayout.newTab().setText("Notification"));
        tabLayout.addTab(tabLayout.newTab().setText("Messages"));
        tabLayout.addTab(tabLayout.newTab().setText("Profile"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        manager=getFragmentManager();

        fragment= new TimeLine_Fragment();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.menu_frame,fragment,"timeline");
        transaction
//                .addToBackStack("abc")
                .commit();
        checkPosition=0;

        TabLayout.Tab tab;
        tab=tabLayout.getTabAt(0);
        tabLayout.setSelected(true);

        tab.setIcon(R.drawable.tab_press);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setIcon(R.drawable.tab_press);
                switch (tab.getPosition()){
                    case 0:
                        checkPosition=0;
                        fragment=new TimeLine_Fragment();
                        break;
                    case 1:
                        checkPosition=1;
                        fragment= new Workout_Fragment();
                        break;
//                    case 2:
//                        fragment= new Notification_Fragment();
//                        break;
                    case 2:
                        checkPosition=2;
                        fragment= new Messages_Fragment();
                        break;
                    case 3:
                        checkPosition=3;
                        fragment= new Fragment_Profile();
                        break;
                }
                FragmentTransaction transaction=manager.beginTransaction();
                transaction.replace(R.id.menu_frame,fragment);
                transaction
//                        .addToBackStack("abc")
                        .commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setIcon(null);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        tabLayout.getTabAt(checkPosition).select();

    }


boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (checkPosition==0)
        {
//            finish();
//            System.exit(0);

            Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();
            tabLayout.getTabAt(checkPosition).select();
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);

        }
        else if (checkPosition==1)
        {
            fragment= new TimeLine_Fragment();
            FragmentTransaction transaction=manager.beginTransaction();
            transaction.replace(R.id.menu_frame,fragment,"timeline");
            transaction.commit();
            checkPosition=0;
            tabLayout.getTabAt(checkPosition).select();
        }
        else if (checkPosition==2)
        {
            fragment= new TimeLine_Fragment();
            FragmentTransaction transaction=manager.beginTransaction();
            transaction.replace(R.id.menu_frame,fragment,"timeline");
            transaction.commit();
            checkPosition=0;
            tabLayout.getTabAt(checkPosition).select();
        }
        else if (checkPosition==3)
        {
            fragment= new TimeLine_Fragment();
            FragmentTransaction transaction=manager.beginTransaction();
            transaction.replace(R.id.menu_frame,fragment,"timeline");
            transaction.commit();
            checkPosition=0;
            tabLayout.getTabAt(checkPosition).select();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if (firebaseUser!=null)
        {
            CurrentLoginUserId = firebaseUser.getUid();
        }
        else
        {
            SharedPreferences sharedPreferences= getSharedPreferences("Google",0);
            CurrentLoginUserId = sharedPreferences.getString("personid","");
        }
    }
}
package workout.fitnessapp.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
import workout.fitnessapp.data_classes.User_Information;
import workout.fitnessapp.menu_fragments.Menu_Home_page;
import workout.fitnessapp.R;

public class Home extends AppCompatActivity {

    private CircleImageView google;
    private FrameLayout frameLayout;
    private Fragment fragment=null;
    private ProgressBar progressBar;
    public static String accountIs;
    public static User_Information user_information;

    private GoogleSignInClient mGoogleSignInClient;

    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        google = findViewById(R.id.Google);
        TabLayout tabLayout = findViewById(R.id.tablayout);
        frameLayout = findViewById(R.id.fl);
        progressBar=findViewById(R.id.flProgress);
        user_information=new User_Information();

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        accountIs="null";

        progressBar.setVisibility(View.INVISIBLE);
        reference= FirebaseDatabase.getInstance().getReference("FitnessUser").child("Accounts");

        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Sign Up"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        MovingButtonEditText();

        FragmentManager manager=getFragmentManager();

        login_fragment login_adapter=new login_fragment();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.add(R.id.fl,login_adapter,"login");
        transaction.commit();


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        fragment=new login_fragment();
                        break;
                    case 1:
                        fragment= new signup_fragment();
                        break;
                }
                FragmentTransaction transaction=manager.beginTransaction();
                transaction.replace(R.id.fl,fragment);
                transaction.commit();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //...............................
        google.setOnClickListener(view ->
        {
            progressBar.setVisibility(View.VISIBLE);
            LoginSignUpWithGoogle();
            signIn();
        });

    }


    private void LoginSignUpWithGoogle() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 33);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 33) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

            assert acct != null;
            String personName = acct.getDisplayName();
                    String personEmail = acct.getEmail();
                    String personId = acct.getId();
                    //Uri personPhoto = acct.getPhotoUrl();

                    //check if already registered

            assert personId != null;

            SharedPreferences sharedPreferences=getSharedPreferences("Google",0);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("personid",personId);
            editor.apply();

            reference.child(personId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.exists())
                            {
                                user_information.setEmail(personEmail);
                                user_information.setFullName(personName);
                                user_information.setPid(personId);
                                accountIs="google";
                                progressBar.setVisibility(View.INVISIBLE);
                                Intent intent=new Intent(Home.this, Add_Age.class);
                                startActivity(intent);
                            }
                            else
                            {
                                user_information.setPid(personId);
                                accountIs="google";
                                progressBar.setVisibility(View.INVISIBLE);
                                Intent intent2=new Intent(Home.this, Menu_Home_page.class);
                                startActivity(intent2);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });

            }

         catch (ApiException e) {
             progressBar.setVisibility(View.INVISIBLE);
             Toast.makeText(this, "error"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    private void MovingButtonEditText() {

        google.setTranslationY(300);
        frameLayout.setTranslationX(300);


        float v = 0;
        google.setAlpha(v);
        frameLayout.setAlpha(v);

        google.animate().translationY(0).alpha(1).setDuration(1500).setStartDelay(400).start();
        frameLayout.animate().translationX(0).alpha(1).setDuration(1500).setStartDelay(400).start();
    }
}


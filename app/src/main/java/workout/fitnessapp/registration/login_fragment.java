package workout.fitnessapp.registration;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import workout.fitnessapp.data_classes.User_Information;
import workout.fitnessapp.menu_fragments.Menu_Home_page;
import workout.fitnessapp.R;

public class login_fragment extends Fragment {
    EditText gmail,password;
    TextView forgetpassword;
    Button Signin;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    public static User_Information user_information;

    Context context;
    float v=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_login_fragment,container,false);

        gmail=view.findViewById(R.id.gmail);
        password=view.findViewById(R.id.passwordlogin);
        forgetpassword=view.findViewById(R.id.forget_password);
        Signin=view.findViewById(R.id.login);
        firebaseAuth=FirebaseAuth.getInstance();
        progressBar=view.findViewById(R.id.loginpbar);
        user_information=new User_Information();
        context=getActivity().getApplicationContext();
//        FirebaseUser auth=FirebaseAuth.getInstance().getCurrentUser();
//
//        if (firebaseAuth.getCurrentUser()!=null)
//        {
//           String kid=auth.getUid();
//            user_information.setPid(kid);
//            Intent intent=new Intent(context,Home.class);
//            startActivity(intent);
//        }

        progressBar.setVisibility(View.INVISIBLE);


        user_information.setPid("22");

        gmail.setTranslationX(800);
        password.setTranslationX(800);
        forgetpassword.setTranslationX(800);
        Signin.setTranslationX(800);

        gmail.setAlpha(v);
        password.setAlpha(v);
        forgetpassword.setAlpha(v);
        Signin.setAlpha(v);

        gmail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgetpassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        Signin.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        Signin.setOnClickListener(view1 -> {

            String nemail=gmail.getText().toString().trim();
            String npass=password.getText().toString();
            if (nemail.isEmpty()||npass.isEmpty())
            {
                Toast.makeText(context, "Email or password can't be empty", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Login1(nemail,npass);
            }
        });

        return view;
    }

    private void Login1(String nemail, String npass) {

        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.signInWithEmailAndPassword(nemail, npass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                progressBar.setVisibility(View.INVISIBLE);
                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                if (!firebaseUser.isEmailVerified())
                {
                    Toast.makeText(context, "Please Verify Your email", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context, "Successfully Login", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(context, Menu_Home_page.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(context, "Login Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if (firebaseUser!=null)
        {
            Intent intent=new Intent(context,Menu_Home_page.class);
            startActivity(intent);
            getActivity().finish();
        }
        else
        {
            SharedPreferences sharedPreferences=getActivity().getSharedPreferences("Google",0);
            String s=sharedPreferences.getString("personid","");
            if (s.length()>7)
            {
                Intent intent=new Intent(getActivity().getApplicationContext(),Menu_Home_page.class);
                startActivity(intent);
                getActivity().finish();
            }
        }
    }
}

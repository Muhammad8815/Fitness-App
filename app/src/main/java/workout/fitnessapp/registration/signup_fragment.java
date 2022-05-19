package workout.fitnessapp.registration;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import workout.fitnessapp.data_classes.User_Information;
import workout.fitnessapp.menu_fragments.Menu_Home_page;
import workout.fitnessapp.R;

public class signup_fragment extends Fragment{

    EditText username,email,address,pass,cpass;
    Button signup;
    FirebaseAuth auth;
    ProgressBar progressBar;

    String UserName;
    String Email;
    String Address;
    String Pass;
    String Cpass;
    Context context;
    public static User_Information user_information=new User_Information();

    float v=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_signup_fragment,container,false);

        username=view.findViewById(R.id.username);
        email=view.findViewById(R.id.email);
        address=view.findViewById(R.id.address);
        pass=view.findViewById(R.id.password);
        cpass=view.findViewById(R.id.password2);
        signup=view.findViewById(R.id.signup2);
        auth= FirebaseAuth.getInstance();
        progressBar=view.findViewById(R.id.signuppbar);
        progressBar.setVisibility(View.INVISIBLE);

        context=getActivity().getApplicationContext();

        signup.setOnClickListener(view2->
        {
            boolean b=RegistrationCheck();
            if (b)
            {
                RegisterEmailPass();
            }
        });
        username.setTranslationY(800);
        email.setTranslationY(800);
        address.setTranslationY(800);
        pass.setTranslationY(800);
        cpass.setTranslationY(800);
        signup.setTranslationY(800);

        username.setAlpha(v);
        email.setAlpha(v);
        address.setAlpha(v);
        pass.setAlpha(v);
        cpass.setAlpha(v);
        signup.setAlpha(v);

        username.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        email.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        address.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        cpass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        signup.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();

        return view;
    }

    private void RegisterEmailPass() {
        progressBar.setVisibility(View.VISIBLE);

        auth.createUserWithEmailAndPassword(Email, Cpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    String keyid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                    //String keyid=auth.getUid();

                    user_information.setPid(keyid);
                    user_information.setEmail(Email);
                    user_information.setFullName(UserName);
                    user_information.setAddress(Address);

                    FirebaseUser firebaseUser=auth.getCurrentUser();
                    assert firebaseUser != null;
                    firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Home.accountIs="signup";

                            Toast.makeText(context, "Verification link sent....", Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(context, Add_Age.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(context, "Verification failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(context, "Account Creation Failed!! "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean RegistrationCheck() {

        boolean b;
        UserName=username.getText().toString().trim();
        Email=email.getText().toString().trim();
        Address=address.getText().toString().trim();
        Pass=pass.getText().toString().trim();
        Cpass=cpass.getText().toString().trim();

        if (UserName.isEmpty())
        {
            b=false;
            username.setError("Username is required!");
            username.requestFocus();
            return b;
        }
        if (Email.isEmpty())
        {
            b=false;
            email.setError("Email is required!");
            email.requestFocus();
            return b;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
        {
            b=false;
            email.setError("Invaild Email!!");
            email.requestFocus();
            return b;
        }
        if (Address.isEmpty())
        {
            Address=" ";
        }
        if (Pass.isEmpty())
        {
            b=false;
            pass.setError("Password is required");
            pass.requestFocus();
            return b;
        }
        if (Pass.length()<6)
        {
            b=false;
            pass.setError("Password can't shorter then 6 Characters!!");
            pass.requestFocus();
            return b;
        }
        if (!Cpass.equals(Pass))
        {
            b=false;
            cpass.setError("Password must be same");
            cpass.requestFocus();
            return b;
        }
        return true;
    }


}

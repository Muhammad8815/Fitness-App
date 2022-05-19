package workout.fitnessapp.menu_fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import workout.fitnessapp.AllImages;
import workout.fitnessapp.Cover_Clicked;
import workout.fitnessapp.Followers_Class;
import workout.fitnessapp.Photos_Profile;
import workout.fitnessapp.Profile_clicked;
import workout.fitnessapp.Search_Adapter;
import workout.fitnessapp.Searched_Profile;
import workout.fitnessapp.data_classes.Followers;
import workout.fitnessapp.data_classes.Following;
import workout.fitnessapp.data_classes.New_Posts;
import workout.fitnessapp.data_classes.Trainner_Followers;
import workout.fitnessapp.data_classes.Trainners;
import workout.fitnessapp.data_classes.User_Information;
import workout.fitnessapp.profile_data.Posts_Only;
import workout.fitnessapp.R;
import workout.fitnessapp.registration.Home;

public class Fragment_Profile extends Fragment {

    TextView ProfileName,Useris,profileAddress,profileDOB,profileEmail;
    TextView textView10;
    public static DatabaseReference dbreference;
    StorageReference storageReference;
    FirebaseAuth firebaseAuth;
    ImageView postPhoto;
    ScrollView scrollView2;
    public static String personID;
    Uri imguri;
    Button postbtn,postbtn1;
    EditText profilepost;
    New_Posts new_posts;
    Context context;
    StorageTask storageTask;
    public static String PName;
    public static String Pprofile;
    String Pcover;
    ImageView coverpic;
    Button logout;
    CircleImageView profile_image,profilePhotos;
    Button Followers2,Following2,Pupil,Trainers2;
    public static ArrayList<Followers> followersArrayList;
    public static ArrayList<Following> followingArrayList;
    public static ArrayList<Trainners> trainnersArrayList;
    public static ArrayList<Trainner_Followers> trainner_followersArrayList;
    public static String FriendList;
    ///////////////////////////////////////////////////////////////

    @SuppressLint({"SetTextI18n", "IntentReset"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment__profile, container, false);

        ///////////////////
        setHasOptionsMenu(true);

        followersArrayList=new ArrayList<>();
        followingArrayList=new ArrayList<>();
        trainnersArrayList=new ArrayList<>();
        trainner_followersArrayList=new ArrayList<>();

        profilePhotos=view.findViewById(R.id.profilePhotos);
        coverpic=view.findViewById(R.id.coverpic);
        profile_image=view.findViewById(R.id.profile_image);
        ProfileName=view.findViewById(R.id.ProfileName);
        Useris=view.findViewById(R.id.UserIs);
        profileAddress=view.findViewById(R.id.profileAddress);
        profileDOB=view.findViewById(R.id.profileDOB);
        profileEmail=view.findViewById(R.id.profileEmail);
        firebaseAuth=FirebaseAuth.getInstance();
        scrollView2=view.findViewById(R.id.scrollView2);
        postPhoto=view.findViewById(R.id.postPhoto);
        logout=view.findViewById(R.id.logout);
        context=getActivity().getApplicationContext();
        textView10=view.findViewById(R.id.textView10);
        Searched_Profile.comfrom="profile";
//        arrayList=new ArrayList<>();
        Followers2=view.findViewById(R.id.Followers);
        Following2=view.findViewById(R.id.Following);
        Pupil=view.findViewById(R.id.Pupil);
        Trainers2=view.findViewById(R.id.Trainers);

        storageReference= FirebaseStorage.getInstance().getReference("Posts");
        postbtn=view.findViewById(R.id.postbtn);
        postbtn1=view.findViewById(R.id.postbtn1);

        scrollView2.setVisibility(View.INVISIBLE);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        profilepost=view.findViewById(R.id.profilepost);

        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.pulltorefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {

                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reFreshData();
                        pullToRefresh.setRefreshing(false);
                    }
                },1000);
            }
        });
        dbreference= FirebaseDatabase.getInstance().getReference("FitnessUser").child("Accounts");

//        if (i==0)
//        {
//            getActivity().finish();
//            startActivity(getActivity().getIntent());
//            i++;
//        }

//        if (getSupportActionBar() != null) {
//            getSupportActionBar().hide();
//        }

        MyFollowers();
        MyFollowing();
        MyPupil();
        MyTrainners();

        profile_image.setOnClickListener(view1 -> {

            if (Pprofile.equals("null"))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                LayoutInflater inflater2 = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view2 = inflater2.inflate(R.layout.gallery, null);
                builder.setView(view2);
                builder.setCancelable(true);

                AlertDialog alertDialog = builder.create();
                alertDialog.setTitle("Choose profile from gallery");
                alertDialog.show();

                ImageView gallery = view2.findViewById(R.id.pic3);
                ImageView viewimg = view2.findViewById(R.id.pic4);
                TextView Vtext=view2.findViewById(R.id.VImgtext);
                TextView UploaadImgtext=view2.findViewById(R.id.UploaadImgtext);
                UploaadImgtext.setVisibility(View.GONE);
                viewimg.setVisibility(View.GONE);
                Vtext.setVisibility(View.GONE);

                gallery.setOnClickListener(view3 ->
                {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent, 3);
                    alertDialog.cancel();
                });
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                LayoutInflater inflater2 = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view2 = inflater2.inflate(R.layout.gallery, null);
                builder.setView(view2);
                builder.setCancelable(true);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                ImageView gallery = view2.findViewById(R.id.pic3);
                ImageView viewimg = view2.findViewById(R.id.pic4);
//                TextView Vtext=view2.findViewById(R.id.VImgtext);
                viewimg.setOnClickListener(view5->{
                    String s;
                    FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                    if (firebaseUser!=null)
                    {
                        s = firebaseUser.getUid();
                    }
                    else
                    {
                        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("Google",0);
                        s=sharedPreferences.getString("personid","");
                    }

                    Intent intent=new Intent(context, Profile_clicked.class);
                    intent.putExtra("pf",Pprofile);
                    intent.putExtra("dp",s);
                    startActivity(intent);
                    alertDialog.cancel();
                });

                gallery.setOnClickListener(view3 ->
                {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent, 3);
                    alertDialog.cancel();
                });
            }

        });

        coverpic.setOnClickListener(view1 ->
        {
            if (Pcover.equals("null"))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                LayoutInflater inflater2 = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view2 = inflater2.inflate(R.layout.gallery, null);
                builder.setView(view2);
                builder.setCancelable(true);

                AlertDialog alertDialog = builder.create();
                alertDialog.setTitle("Choose cover from gallery");
                alertDialog.show();

                ImageView gallery = view2.findViewById(R.id.pic3);
                ImageView viewimg = view2.findViewById(R.id.pic4);
                TextView Vtext=view2.findViewById(R.id.VImgtext);
                TextView UploaadImgtext=view2.findViewById(R.id.UploaadImgtext);
                UploaadImgtext.setVisibility(View.GONE);
                viewimg.setVisibility(View.GONE);
                Vtext.setVisibility(View.GONE);
                gallery.setOnClickListener(view3 ->
                {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent, 4);
                    alertDialog.cancel();
                });
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                LayoutInflater inflater2 = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view2 = inflater2.inflate(R.layout.gallery, null);
                builder.setView(view2);
                builder.setCancelable(true);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                ImageView gallery = view2.findViewById(R.id.pic3);
                ImageView viewimg = view2.findViewById(R.id.pic4);
//                TextView Vtext=view2.findViewById(R.id.VImgtext);
                viewimg.setOnClickListener(view5->
                {
                    String s;
                    FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                    if (firebaseUser!=null)
                    {
                        s = firebaseUser.getUid();
                    }
                    else
                    {
                        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("Google",0);
                        s=sharedPreferences.getString("personid","");
                    }

                    Intent intent=new Intent(context, Cover_Clicked.class);
                    intent.putExtra("cf",Pcover);
                    intent.putExtra("dc",s);
                    startActivity(intent);
                    alertDialog.cancel();
                });

                gallery.setOnClickListener(view3 ->
                {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent, 4);
                    alertDialog.cancel();
                });
            }
        });

        ///////////




        postPhoto.setOnClickListener(view1 ->
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
            LayoutInflater inflater2=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view2=inflater2.inflate(R.layout.gallery_cam_view,null);
            builder.setView(view2);
            builder.setCancelable(true);

            AlertDialog alertDialog=builder.create();
            alertDialog.show();

            ImageView openCam=view2.findViewById(R.id.cam2);
            ImageView gallery=view2.findViewById(R.id.pic2);
            gallery.setOnClickListener(view3 ->
            {
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent,1);
                alertDialog.cancel();
            });
            openCam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkpermission()) {
                        capturePic();
                    }
                    alertDialog.cancel();
                }
            });
//            alertDialog.cancel();

        });

        postbtn1.setOnClickListener(view2->
        {
            FriendList="profile";
            Intent intent=new Intent(context, Posts_Only.class);
            startActivity(intent);
        });

        postbtn.setOnClickListener(view1 ->
        {
            if (imguri!=null)
            {
                if (storageTask!=null && storageTask.isInProgress())
                {
                    Toast.makeText(context, "Uploading in Progress...", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    FileUpload();
                }
            }
            else
            {
                if (!profilepost.getText().toString().trim().isEmpty())
                {
                    new_posts = new New_Posts(profilepost.getText().toString().trim(), "null");
                    dbreference.child(personID).child("posts").push().setValue(new_posts);
                    Toast.makeText(context, "Successfully Posted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Followers2.setOnClickListener(view1 ->
        {
//            Searched_Profile.comfrom="pfollowers";
            FriendList="Followers";
            Intent intent=new Intent(context, Followers_Class.class);
            getActivity().startActivity(intent);
        });
        Following2.setOnClickListener(view1 ->
        {
            FriendList="Following";
            Intent intent=new Intent(context, Followers_Class.class);
            getActivity().startActivity(intent);
        });
        Pupil.setOnClickListener(view1 ->
        {
            FriendList="Pupil";
            Intent intent=new Intent(context, Followers_Class.class);
            getActivity().startActivity(intent);
        });
        Trainers2.setOnClickListener(view1 ->
        {
            FriendList="Trainer";
            Intent intent=new Intent(context, Followers_Class.class);
            getActivity().startActivity(intent);
        });


        profilePhotos.setOnClickListener(view1 -> {

            Intent intent=new Intent(context, Photos_Profile.class);
            intent.putExtra("pname",ProfileName.getText().toString());
            startActivity(intent);
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                //google logout
                SharedPreferences sharedPreferences= getActivity().getSharedPreferences("Google",0);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("personid","logout");
                editor.apply();

                Intent intent=new Intent(getActivity().getApplicationContext(), Home.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    //reFresh Fragment
    private void reFreshData()
    {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }

    private boolean checkpermission() {
        if (Build.VERSION.SDK_INT>=23)
        {
            int perm = ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
            if (perm== PackageManager.PERMISSION_DENIED)
            {
                requestPermissions(new String[] {Manifest.permission.CAMERA},33);
                return false;
            }
        }
        return true;
    }

    private void MyFollowers()
    {
        dbreference.child(Menu_Home_page.CurrentLoginUserId).child("followers").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                int c=0;
                followersArrayList.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    c++;
                    Followers followers=snapshot1.getValue(Followers.class);
                    assert followers != null;
                    followers.setFollowerKey(snapshot1.getKey());
                    followersArrayList.add(followers);
                }
                Followers2.setText("Followers : "+c);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void MyFollowing()
    {
        dbreference.child(Menu_Home_page.CurrentLoginUserId).child("following").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                int c=0;
                followingArrayList.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    c++;
                    Following following=snapshot1.getValue(Following.class);
                    assert following != null;
                    following.setFollowingKey(snapshot1.getKey());
                    followingArrayList.add(following);
                }
                Following2.setText("Following : "+c);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void MyPupil()
    {
        dbreference.child(Menu_Home_page.CurrentLoginUserId).child("trainnerFollowers").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                int c=0;
                trainner_followersArrayList.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    c++;
                    Trainner_Followers trainner_followers=snapshot1.getValue(Trainner_Followers.class);
                    assert trainner_followers != null;
                    trainner_followers.setTrainnerFollowersKey(snapshot1.getKey());
                    trainner_followersArrayList.add(trainner_followers);
                }
                Pupil.setText("Pupil : "+c);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void MyTrainners()
    {
        dbreference.child(Menu_Home_page.CurrentLoginUserId).child("trainner").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                int c=0;
                trainnersArrayList.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    c++;
                    Trainners trainners=snapshot1.getValue(Trainners.class);
                    assert trainners != null;
                    trainners.setTrainnerKey(snapshot1.getKey());
                    trainnersArrayList.add(trainners);
                }
                Trainers2.setText("Trainers : "+c);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }





    private void capturePic() {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //
        if (intent.resolveActivity(context.getPackageManager())!=null) {
            startActivityForResult(intent, 2);
        }
    }

    private void FileUpload()
    {

        final ProgressDialog dialog=new ProgressDialog(getView().getContext());
        dialog.setTitle("Uploading.....");
        dialog.show();

//        progressBar.setVisibility(View.VISIBLE);
        StorageReference mountainImagesRef = storageReference.child(personID).child(System.currentTimeMillis()+".png");
        storageTask=mountainImagesRef.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mountainImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        if (!profilepost.getText().toString().trim().isEmpty())
                        {
                            new_posts = new New_Posts(profilepost.getText().toString().trim(), uri.toString());
                            dbreference.child(personID).child("posts").push().setValue(new_posts);
                        }
                        else
                        {
                            new_posts = new New_Posts("null", uri.toString());
                            dbreference.child(personID).child("posts").push().setValue(new_posts);
                        }

                        AllImages allImages=new AllImages(uri.toString());
                        dbreference.child(personID).child("Images").push().setValue(allImages);

                        dialog.dismiss();
                        Snackbar.make(scrollView2,"Successfully Posted",Snackbar.LENGTH_LONG).show();
                        profilepost.setText(null);
                        imguri=null;
                        postPhoto.setImageDrawable(getActivity().getApplicationContext().getResources().getDrawable(R.drawable.post_photo));
                    }
                });

            }
        }).addOnFailureListener(fail->
        {
            dialog.dismiss();
            Snackbar.make(scrollView2,"Uploading fail..."+fail.getMessage(),Snackbar.LENGTH_LONG).show();
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double aDouble = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                int percentageinint = (int) aDouble;

                dialog.setMessage("Uploading " + percentageinint + "%");
            }
        });
    }

    public void ProfileInformation(String pid) {
        dbreference.child(pid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful())
                {
                    scrollView2.setVisibility(View.VISIBLE);

                    if (task.getResult().exists())
                    {
                        DataSnapshot snapshot=task.getResult();

                        String Profilepic=String.valueOf(snapshot.child("Profile").getValue());
                        if (!Profilepic.equals("null"))
                        {
                            Glide.with(context)
                                    .load(Profilepic)
                                    .centerCrop()
                                    .placeholder(R.drawable.blank_profile)
                                    .error(R.drawable.blank_profile)
                                    .into(profile_image);
                            Pprofile=Profilepic;
//                            textView10.setVisibility(View.GONE);
                        }
                        else
                        {
                            Pprofile="null";
                        }

                        String FullName=String.valueOf(snapshot.child("fullName").getValue());
                        ProfileName.setText(FullName);
                        PName = FullName;

                        String Coverpic=String.valueOf(snapshot.child("cover").getValue());
                        if (!Coverpic.equals("null"))
                        {
                            Glide.with(context)
                                    .load(Coverpic)
                                    .centerCrop()
                                    .placeholder(R.drawable.fitness_theme)
                                    .error(R.drawable.fitness_theme)
                                    .into(coverpic);
                            Pcover=Coverpic;
                            textView10.setVisibility(View.GONE);
                        }
                        else
                        {
                            Pcover="null";
                        }

                        String Email=String.valueOf(snapshot.child("email").getValue());
                        if (!Email.equals("null")) {
                            profileEmail.setVisibility(View.VISIBLE);
                            profileEmail.setText(Email);
                        }
                        else
                        {
                            profileEmail.setVisibility(View.INVISIBLE);
                        }


                        String UserStatus=String.valueOf(snapshot.child("userIs").getValue());
                        if (!UserStatus.equals("null"))
                        {
                            Useris.setVisibility(View.VISIBLE);
                            Useris.setText(UserStatus);
                            if (UserStatus.equals("Trainer"))
                            {
                                Pupil.setVisibility(View.VISIBLE);
                                Trainers2.setVisibility(View.GONE);
                            }
                            else
                            {
                                Pupil.setVisibility(View.GONE);
                                Trainers2.setVisibility(View.VISIBLE);
                            }
                        }
                        else
                        {
                            Useris.setVisibility(View.INVISIBLE);
                        }
                        String Address=String.valueOf(snapshot.child("address").getValue());
                        if (!Address.equals("null")) {
                            profileAddress.setVisibility(View.VISIBLE);
                            profileAddress.setText(Address);
                        }
                        else
                        {
                            profileAddress.setVisibility(View.GONE);
                        }
                        String DOBDay=String.valueOf(snapshot.child("dobDay").getValue());
                        String DOBMonth=String.valueOf(snapshot.child("dobMonth").getValue());
                        String DOBYear=String.valueOf(snapshot.child("dobYear").getValue());
                        if (!DOBDay.equals("null"))
                        {
                            profileDOB.setVisibility(View.VISIBLE);
                            profileDOB.setText(DOBDay + "/" + DOBMonth + "/" + DOBYear);
                        }
                        else
                        {
                            profileDOB.setVisibility(View.INVISIBLE);
                        }
                    }
                }
                else
                {
//                    scrollView2.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity().getApplicationContext(), "Loading Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(fail->
        {
//            scrollView2.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity().getApplicationContext(), "Loading Failed.... "+fail.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if (firebaseUser!=null)
        {
            String s = firebaseUser.getUid();
            ProfileInformation(s);
            personID=s;
        }
        else
        {
            SharedPreferences sharedPreferences= getActivity().getSharedPreferences("Google",0);
            String s=sharedPreferences.getString("personid","");
            ProfileInformation(s);
            personID=s;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 1:
                if (resultCode==RESULT_OK)
                {
                    imguri=data.getData();
                    postPhoto.setImageURI(imguri);
                }
                break;
            case 2:
                if (resultCode==RESULT_OK)
                {
                    imguri=data.getData();
                    postPhoto.setImageURI(imguri);
                }
                break;
            case 3:
                if (resultCode== RESULT_OK)
                {
                    Uri uri=data.getData();
                    UploadProfile(uri);
                }
                break;
            case 4:
                if (resultCode== RESULT_OK)
                {
                    Uri uri=data.getData();
                    UploadProfile2(uri);
                }
                break;

        }
    }

    private void UploadProfile2(Uri uri2) {
        final ProgressDialog dialog=new ProgressDialog(getView().getContext());
        dialog.setTitle("Cover Uploading...");
        dialog.show();

//        progressBar.setVisibility(View.VISIBLE);
        StorageReference mountainImagesRef = storageReference.child(personID).child(System.currentTimeMillis()+".png");
        storageTask=mountainImagesRef.putFile(uri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mountainImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri)
                    {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("cover", uri.toString());

                        dbreference.child(personID).updateChildren(map).addOnSuccessListener(succ ->
                        {
                            Snackbar.make(scrollView2,"Cover Successfully Uploaded",Snackbar.LENGTH_LONG).show();

                        }).addOnFailureListener(fail ->
                        {
                            Snackbar.make(scrollView2,fail.getMessage(),Snackbar.LENGTH_LONG).show();
                        });

                        AllImages allImages=new AllImages(uri.toString());
                        dbreference.child(personID).child("Images").push().setValue(allImages);

                        dialog.dismiss();
//                        Snackbar.make(scrollView2,"Successfully Posted",Snackbar.LENGTH_LONG).show();
                        coverpic.setImageURI(uri2);
                        Pcover=uri.toString();
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "Pull the screen to Refresh", Toast.LENGTH_SHORT).show();
                            }
                        },500);
                    }
                });

            }
        }).addOnFailureListener(fail->
        {
            dialog.dismiss();
            Snackbar.make(scrollView2,"Uploading fail... "+fail.getMessage(),Snackbar.LENGTH_LONG).show();
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double aDouble = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                int percentageinint = (int) aDouble;

                dialog.setMessage("Uploading " + percentageinint + "%");
            }
        });
        ////////////////////////
    }

    private void UploadProfile(Uri uri2) {

        final ProgressDialog dialog=new ProgressDialog(getView().getContext());
        dialog.setTitle("Profile Uploading...");
        dialog.show();

//        progressBar.setVisibility(View.VISIBLE);
        StorageReference mountainImagesRef = storageReference.child(personID).child(System.currentTimeMillis()+".png");
        storageTask=mountainImagesRef.putFile(uri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mountainImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri)
                    {
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("Profile", uri.toString());

                             dbreference.child(personID).updateChildren(map).addOnSuccessListener(succ ->
                            {
                                Snackbar.make(scrollView2,"Profile successfully uploaded",Snackbar.LENGTH_LONG).show();

                            }).addOnFailureListener(fail ->
                            {
                                Snackbar.make(scrollView2,fail.getMessage(),Snackbar.LENGTH_LONG).show();
                            });
                            AllImages allImages=new AllImages(uri.toString());
                            dbreference.child(personID).child("Images").push().setValue(allImages);

                        dialog.dismiss();
                        profile_image.setImageURI(uri2);
                        Pprofile=uri.toString();
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "Pull the screen to Refresh", Toast.LENGTH_SHORT).show();
                            }
                        },500);
                    }
                });

            }
        }).addOnFailureListener(fail->
        {
            dialog.dismiss();
            Snackbar.make(scrollView2,"Uploading fail..."+fail.getMessage(),Snackbar.LENGTH_LONG).show();
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double aDouble = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                int percentageinint = (int) aDouble;

                dialog.setMessage("Uploading " + percentageinint + "%");
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==33 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            capturePic();
        }
        else
        {
            Toast.makeText(context, "Please Grant Camera Permission", Toast.LENGTH_SHORT).show();
        }
    }



/////////////////////////////////////////



}
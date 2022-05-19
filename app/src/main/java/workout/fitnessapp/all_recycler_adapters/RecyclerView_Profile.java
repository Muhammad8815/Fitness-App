package workout.fitnessapp.all_recycler_adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import workout.fitnessapp.Searched_Profile;
import workout.fitnessapp.profile_data.Edit_post_profile;
import workout.fitnessapp.profile_data.Image_Clicked;
import workout.fitnessapp.data_classes.New_Posts;
import workout.fitnessapp.profile_data.Posts_Only;
import workout.fitnessapp.R;

public class RecyclerView_Profile extends RecyclerView.Adapter<RecyclerView_Profile.MyViewHolder> {

    Context context;
    ArrayList<New_Posts> list;
    String PName;
    String PUid;
    String Pprofile;
//    ArrayList<Favorite> favoriteArrayList=new ArrayList<>();
//    int i;

    public RecyclerView_Profile(Context context, ArrayList<New_Posts> list) {
        this.context = context;
        this.list = list;
    }


    public void setPprofile(String pprofile) {
        Pprofile = pprofile;
    }

    public void setPUid(String PUid) {
        this.PUid = PUid;
    }

    public void setPName(String PName) {
        this.PName = PName;
    }

    public RecyclerView_Profile() {
    }

    @NonNull
    @Override
    public RecyclerView_Profile.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_profile,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView_Profile.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
/*
        Posts_Only.db
                .child(list.get(position).getUuid())
                .child("favorite").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int c = 0;
//                trai = 0;
                int che=0;
                favoriteArrayList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    c++;
                    Favorite favorite = snapshot1.getValue(Favorite.class);
                    assert favorite != null;
                    if (favorite.getFavorite().equals(PUid)) {
                        favorite.setFavoriteKey(snapshot1.getKey());
                        favoriteArrayList.add(favorite);
                        trai = 1;
                        che++;
                        holder.favorite.setBackgroundResource(R.drawable.post_red);
                    }
                }
                if (che==0)
                {
                    trai=0;
                }
//                    holder.Nofavorite.setVisibility(View.VISIBLE);
                    holder.Nofavorite.setText("" + c);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
*/

        holder.pname.setText(PName);

        Glide.with(context)
                .load(Pprofile)
                .centerCrop()
                .placeholder(R.drawable.fitness_logo)
                .error(R.drawable.blank_profile)
                .into(holder.profile_image_post);


        holder.share.setOnClickListener(view ->
        {
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Hello there!!\n    I'm sahre Fitness App Post: \n"+ list.get(position).getImgUrl());
            whatsappIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                holder.more_dialogbox_btn.getContext().startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                holder.more_dialogbox_btn.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.whatsapp")));
            }
        });

/*
        holder.favorite.setOnClickListener(view ->
        {
            if (trai!=1)
            {

                Favorite favorite2=new Favorite(PUid);
                Posts_Only.db.child(list.get(position).getUuid()).child("favorite").push().setValue(favorite2).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused)
                    {
                        holder.favorite.setBackgroundResource(R.drawable.post_red);
                        trai=1;
                        Toast.makeText(context, "Favorite", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(fail ->
                {
                    Toast.makeText(context, fail.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
            else
            {
                Toast.makeText(context, favoriteArrayList.get(0).getFavoriteKey(), Toast.LENGTH_SHORT).show();
                Posts_Only.db
                        .child(list.get(position).getUuid())
                        .child("favorite")
                        .child(favoriteArrayList.get(0).getFavoriteKey())
//                        .child("favorite")
                        .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        holder.favorite.setBackgroundResource(R.drawable.post_press_state);
                        trai=0;
                        Toast.makeText(context, "favorite removed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(fail->
                {
                    Toast.makeText(context, fail.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }



        });

*/

        holder.Posttext.setText(list.get(position).getPost());
        if (holder.Posttext.getText().toString().equals("null"))
        {
//            Toast.makeText(context, "nnn", Toast.LENGTH_SHORT).show();
            holder.Posttext.setText("");
        }

//            holder.postpic.setVisibility(View.VISIBLE);

            Glide.with(context)
                    .load(list.get(position).getImgUrl())
                    .centerCrop()
                    .placeholder(R.drawable.fitness_logo)
                    .error(R.drawable.error_pic)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.postpic.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(holder.postpic);
//        if (holder.postpic.getTe)

            holder.postpic.setOnClickListener(view ->
            {
                Intent intent=new Intent(view.getContext(), Image_Clicked.class);
                intent.putExtra("imgurl",list.get(position).getImgUrl());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            });

        if (RecyclerView_TimeLine.from.equals("timeL"))
        {
            holder.more_dialogbox_btn.setVisibility(View.GONE);
        }
        else
        {
            holder.more_dialogbox_btn.setOnClickListener(view ->
            {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = layoutInflater.inflate(R.layout.edit_post, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(dialogView);

                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setLayout(0, 400);
                alertDialog.show();

                Button vpost = dialogView.findViewById(R.id.vpost);
                vpost.setOnClickListener(view1 -> {
                    Intent intent = new Intent(view1.getContext(), Image_Clicked.class);
                    intent.putExtra("imgurl", list.get(position).getImgUrl());
                    view1.getContext().startActivity(intent);
                    alertDialog.cancel();
                });

                Button vpshare = dialogView.findViewById(R.id.vpshare);
                vpshare.setOnClickListener(view1 ->
                {
                    Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                    whatsappIntent.setType("text/plain");
                    whatsappIntent.setPackage("com.whatsapp");
                    whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Hello there!!\n    I'm sahre Fitness App Post: \n"+ list.get(position).getImgUrl());
                    whatsappIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    try {
                        holder.more_dialogbox_btn.getContext().startActivity(whatsappIntent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        holder.more_dialogbox_btn.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.whatsapp")));
                    }
                });
                Button vpedit = dialogView.findViewById(R.id.vpedit);
                Button vpdelete = dialogView.findViewById(R.id.vpdelete);


                if (Searched_Profile.comfrom.equals("profile")){

                    vpedit.setOnClickListener(view1 ->
                    {
                        Intent intent = new Intent(view1.getContext(), Edit_post_profile.class);
                        intent.putExtra("ePosttext", list.get(position).getPost());
                        intent.putExtra("epostimgurl", list.get(position).getImgUrl());
                        intent.putExtra("euuid", list.get(position).getUuid());
                        view1.getContext().startActivity(intent);
                        ((Activity)context).finish();
                        alertDialog.cancel();
                    });

                    vpdelete.setOnClickListener(view1 ->
                    {
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                        builder2.setTitle("Delete!!!").setMessage("Are you really want to delete this?").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Posts_Only.db.child(list.get(position).getUuid()).removeValue().addOnSuccessListener(succ ->
                                {
                                    Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();
                                    alertDialog.cancel();
                                    Intent intent=new Intent(holder.Posttext.getContext(),Posts_Only.class);
                                    view1.getContext().startActivity(intent);
                                    ((Activity)context).finish();
                                }).addOnFailureListener(fai ->
                                {
                                    alertDialog.cancel();
                                    Toast.makeText(context, fai.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                            }
                        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog.cancel();
                            }
                        });

                        AlertDialog alertDialog2 = builder2.create();
                        // alertDialog2.getWindow().setLayout(200, 400);
                        alertDialog2.show();
                    });
                }
                else
                {
                    vpedit.setVisibility(View.GONE);
                    vpdelete.setVisibility(View.GONE);
                }

            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profile_image_post;
        ImageView postpic;
        TextView Posttext,pname,nop,Nofavorite;
        Button more_dialogbox_btn,share,favorite;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_image_post=itemView.findViewById(R.id.profile_image_post);
            postpic=itemView.findViewById(R.id.postpic);
            Posttext=itemView.findViewById(R.id.posttext);
            pname=itemView.findViewById(R.id.post_username);
            nop=itemView.findViewById(R.id.noPost);
            more_dialogbox_btn=itemView.findViewById(R.id.more_dialogbox_btn);
            Nofavorite=itemView.findViewById(R.id.Nofavorite);
            share=itemView.findViewById(R.id.share);
            favorite=itemView.findViewById(R.id.favorite);
//            nop.setVisibility(View.VISIBLE);
//            nop.setText("No Posts");
        }
    }
}

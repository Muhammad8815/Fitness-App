package workout.fitnessapp.profile_data;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

import workout.fitnessapp.R;

public class Edit_post_profile extends AppCompatActivity {

    EditText edit_old_post;
    ImageView old_post_pic;
    Button edit_old_post_save,edit_old_post_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post_profile);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        edit_old_post=findViewById(R.id.edit_old_post);
        old_post_pic=findViewById(R.id.old_post_pic);
        edit_old_post_save=findViewById(R.id.edit_old_post_save);
        edit_old_post_delete=findViewById(R.id.edit_old_post_delete);

        String post= getIntent().getStringExtra("ePosttext");
        String imgurl= getIntent().getStringExtra("epostimgurl");
        String uuid= getIntent().getStringExtra("euuid");

        edit_old_post.setText(post);

        if (imgurl.equals("null"))
        {
            old_post_pic.setVisibility(View.GONE);
        }
        else
        {
            //
            old_post_pic.setVisibility(View.VISIBLE);

            Glide.with(this)
                    .load(imgurl)
                    .placeholder(R.drawable.fitness_logo)
                    .error(R.drawable.error_pic)
                    .into(old_post_pic);
        }

        edit_old_post_save.setOnClickListener(view ->
        {
            HashMap<String, Object> map = new HashMap<>();
            map.put("post", edit_old_post.getText().toString().trim());

            Posts_Only.db.child(uuid).updateChildren(map).addOnSuccessListener(succ ->
            {
                Snackbar.make(view,"Successfully Updated",Snackbar.LENGTH_LONG).show();

            }).addOnFailureListener(fail ->
            {
                Snackbar.make(view,fail.getMessage(),Snackbar.LENGTH_LONG).show();
            });
        });
        edit_old_post_delete.setOnClickListener(view ->
        {
            AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
            builder2.setTitle("Delete!!!").setMessage("Are you really want to delete this?").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Posts_Only.db.child(uuid).removeValue().addOnSuccessListener(succ->
                    {
                        Toast.makeText(Edit_post_profile.this, "Successfully deleted", Toast.LENGTH_SHORT).show();
                        finish();
                      }).addOnFailureListener(fai->
                    {
                        Toast.makeText(Edit_post_profile.this, fai.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            AlertDialog alertDialog2 = builder2.create();
            alertDialog2.show();
        });
    }
}
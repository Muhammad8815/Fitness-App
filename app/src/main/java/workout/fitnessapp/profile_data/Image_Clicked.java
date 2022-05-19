package workout.fitnessapp.profile_data;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import workout.fitnessapp.R;

public class Image_Clicked extends AppCompatActivity {

    ImageView imgclicked;
//    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_clicked);
        imgclicked=findViewById(R.id.imgclicked);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        String s= getIntent().getStringExtra("imgurl");

        Glide.with(Image_Clicked.this)
                .load(s)
                .placeholder(R.drawable.fitness_logo)
                .error(R.drawable.error_pic)
                .into(imgclicked);
    }
}
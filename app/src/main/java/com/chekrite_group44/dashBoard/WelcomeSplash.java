/*
 * Date: 2020.4.27
 * This file is created by Kai.
 * Summary:
 */

/*
 * Date: 2020.4.19
 * This file is created by Kai.
 * Summary:
 */

/*
 * Date: 2020.4.5
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.dashBoard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chekrite_group44.R;

public class WelcomeSplash extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    ImageView profile_photo;
    TextView username;
    String photo_url,first_name,last_name, employee_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_splash);
        photo_url=getIntent().getStringExtra("profile_photo");
        first_name=getIntent().getStringExtra("first_name");
        last_name=getIntent().getStringExtra("last_name");
        profile_photo=findViewById(R.id.profile_photo);
        username=findViewById(R.id.user_name);
        username.setText(first_name+" "+last_name);
        Glide.with(getApplicationContext()).load(photo_url).apply(RequestOptions.circleCropTransform()).into(profile_photo);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent dashboardIntent = new Intent(WelcomeSplash.this, Dashboard.class);
                dashboardIntent.putExtra("profile_image", photo_url);
                startActivity(dashboardIntent);
                finish();

            }
        }, SPLASH_TIME_OUT);
    }
}

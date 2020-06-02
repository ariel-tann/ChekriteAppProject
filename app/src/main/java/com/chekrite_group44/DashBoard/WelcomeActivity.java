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

package com.chekrite_group44.DashBoard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chekrite_group44.Chekrite;
import com.chekrite_group44.R;

public class WelcomeActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2400;
    ImageView profilePhoto;
    TextView userName;
    String photoUrl, firstName, lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_splash);

        //Set background color
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar2);
        toolbar.setBackgroundColor(Chekrite.getParseColor());

        photoUrl =getIntent().getStringExtra("profile_photo");
        firstName =getIntent().getStringExtra("first_name");
        lastName =getIntent().getStringExtra("last_name");
        profilePhoto =findViewById(R.id.profile_photo);
        userName =findViewById(R.id.user_name);
        userName.setText(firstName +" "+ lastName);
        if(!photoUrl.equals("null")) {
            Glide.with(getApplicationContext()).load(photoUrl).apply(RequestOptions.circleCropTransform()).into(profilePhoto);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent dashboardIntent = new Intent(WelcomeActivity.this, DashboardActivity.class);
                dashboardIntent.putExtra("profile_image", photoUrl);
                startActivity(dashboardIntent);
                finish();

            }
        }, SPLASH_TIME_OUT);
    }


}

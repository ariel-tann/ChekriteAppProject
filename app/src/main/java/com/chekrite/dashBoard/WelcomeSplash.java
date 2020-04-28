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

package com.chekrite.dashBoard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.chekrite.R;

public class WelcomeSplash extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent dashboardIntent = new Intent(WelcomeSplash.this, Dashboard.class);
                startActivity(dashboardIntent);
                finish();

            }
        }, SPLASH_TIME_OUT);
    }
}

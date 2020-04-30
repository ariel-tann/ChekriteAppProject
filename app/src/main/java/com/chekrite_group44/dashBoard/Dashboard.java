/*
 * Date: 2020.4.5
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.dashBoard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.chekrite_group44.Login;
import com.chekrite_group44.R;
import com.chekrite_group44.SelectAssetScreen.SelectAssetScreen;


public class Dashboard extends AppCompatActivity {
    private Button logout_button;
    ImageView check_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        logout_button=findViewById(R.id.logout_button);
        check_button=findViewById(R.id.newCheck);
        check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewcheck(view);
            }
        });
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });


    }
    public void startNewcheck(View view){
        Log.d("startcheck","Inside start check");
        Intent intent = new Intent(this, SelectAssetScreen.class);
        startActivity(intent);

    }
    public void logout(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}

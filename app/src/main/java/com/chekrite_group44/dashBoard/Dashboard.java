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

import com.chekrite_group44.R;
import com.chekrite_group44.select_asset_screen.Select_asset_screen;

public class Dashboard extends AppCompatActivity {
    private Button button;
import com.chekrite_group44.Login;
import com.chekrite_group44.R;
import com.chekrite.SelectAssetScreen.SelectAssetScreen;
import com.chekrite.SelectAssetScreen.SelectAssetScreenFragment;

public class Dashboard extends AppCompatActivity {
    private Button logout_button;
    ImageView check_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
<<<<<<< Updated upstream:app/src/main/java/com/chekrite_group44/dashBoard/Dashboard.java
=======
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
>>>>>>> Stashed changes:app/src/main/java/com/chekrite/dashBoard/Dashboard.java


    }
    public void startNewcheck(View view){
        Log.d("startcheck","Inside start check");
        Intent intent = new Intent(this, SelectAssetScreen.class);
        startActivity(intent);
//        SelectAssetScreenFragment selectAssetScreenFragment =new SelectAssetScreenFragment();
//        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout_selectassetscreen, selectAssetScreenFragment).commit();


    }
}

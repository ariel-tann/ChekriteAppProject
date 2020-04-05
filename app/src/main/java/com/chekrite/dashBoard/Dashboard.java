/*
 * Date: 2020.4.5
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite.dashBoard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.chekrite.R;
import com.chekrite.select_asset_screen.Select_asset_screen;

public class Dashboard extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


    }
    public void startNewcheck(View view){
        Intent intent = new Intent(this, Select_asset_screen.class);
        startActivity(intent);
    }
}

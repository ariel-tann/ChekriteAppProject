/*
 * Date: 2020.4.30
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.SelectAssetScreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.chekrite_group44.R;
import com.chekrite_group44.DashBoard.Dashboard;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


public class SelectAssetScreen extends AppCompatActivity {
    Button backbutton;
    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_asset_screen_fragment);
        backbutton=findViewById(R.id.back_button);
        viewPager=findViewById(R.id.view_pager_select_tab);
        tabLayout=findViewById(R.id.sliding_tabs);
        viewPager.setAdapter(new TabFragmentPagerAdapter(getSupportFragmentManager(),this));
        tabLayout.setupWithViewPager(viewPager);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dashboard(view);
            }
        });
        Log.d("LOG","Inside select asset screeen ");
    }


    public void dashboard(View view){
        Intent dashboardintent=new Intent(getApplicationContext(), Dashboard.class);
        startActivity(dashboardintent);
    }

}

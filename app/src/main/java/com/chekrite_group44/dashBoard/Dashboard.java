/*
 * Date: 2020.4.5
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.dashBoard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chekrite_group44.Asset_Properties.Asset_Classes;
import com.chekrite_group44.Asset_Properties.Select_Asset_Classes;
import com.chekrite_group44.Login;
import com.chekrite_group44.R;
import com.chekrite_group44.SelectAssetScreen.SelectAssetScreen;
import com.chekrite_group44.http_request.APIs;
import com.chekrite_group44.http_request.APIsListener;
import com.chekrite_group44.http_request.APIsTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Dashboard extends AppCompatActivity {
    private Button logout_button;
    ImageView check_button;
    ImageView profile_button_photo;
    String photo_url;
    APIsListener AssetsListener = new APIsListener() {
        @Override
        public void API_Completed(JSONObject jsonObject) {
            try {
                String status = (String) jsonObject.get("status");
                if(status.equals("success")){
                    Log.d("KAI", "GET assets success");
                    ArrayList<Asset_Classes> asset_classes = new Select_Asset_Classes(jsonObject).getAsset_classes();
                    Log.d("KAI","number of asset classes: "+asset_classes.size());
                    Log.d("KAI",""+asset_classes.get(0).getAssets().get(0).getUnit_number());
                }else{
                    // TODO logout fail
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };
    APIsListener LogoutListener = new APIsListener() {
        @Override
        public void API_Completed(JSONObject jsonObject) {
            try {
                String status = (String) jsonObject.get("status");
                if(status.equals("success")){
                    Log.d("KAI", "Logout success");
                }else{
                    // TODO logout fail
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        SharedPreferences pref = getSharedPreferences(Login.SHARED_PREFS, Context.MODE_PRIVATE);
        String highlight_colour = pref.getString("highlight_colour", "#65cb81");
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.parseColor(highlight_colour));

        photo_url=getIntent().getStringExtra("profile_image");
        profile_button_photo = findViewById(R.id.btn_profile);
        //get_btn_profile
        Glide.with(getApplicationContext()).load(photo_url).apply(RequestOptions.circleCropTransform()).into(profile_button_photo);

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
        new APIsTask(AssetsListener, getApplicationContext()).execute("GET", APIs.ASSETS,"","");
        new APIsTask(LogoutListener, getApplicationContext()).execute("POST", APIs.LOGOUT,"","");
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}

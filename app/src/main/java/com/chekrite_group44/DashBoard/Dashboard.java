/*
 * Date: 2020.4.5
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.DashBoard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chekrite_group44.ArielTest;
import com.chekrite_group44.Asset_Properties.Asset_Classes;
import com.chekrite_group44.Asset_Properties.Select_Asset_Classes;
import com.chekrite_group44.Categories.Categories;
import com.chekrite_group44.Chekrite;
import com.chekrite_group44.Login;
import com.chekrite_group44.R;
import com.chekrite_group44.SelectAssetScreen.SelectAssetScreen;
import com.chekrite_group44.Tests.Inspection_main;
import com.chekrite_group44.Http_Request.APIs;
import com.chekrite_group44.Http_Request.APIsListener;
import com.chekrite_group44.Http_Request.APIsTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Dashboard extends AppCompatActivity {
    private Button logout_button;
    ImageView check_button;
    ImageView profile_button_photo;
    String photo_url;
    private ImageButton test_btns;
    private ImageButton test_btns_ctgr;
   // private ImageButton ariel_test_btn;


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
                    String message = jsonObject.getString("message");
                    Toast toast = Toast.makeText(getApplicationContext(),
                            message, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else{
                    String message = jsonObject.getString("message");
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Error: "+message, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
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

        //Set background color
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Chekrite.getParseColor());
        photo_url = Chekrite.getString("profile_photo");
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
        // for testing
        test_btns = findViewById(R.id.imageButton2);
        test_btns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInspection();
            }
        });
        test_btns_ctgr =findViewById(R.id.imageButton1);
        test_btns_ctgr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCategories();
            }
        });

        //ariel test
        profile_button_photo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                open_ariel_test();
            }
        });
    }
    private void openInspection() {
        Intent intent = new Intent(this, Inspection_main.class);
        intent.putExtra("checklist_id", "5011");
        intent.putExtra("asset_id", 28436);
        intent.putExtra("asset_selection", "search");
        startActivity(intent);
    }
    public void openCategories() {
        Intent intent = new Intent(this, Categories.class);
     //   intent.putExtra("asset_id", "28433");
       // intent.putExtra("make", "Haulotte");
       // intent.putExtra("unit_number", "AE-001");
        //intent.putExtra("model", "Compact 10 Scissor Lift");
        //intent.putExtra("photo", "https://chekrite-cdn.s3.ap-southeast-2.amazonaws.com/141/Make/DtYVyRGaCm.jpg");
        intent.putExtra("asset_id", "28436");

        intent.putExtra("make", "Test");
        intent.putExtra("unit_number", "UFO-01");
        intent.putExtra("model", "Test");
        intent.putExtra("photo", "https://chekrite-cdn.s3.ap-southeast-2.amazonaws.com/141/Make/xuM2F2l9Ye.png");

        startActivity(intent);

    }
    public void startNewcheck(View view){
        Log.d("startcheck","Inside start check");
        Intent intent = new Intent(this, SelectAssetScreen.class);
       // new APIsTask(AssetsListener, getApplicationContext()).execute("GET", APIs.ASSETS,"","");
        startActivity(intent);

    }
    public void logout(){
        new APIsTask(AssetsListener, getApplicationContext()).execute("GET", APIs.ASSETS,"","");
        new APIsTask(LogoutListener, getApplicationContext()).execute("POST", APIs.LOGOUT,"","");
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void open_ariel_test() {
        Intent intent = new Intent(this, ArielTest.class);
        startActivity(intent);
    }
}

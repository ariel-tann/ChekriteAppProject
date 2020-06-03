/*
 * Date: 2020.5.20
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.ChecklistSelection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chekrite_group44.Chekrite;
import com.chekrite_group44.R;
import com.chekrite_group44.Inspection.InspectionActivity;


public class StartInspectionActivity extends AppCompatActivity {
    ImageView assetImage;
    TextView assetUnum;
    TextView assetMake;
    TextView assetModel;
    ImageButton logoutBtn;
    TextView checklistCategory;
    TextView checklistName;
    Button startBtn;
    ImageButton backBtn;
    TextView backText;
    private static final String TAG = "checklist";

    //asset data
   private Integer selectedAssetId;
   private String selectedAssetUnumber;
   private String selectedAssetMake;
   private String selectedAssetModel;
   private String selectedAssetPhoto;

   //checklist data
   private String selectedChecklistId;
   private String selectedChecklistCategory;
   private String selectedChecklistName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_inspection);
        SharedPreferences pref = getSharedPreferences(Chekrite.SHARED_PREFS, Context.MODE_PRIVATE);
        String highlight_colour = pref.getString("highlight_colour", "#65cb81");


        //toolbar
        Toolbar toolbar = findViewById(R.id.start_toolbar);
        toolbar.setBackgroundColor(Chekrite.getParseColor());
        //back button
        backBtn = findViewById(R.id.back_arrow);
        backText = findViewById(R.id.back_text);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartInspectionActivity.super.onBackPressed();
            }
        });
        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartInspectionActivity.super.onBackPressed();
            }
        });
        TextView title = (TextView) findViewById(R.id.screen_title);
        title.setText("Start");
        logoutBtn =(ImageButton) findViewById(R.id.logout_img_btn);
        String profile_link = pref.getString("profile_photo", "");
        if(!profile_link.equals("null")) {
            Glide.with(getApplicationContext()).load(profile_link).apply(RequestOptions.circleCropTransform()).into(logoutBtn);
        }
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignout();
            }
        });





        //get selected infromation
        selectedAssetId = getIntent().getIntExtra("asset_id", 0);
        selectedAssetUnumber = getIntent().getStringExtra("unit_number");
        selectedAssetMake = getIntent().getStringExtra("make");
        selectedAssetModel = getIntent().getStringExtra("model");
        selectedAssetPhoto =getIntent().getStringExtra("photo");
        selectedChecklistCategory =getIntent().getStringExtra("category");
        selectedChecklistName =getIntent().getStringExtra("name");
        selectedChecklistId = getIntent().getStringExtra("checklist_id");

        //test getExtra
        Log.d(TAG, "getExtra checklist id: " + selectedChecklistId);





        assetImage =(ImageView) findViewById(R.id.asset_image);
        Glide.with(getApplicationContext()).load(selectedAssetPhoto).apply(RequestOptions.circleCropTransform()).into(assetImage);
        assetUnum = (TextView) findViewById(R.id.unit_number);
        assetUnum.setText(selectedAssetUnumber);
        assetMake = (TextView) findViewById(R.id.make);
        assetMake.setText(selectedAssetMake);
        assetModel = (TextView) findViewById(R.id.model);
        assetModel.setText(selectedAssetModel);

        checklistCategory = (TextView) findViewById(R.id.test_category);
        checklistCategory.setText(selectedChecklistCategory);
        checklistCategory.setTextColor(Chekrite.getParseColor());
        checklistName = (TextView) findViewById(R.id.inspection);
        checklistName.setText(selectedChecklistName);
        checklistName.setTextColor(Chekrite.getParseColor());


        //start inspection
        startBtn = (Button) findViewById(R.id.start_inspection);
        // Button Radius
        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius(10);
        shape.setColor(Chekrite.getParseColor());
        startBtn.setBackground(shape);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInspection();
            }
        });
    }

    public void openSignout() {
        Intent intent = new Intent(this, SignoutActivity.class);
        //    intent.putExtra("asset_id", 28436);
        startActivity(intent);
    }


    private void openInspection() {
        Intent intent = new Intent(this, InspectionActivity.class);
        intent.putExtra("checklist_id", selectedChecklistId);
        intent.putExtra("asset_id", selectedAssetId);
        intent.putExtra("asset_selection", "search");
        startActivity(intent);
    }
}

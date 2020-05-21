/*
 * Date: 2020.5.20
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Categories;

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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chekrite_group44.Chekrite;
import com.chekrite_group44.R;
import com.chekrite_group44.Tests.Inspection_main;

import org.w3c.dom.Text;


public class StartInspection extends AppCompatActivity {
    ImageView asset_image;
    TextView asset_unum;
    TextView asset_make;
    TextView asset_model;

    TextView checklist_category;
    TextView checklist_name;
    Button start;
    private static final String TAG = "checklist";

    //data
   private Integer selected_asset_id;
   private String selected_asset_unumber;
   private String selected_asset_make;
   private String selected_asset_model;
   private String selected_asset_photo;
   private String selected_checklist_id;
   private String selected_checklist_category;
   private String selected_checklist_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_inspection);
        SharedPreferences pref = getSharedPreferences(Chekrite.SHARED_PREFS, Context.MODE_PRIVATE);
        String highlight_colour = pref.getString("highlight_colour", "#65cb81");

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.categories_toolbar);
        toolbar.setBackgroundColor(Color.parseColor(highlight_colour));
        TextView title = (TextView) findViewById(R.id.screen_title);
        title.setText("Start");
        start = (Button) findViewById(R.id.start_inspection);
        start.setBackgroundColor(Color.parseColor(highlight_colour));


        selected_asset_id = getIntent().getIntExtra("asset_id", 0);
        selected_asset_unumber = getIntent().getStringExtra("unit_number");
        selected_asset_make = getIntent().getStringExtra("make");
        selected_asset_model = getIntent().getStringExtra("model");
        selected_asset_photo =getIntent().getStringExtra("photo");
        selected_checklist_category =getIntent().getStringExtra("category");
        selected_checklist_name =getIntent().getStringExtra("name");
        selected_checklist_id = getIntent().getStringExtra("checklist_id");

        //test getExtra
        Log.d(TAG, "getExtra name: " + selected_checklist_name);


        asset_image =(ImageView) findViewById(R.id.asset_image);
        Glide.with(getApplicationContext()).load(selected_asset_photo).apply(RequestOptions.circleCropTransform()).into(asset_image);
        asset_unum = (TextView) findViewById(R.id.unit_number);
        asset_unum.setText(selected_asset_unumber);
        asset_make = (TextView) findViewById(R.id.make);
        asset_make.setText(selected_asset_make);
        asset_model = (TextView) findViewById(R.id.model);
        asset_model.setText(selected_asset_model);

        checklist_category = (TextView) findViewById(R.id.test_category);
        checklist_category.setText(selected_checklist_category);
        checklist_name = (TextView) findViewById(R.id.inspection);
        checklist_name.setText(selected_checklist_name);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInspection();
            }
        });
    }

        


    private void openInspection() {
        Intent intent = new Intent(this, Inspection_main.class);
        intent.putExtra("checklist_id", "5020");
        intent.putExtra("asset_id", 28436);
        intent.putExtra("asset_selection", "search");
        startActivity(intent);
    }
}

/*
 * Date: 2020.5.16
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Categories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chekrite_group44.Chekrite;
import com.chekrite_group44.R;

public class ActivityChecklist extends AppCompatActivity {
    String selected_category;
    TextView category_test;
    TextView toolbar_title;
    TextView list_title;
    ImageButton logout_btn;

    //asset panel
    ImageView asset_image;
    TextView asset_unum;
    TextView asset_make;
    TextView asset_model;
    ListView listView;


    //DATA
    String selected_asset_id;
    String selected_asset_unumber;
    String selected_asset_make;
    String selected_asset_model;
    String selected_asset_photo;

    private static final String TAG = "checklist";

    String categorytest;
    @Override



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);

//get sharedpreferences
        SharedPreferences pref = getSharedPreferences(Chekrite.SHARED_PREFS, Context.MODE_PRIVATE);

        Bundle bundle =getIntent().getExtras();
       // category_test = getIntent().getStringExtra("model");
        // if bundle != null {...  getstring("category ")}
        if(bundle != null ) {
            Log.d(TAG, "get bundle extra  " + bundle );

            selected_category = bundle.getString("category");
            Log.d(TAG, "set category " + selected_category );
               category_test = (TextView) findViewById(R.id.selected_category);
               category_test.setText(selected_category);

        }else {
            Log.d(TAG, " bundel = null "  );
        }
        list_title = (TextView) findViewById(R.id.checklist_category_title);
        list_title.setText("Checklist Name");
        toolbar_title = (TextView) findViewById(R.id.screen_title);
        toolbar_title.setText("Checklist");
        logout_btn =(ImageButton) findViewById(R.id.logout_img_btn);
        String highlight_colour = pref.getString("highlight_colour", "#65cb81");
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.categories_toolbar);
        toolbar.setBackgroundColor(Color.parseColor(highlight_colour));
        category_test.setTextColor(Color.parseColor(highlight_colour));
        String profile_link = pref.getString("profile_photo", "");
        Glide.with(getApplicationContext()).load(profile_link).apply(RequestOptions.circleCropTransform()).into(logout_btn);


        selected_asset_id = getIntent().getStringExtra("asset_id");
        selected_asset_unumber = getIntent().getStringExtra("unit_number");
        selected_asset_make = getIntent().getStringExtra("make");
        selected_asset_model = getIntent().getStringExtra("model");
        selected_asset_photo =getIntent().getStringExtra("photo");
        asset_image =(ImageView) findViewById(R.id.asset_image);
        Glide.with(getApplicationContext()).load(selected_asset_photo).apply(RequestOptions.circleCropTransform()).into(asset_image);
        asset_unum = (TextView) findViewById(R.id.asset_number);
        asset_unum.setText(selected_asset_unumber);
        asset_make = (TextView) findViewById(R.id.asset_make);
        asset_make.setText(selected_asset_make);
        asset_model = (TextView) findViewById(R.id.asset_model);
        asset_model.setText(selected_asset_model);






      //
    }
}

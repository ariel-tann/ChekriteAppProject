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
import android.widget.TextView;

import com.chekrite_group44.Chekrite;
import com.chekrite_group44.R;

public class ActivityChecklist extends AppCompatActivity {
    String selected_category;
    TextView category_test;
    TextView toolbar_title;
    TextView list_title;
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
        String highlight_colour = pref.getString("highlight_colour", "#65cb81");
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.categories_toolbar);
        toolbar.setBackgroundColor(Color.parseColor(highlight_colour));
        category_test.setTextColor(Color.parseColor(highlight_colour));
        String profile_link = pref.getString("profile_photo", "");







      //
    }
}

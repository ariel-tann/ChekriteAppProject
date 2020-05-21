/*
 * Date: 2020.5.16
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chekrite_group44.Asset_Properties.Checklist;
import com.chekrite_group44.Asset_Properties.ChecklistArray;
import com.chekrite_group44.Chekrite;
import com.chekrite_group44.Http_Request.APIs;
import com.chekrite_group44.Http_Request.APIsListener;
import com.chekrite_group44.Http_Request.APIsTask;
import com.chekrite_group44.R;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
    ChecklistArray mChecklistArray;
    String selected_asset_id;
    String selected_asset_unumber;
    String selected_asset_make;
    String selected_asset_model;
    String selected_asset_photo;
    ArrayList<String> checkList = new ArrayList<>();


    ArrayList<String> nameList = new ArrayList<>();
    private static final String TAG = "checklist";
    String categorytest;
    Integer checklist_id;
    ArrayList<String> checklist_names = new ArrayList<>();

    ArrayAdapter<String> nameListAdapter;


    int countposition = 0;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);

//get sharedpreferences
        SharedPreferences pref = getSharedPreferences(Chekrite.SHARED_PREFS, Context.MODE_PRIVATE);
        // call apistask


        Bundle bundle = getIntent().getExtras();

        selected_asset_id = bundle.getString("asset_id");
        selected_category = bundle.getString("category");
        new APIsTask(ChecklistListener).execute("GET", APIs.CHECKLIST, selected_asset_id, "");
        // category_test = getIntent().getStringExtra("model");
        // if bundle != null {...  getstring("category ")}
        if (bundle != null) {
            Log.d(TAG, "get bundle extra  " + bundle);


            Log.d(TAG, "set category " + selected_category);
            category_test = (TextView) findViewById(R.id.selected_category);
            category_test.setText(selected_category);

            //      checkList = bundle.getStringArrayList("data_array_list");
            //      Log.d(TAG, "all checklist data " + checkList);

        } else {
            Log.d(TAG, " bundel = null ");
        }
        list_title = (TextView) findViewById(R.id.checklist_category_title);
        list_title.setText("Checklist Name");
        toolbar_title = (TextView) findViewById(R.id.screen_title);
        toolbar_title.setText("Checklist");
        logout_btn = (ImageButton) findViewById(R.id.logout_img_btn);
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
        selected_asset_photo = getIntent().getStringExtra("photo");
        asset_image = (ImageView) findViewById(R.id.asset_image);
        Glide.with(getApplicationContext()).load(selected_asset_photo).apply(RequestOptions.circleCropTransform()).into(asset_image);
        asset_unum = (TextView) findViewById(R.id.asset_number);
        asset_unum.setText(selected_asset_unumber);
        asset_make = (TextView) findViewById(R.id.asset_make);
        asset_make.setText(selected_asset_make);
        asset_model = (TextView) findViewById(R.id.asset_model);
        asset_model.setText(selected_asset_model);


        listView = (ListView) findViewById(R.id.checklist_category);
        //    listView.setAdapter(checklistAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           //     position = countposition;

                Intent intent = new Intent(ActivityChecklist.this, StartInspection.class);
                //pass asset info
                intent.putExtra("unit_number", selected_asset_unumber);
                intent.putExtra("asset_id", selected_asset_id);
                intent.putExtra("model", selected_asset_model);
                intent.putExtra("make", selected_asset_make);
                intent.putExtra("photo", selected_asset_photo);

             //   intent.putExtra("checklist_id", parent.getItemAtPosition(position).);

                intent.putExtra("checklist_id", mChecklistArray.getChecklists().get(position).getId());
                intent.putExtra("category", mChecklistArray.getChecklists().get(position).getCategory());
                intent.putExtra("name", mChecklistArray.getChecklists().get(position).getName());

                startActivity(intent);
            }
        });
    }
    APIsListener ChecklistListener = new APIsListener() {
        @Override
        public void API_Completed(JSONObject jsonObject) {
            String status = null;

            try {
                status = (String) jsonObject.get("status");
                String message = (String) jsonObject.get("message");
                if (status.equals("success")) {
                    Log.d(TAG, "success: " + message);
                    mChecklistArray = new ChecklistArray(jsonObject);


                    for (int i = 0; i < mChecklistArray.getChecklists().size(); i++) {

                        if (! mChecklistArray.getChecklists().get(i).getCategory().equalsIgnoreCase(selected_category)) {
                             mChecklistArray.getChecklists().remove(i);

                        }

                    }

                    for (int i = 0; i < mChecklistArray.getChecklists().size(); i++) {
                        Log.d(TAG, "reduced size: " + mChecklistArray.getChecklists().size());

                        nameList.add(mChecklistArray.getChecklists().get(i).getName());
                    }
                    Log.d(TAG, "checklist name filted : " + nameList);
                    nameListAdapter = new ArrayAdapter<>(ActivityChecklist.this, R.layout.simple_list_item_1, nameList);
                    listView.setAdapter(nameListAdapter);
                    Log.d(TAG, "get checklist namelist: " + nameList);


                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };
}

/*
 * Date: 2020.4.29
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.ChecklistSelection;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chekrite_group44.Chekrite;
import com.chekrite_group44.AssetProperties.Checklist;
import com.chekrite_group44.AssetProperties.ChecklistArray;
import com.chekrite_group44.HttpRequest.APIs;
import com.chekrite_group44.HttpRequest.APIsListener;
import com.chekrite_group44.HttpRequest.APIsTask;
import com.chekrite_group44.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {

    private static final String TAG = "checklist";
    private int dotsCount;
    private ImageView[] dots;
    //VIEW
    ImageButton logoutBtn;
    ImageButton backBtn;
    TextView backText;
    ViewPager viewPager;
    LinearLayout sliderDotview;
    ImageView assetImage;
    TextView assetUnum;
    TextView assetMake;
    TextView assetModel;
    ListView listView;


    //DATA
    int selectedAssetId;
    String selectedAssetUnumber;
    String selectedAssetMake;
    String selectedAssetModel;
    String selectedAssetPhoto;


    ChecklistArray mChecklistArray;
    ArrayList<String> categoriesList = new ArrayList<>();
    ArrayList<String> filteredCategories = new ArrayList<>();
    ArrayAdapter<String> listAdapter;




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

                    //if data != null, get data
                    if(mChecklistArray!=null){
                        Log.d(TAG, "mChecklistArray length: " + mChecklistArray.getChecklists().size());
                        // set item category in array
                        for (int i = 0; i< mChecklistArray.getChecklists().size(); i++) {
                            String checklist_category = mChecklistArray.getChecklists().get(i).getCategory();
                            categoriesList.add(checklist_category);
                        }
                        //filter repeated categories
                        for (String element : categoriesList) {
                            if (!filteredCategories.contains(element)) {
                                filteredCategories.add(element);
                            }
                        }

                        //set list adatper
                        listAdapter = new ArrayAdapter<>(CategoriesActivity.this, R.layout.simple_list_item_1, filteredCategories);  //for test
                        listView.setAdapter(listAdapter);

                    //return log info if data is null
                    }else{
                        Log.d(TAG, "jsonDATA = null");
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
       }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        SharedPreferences pref = getSharedPreferences(Chekrite.SHARED_PREFS, Context.MODE_PRIVATE);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.categories_toolbar);
        toolbar.setBackgroundColor(Chekrite.getParseColor());
        //toolbar panel


        logoutBtn =(ImageButton) findViewById(R.id.logout_img_btn);

        //set goback button
        backBtn = findViewById(R.id.back_arrow);
        backText = findViewById(R.id.back_text);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoriesActivity.super.onBackPressed();
            }
        });
        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoriesActivity.super.onBackPressed();
            }
        });


        // asset info panel
        selectedAssetId = getIntent().getIntExtra("asset_id", 0);
        selectedAssetUnumber = getIntent().getStringExtra("unit_number");
        selectedAssetMake = getIntent().getStringExtra("make");
        selectedAssetModel = getIntent().getStringExtra("model");
        selectedAssetPhoto =getIntent().getStringExtra("photo");
        assetImage =(ImageView) findViewById(R.id.asset_image);
        Glide.with(getApplicationContext()).load(selectedAssetPhoto).apply(RequestOptions.circleCropTransform()).into(assetImage);
        assetUnum = (TextView) findViewById(R.id.asset_number);
        assetUnum.setText(selectedAssetUnumber);
        assetMake = (TextView) findViewById(R.id.asset_make);
        assetMake.setText(selectedAssetMake);
        assetModel = (TextView) findViewById(R.id.asset_model);
        assetModel.setText(selectedAssetModel);



        listView = (ListView) findViewById(R.id.checklist_category);

        String fetchingID = Integer.toString(selectedAssetId);
        // call apis task
        new APIsTask(ChecklistListener).execute("GET", APIs.CHECKLIST, fetchingID, "");

        //set profile button upper right
        String profile_link = pref.getString("profile_photo", "");
        if(!profile_link.equals("null")) {
            Glide.with(getApplicationContext()).load(profile_link).apply(RequestOptions.circleCropTransform()).into(logoutBtn);
        }

        //profile button onclick
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignout();
            }
        });

            //LIST ONCLICK goto checklist activity
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(CategoriesActivity.this, ChecklistActivity.class);
                //sent seleced info
                intent.putExtra("category", listView.getItemAtPosition(i).toString());
                intent.putExtra("checklist_id", mChecklistArray.getChecklists().get(i).getId());
                Log.d(TAG, "mChecklistArray ids: " + mChecklistArray.getChecklists().get(i).getId());


                intent.putExtra("unit_number", selectedAssetUnumber);
                intent.putExtra("asset_id", selectedAssetId);
                intent.putExtra("model", selectedAssetModel);
                intent.putExtra("make", selectedAssetMake);
                intent.putExtra("photo", selectedAssetPhoto);


                startActivity(intent);
            }
        });
    }

    public void openChecklist() {
        Intent intent = new Intent(this, ChecklistActivity.class);
        startActivity(intent);

    }

    public void openSignout() {
        Intent intent = new Intent(this, SignoutActivity.class);
        startActivity(intent);
    }

}

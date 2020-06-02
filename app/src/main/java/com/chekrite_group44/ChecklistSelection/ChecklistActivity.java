/*
 * Date: 2020.5.16
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.ChecklistSelection;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chekrite_group44.AssetProperties.ChecklistArray;
import com.chekrite_group44.Chekrite;
import com.chekrite_group44.HttpRequest.APIs;
import com.chekrite_group44.HttpRequest.APIsListener;
import com.chekrite_group44.HttpRequest.APIsTask;
import com.chekrite_group44.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class ChecklistActivity extends AppCompatActivity {
    String selectedCategory;
    TextView categoryTest;
    TextView toolbarTitle;
    TextView listTitle;
    ImageButton logoutBtn;
    ImageButton backBtn;
    TextView backText;

    //asset panel
    ImageView assetImage;
    TextView assetUnum;
    TextView assetMake;
    TextView assetModel;
    ListView listView;


    //DATA
    ChecklistArray mChecklistArray;
    Integer selectedAssetId;
    String selectedAssetUnumber;
    String selectedAssetMake;
    String selectedAssetModel;
    String selectedAssetPhoto;


    ArrayList<String> nameList = new ArrayList<>();
    private static final String TAG = "checklist";
    ArrayAdapter<String> nameListAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        //get sharedpreferences
        SharedPreferences pref = getSharedPreferences(Chekrite.SHARED_PREFS, Context.MODE_PRIVATE);
        // call apistask

        Bundle bundle = getIntent().getExtras();

        selectedAssetId = bundle.getInt("asset_id");
        selectedCategory = bundle.getString("category");
        String FetchId = Integer.toString(selectedAssetId);
        new APIsTask(ChecklistListener).execute("GET", APIs.CHECKLIST, FetchId, "");
        if (bundle != null) {
            Log.d(TAG, "get bundle extra  " + bundle);
            Log.d(TAG, "set category " + selectedCategory);
            categoryTest = (TextView) findViewById(R.id.selected_category);
            categoryTest.setText(selectedCategory);


        } else {
            Log.d(TAG, " bundel = null ");
        }
        listTitle = (TextView) findViewById(R.id.checklist_category_title);
        listTitle.setText("Checklist Name");
        toolbarTitle = (TextView) findViewById(R.id.screen_title);
        toolbarTitle.setText("Checklist");
        logoutBtn = (ImageButton) findViewById(R.id.logout_img_btn);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.categories_toolbar);
        toolbar.setBackgroundColor(Chekrite.getParseColor());
        categoryTest.setTextColor(Chekrite.getParseColor());
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

        //toolbar goback button
        backBtn = findViewById(R.id.back_arrow);
        backText = findViewById(R.id.back_text);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChecklistActivity.super.onBackPressed();
            }
        });
        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChecklistActivity.super.onBackPressed();
            }
        });


        selectedAssetId = getIntent().getIntExtra("asset_id", 0);
        selectedAssetUnumber = getIntent().getStringExtra("unit_number");
        selectedAssetMake = getIntent().getStringExtra("make");
        selectedAssetModel = getIntent().getStringExtra("model");
        selectedAssetPhoto = getIntent().getStringExtra("photo");
        assetImage = (ImageView) findViewById(R.id.asset_image);
        Glide.with(getApplicationContext()).load(selectedAssetPhoto).apply(RequestOptions.circleCropTransform()).into(assetImage);
        assetUnum = (TextView) findViewById(R.id.asset_number);
        assetUnum.setText(selectedAssetUnumber);
        assetMake = (TextView) findViewById(R.id.asset_make);
        assetMake.setText(selectedAssetMake);
        assetModel = (TextView) findViewById(R.id.asset_model);
        assetModel.setText(selectedAssetModel);


        listView = (ListView) findViewById(R.id.checklist_category);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ChecklistActivity.this, StartInspectionActivity.class);
                //pass asset info
                intent.putExtra("unit_number", selectedAssetUnumber);
                intent.putExtra("asset_id", selectedAssetId);
                intent.putExtra("model", selectedAssetModel);
                intent.putExtra("make", selectedAssetMake);
                intent.putExtra("photo", selectedAssetPhoto);

                String checkistID = Integer.toString(mChecklistArray.getChecklists().get(position).getId());
                intent.putExtra("checklist_id",checkistID);
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

                        if (! mChecklistArray.getChecklists().get(i).getCategory().equalsIgnoreCase(selectedCategory)) {
                             mChecklistArray.getChecklists().remove(i);

                        }

                    }

                    for (int i = 0; i < mChecklistArray.getChecklists().size(); i++) {
                        Log.d(TAG, "reduced size: " + mChecklistArray.getChecklists().size());

                        nameList.add(mChecklistArray.getChecklists().get(i).getName());
                    }
                    Log.d(TAG, "checklist name filted : " + nameList);
                    nameListAdapter = new ArrayAdapter<>(ChecklistActivity.this, R.layout.simple_list_item_1, nameList);
                    listView.setAdapter(nameListAdapter);
                    Log.d(TAG, "get checklist namelist: " + nameList);


                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };
    public void openSignout() {
        Intent intent = new Intent(this, SignoutActivity.class);
        startActivity(intent);
    }
}

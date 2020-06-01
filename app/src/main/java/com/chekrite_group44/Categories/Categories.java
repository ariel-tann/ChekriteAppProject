/*
 * Date: 2020.4.29
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Categories;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class Categories extends AppCompatActivity {

    private static final String TAG = "checklist";
    private int dotscount;
    private ImageView[] dots;
    //UI
    ImageButton logout_btn;
    Button back;
    //androidx.appcompat.widget.Toolbar toolbar;
    ViewPager viewPager;
    LinearLayout sliderDotview;
    ImageView asset_image;
    TextView asset_unum;
    TextView asset_make;
    TextView asset_model;
    ListView listView;


    //DATA
    int selected_asset_id;
    String selected_asset_unumber;
    String selected_asset_make;
    String selected_asset_model;
    String selected_asset_photo;

    Checklist checklist_items;
    ChecklistArray mChecklistArray;
    ArrayList<String> categories_list = new ArrayList<>();
    ArrayList<String> filtered_categories = new ArrayList<>();
    ArrayAdapter<String> listAdapter;
    ArrayList<String> testlist =new ArrayList<>();




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




                    if(mChecklistArray!=null){
                        Log.d(TAG, "mChecklistArray length: " + mChecklistArray.getChecklists().size());

                        for (int i = 0; i< mChecklistArray.getChecklists().size(); i++) {

                            Checklist item = mChecklistArray.getChecklists().get(i);

                            Integer checklist_id = mChecklistArray.getChecklists().get(i).getId();
                            String checklist_category = mChecklistArray.getChecklists().get(i).getCategory();
               //             String checklist_name = mChecklistArray.getChecklists().get(i).getName();
                            categories_list.add(checklist_category);


                        }

                        for (String element : categories_list) {

                            if (!filtered_categories.contains(element)) {

                                filtered_categories.add(element);
                            }
                        }



                        listAdapter = new ArrayAdapter<>(Categories.this, R.layout.simple_list_item_1, filtered_categories);  //for test
                        listView.setAdapter(listAdapter);


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
        setContentView(R.layout.categories);

        SharedPreferences pref = getSharedPreferences(Chekrite.SHARED_PREFS, Context.MODE_PRIVATE);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.categories_toolbar);
       // getSupportActionBar().hide();
        //setSupportActionBar(toolbar);


        toolbar.setBackgroundColor(Chekrite.getParseColor());
        //toolbar panel


        back = (Button) findViewById(R.id.pin_cancel);
        logout_btn =(ImageButton) findViewById(R.id.logout_img_btn);

        //image url

        // asset info panel
        selected_asset_id = getIntent().getIntExtra("asset_id", 0);
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


        // for latar add : get test info for asset
        //TODO SHOW TEST INFOMATION



        listView = (ListView) findViewById(R.id.checklist_category);

        String fetchingID = Integer.toString(selected_asset_id);
        // call apis task
        new APIsTask(ChecklistListener).execute("GET", APIs.CHECKLIST, fetchingID, "");

        //set profile button upper right
        String profile_link = pref.getString("profile_photo", "");
        if(!profile_link.equals("null")) {
            Glide.with(getApplicationContext()).load(profile_link).apply(RequestOptions.circleCropTransform()).into(logout_btn);
        }

        //profile button onclick
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignout();
                //for test
         //       openChecklist();
            }
        });


        //for later add
    //    viewPager = (ViewPager) findViewById(R.id.slider);
    //    sliderDotview = (LinearLayout) findViewById(R.id.Slider_dots);
   //     ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
   //     viewPager.setAdapter(viewPagerAdapter);

   /*
        dots = new ImageView[dotscount];
        for (int i =0; i< dotscount; i++){
          dots[i] = new ImageView(this);
          dots[i].setImageDrawable(ContextCompat.getDrawable(get), R.drawable.noactive_dot);
        }
*/



            //LIST ONCLICK goto checklist activity
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(Categories.this, ActivityChecklist.class);
                //sent seleced info
   //             intent.putExtra("asset_id", searchAssetList.get(i).getId());
                intent.putExtra("category", listView.getItemAtPosition(i).toString());
                intent.putExtra("checklist_id", mChecklistArray.getChecklists().get(i).getId());
                Log.d(TAG, "mChecklistArray ids: " + mChecklistArray.getChecklists().get(i).getId());


                intent.putExtra("unit_number", selected_asset_unumber);
                intent.putExtra("asset_id", selected_asset_id);
                intent.putExtra("model", selected_asset_model);
                intent.putExtra("make", selected_asset_make);
                intent.putExtra("photo", selected_asset_photo);


                
                //***************************
                //put extra("position", pos);
                //in nect activity : get extra get int pos
                // set text ( adapter. name[pos])
                // set TEXT( adapter. id[pos])




           //     intent.putExtra("JSON_OBJECT", data.toString());
                startActivity(intent);
            }
        });




    }



    public void openChecklist() {
        Intent intent = new Intent(this, ActivityChecklist.class);
        //   intent.putExtra("asset_id", "28433");
        // intent.putExtra("make", "Haulotte");
        // intent.putExtra("unit_number", "AE-001");
        //intent.putExtra("model", "Compact 10 Scissor Lift");
        //intent.putExtra("photo", "https://chekrite-cdn.s3.ap-southeast-2.amazonaws.com/141/Make/DtYVyRGaCm.jpg");

        startActivity(intent);

    }

    public void openSignout() {
        Intent intent = new Intent(this, SignOut.class);
        //    intent.putExtra("asset_id", 28436);
        startActivity(intent);
    }
/**
 *for later to add
    private void setSliderViews(){

        for(){


            switch (i){
                case 0:
                    break;
                case 1:
                    break;

            }
        }

    }
 **/

}

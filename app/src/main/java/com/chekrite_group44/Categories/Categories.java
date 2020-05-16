/*
 * Date: 2020.4.29
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Categories;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chekrite_group44.Chekrite;
import com.chekrite_group44.Asset_Properties.Checklist;
import com.chekrite_group44.Asset_Properties.ChecklistArray;
import com.chekrite_group44.Http_Request.APIs;
import com.chekrite_group44.Http_Request.APIsListener;
import com.chekrite_group44.Http_Request.APIsTask;
import com.chekrite_group44.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Categories extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager viewPager;
    LinearLayout sliderDotview;
    private int dotscount;
    private ImageView[] dots;
    ImageButton logout_btn;
    Button back;
    String selected_asset_id;
    String selected_asset_unumber;
    String selected_asset_make;
    String selected_asset_model;
    String selected_asset_photo;

    ImageView asset_image;
    TextView asset_unum;
    TextView asset_make;
    TextView asset_model;
    ListView listView;
    String selected_asset_image;
    private static final String TAG = "checklist";
    TextView test_json;

    ChecklistArray mChecklistArray;
    ArrayList<String> categories_list = new ArrayList<>();
    ArrayList<String> filtered_categories = new ArrayList<>();
    ArrayAdapter<String> listAdapter;
    ArrayList<String> testlist =new ArrayList<>();




//get data from api with input params[asset id] :
// 1. set apitask to /api/{asset_id}/checklist
// 1.5 set api listener to get result of api tasks
// 2. get json data from fetched url, put to shared preferences
// 3. to parse json data, here should use Checklist.java class, get categories from json
// 4. define a array, setText from data to the array

//define a data group to store the data, here is list of categories


    APIsListener ChecklistListener = new APIsListener() {
        @Override
        public void API_Completed(JSONObject jsonObject) {
            String status = null;
            try {
                status = (String) jsonObject.get("status");
                String message = (String) jsonObject.get("message");
                if (status.equals("success")) {
                    Log.d(TAG, "success: " + message);

                    JSONArray data = jsonObject.getJSONArray("data");
                    if(data!=null){


                        for(int i=0; i<data.length(); i++) {
                            JSONObject object = data.getJSONObject(i);
                            Log.d(TAG, "SHOW JSON OBJECT: " + object);
                            String checklist_category = object.getString("category");

                        categories_list.add(checklist_category);

                        }
                        //filter repeated elements in category list
                        for (String element : categories_list) {

                            if (!filtered_categories.contains(element)) {

                                filtered_categories.add(element);
                            }
                        }
               //         Log.d(TAG, "get categories successfully " + filtered_categories );




                        listAdapter = new ArrayAdapter<>(Categories.this, R.layout.simple_list_item_1, filtered_categories);  //for test
                        listView.setAdapter(listAdapter);



                    }else{
                        Log.v("search by hour", "No data available");
                    }
      //              Checklist list = new Checklist(data);
      //              test_json.setText(data.toString());

     //               JSONArray jCheckList = data.getJSONArray("checklist_items");


                //    ChecklistArray categories = new ChecklistArray(data);
              //      singleParsed = "Name:" + categories.get("name");
             //       JSONArray categories = new JSONArray()
                 //   String categories = data.getString("category");
                    //PRINT EVERYTHING IN DATA

                    /*  JSONObject jchecklist = data.getJSONObject("checklist");
                    Checklist checklist = new Checklist(jchecklist);**/


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



        //toolbar panel
        SharedPreferences pref = getSharedPreferences(Chekrite.SHARED_PREFS, Context.MODE_PRIVATE);

        back = (Button) findViewById(R.id.pin_cancel);
        logout_btn =(ImageButton) findViewById(R.id.logout_img_btn);
        //image url
 //       selected_asset_image = Chekrite.getString("");
        // asset info panel
        selected_asset_id = getIntent().getStringExtra("asset_id");
        selected_asset_unumber = getIntent().getStringExtra("unit_number");
        selected_asset_make = getIntent().getStringExtra("make");
        selected_asset_model = getIntent().getStringExtra("model");
        selected_asset_photo =getIntent().getStringExtra("photo");
        asset_image =(ImageView) findViewById(R.id.asset_image);

        asset_unum = (TextView) findViewById(R.id.asset_number);
        asset_unum.setText(selected_asset_unumber);

        asset_make = (TextView) findViewById(R.id.asset_make);
        asset_make.setText(selected_asset_make);

        asset_model = (TextView) findViewById(R.id.asset_model);
        asset_model.setText(selected_asset_model);
 //       test_json = (TextView) findViewById(R.id.test_json);

        Glide.with(getApplicationContext()).load(selected_asset_photo).apply(RequestOptions.circleCropTransform()).into(asset_image);
 //       String asset_photo_url = pref.getString("photo", "");
  //      Glide.with(getApplicationContext()).load(photo).apply(RequestOptions.circleCropTransform()).into(logout_btn);


        new APIsTask(ChecklistListener, getApplicationContext()).execute("GET", APIs.CHECKLIST, selected_asset_id, "");

        String profile_link = pref.getString("profile_photo", "");
        Glide.with(getApplicationContext()).load(profile_link).apply(RequestOptions.circleCropTransform()).into(logout_btn);

        String highlight_colour = pref.getString("highlight_colour", "#65cb81");
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.categories_toolbar);
        toolbar.setBackgroundColor(Color.parseColor(highlight_colour));

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignout();
                //for test
         //       openChecklist();
            }
        });


        //for later
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

            listView = (ListView) findViewById(R.id.checklist_category);

            //LIST ONCLICK



            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(Categories.this, ActivityChecklist.class);
                intent.putExtra("category", listView.getItemAtPosition(i).toString());
           //     intent.putExtra("JSON_OBJECT", data.toString());
                startActivity(intent);
            }
        });




/***
 * list of categories type
        ArrayList<String> arrayList =new ArrayList<>();
        arrayList.add("checklist 1");
        arrayList.add("checklist 2");
        arrayList.add("checklist 3");
 **/

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

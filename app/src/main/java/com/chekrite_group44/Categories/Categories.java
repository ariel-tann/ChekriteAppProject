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
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
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
import com.chekrite_group44.Http_Request.APIsListener;
import com.chekrite_group44.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Categories extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager viewPager;
    LinearLayout sliderDotview;
    private int dotscount;
    private ImageView[] dots;
    ImageButton logout_btn;
    Button back;

    ImageView asset_image;
    TextView asset_num;
    TextView asset_info;

    ListView listView;
    private static final String TAG = "Checklist";





    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);

        //toolbar panel
        back = (Button) findViewById(R.id.pin_cancel);
        logout_btn =(ImageButton) findViewById(R.id.logout_img_btn);

        // asset info panel
        asset_image =(ImageView) findViewById(R.id.asset_image);
        asset_num = (TextView) findViewById(R.id.asset_number);
        asset_info = (TextView) findViewById(R.id.asset_info);

        SharedPreferences pref = getSharedPreferences(Chekrite.SHARED_PREFS, Context.MODE_PRIVATE);
        String profile_link = pref.getString("profile_photo", "");
        Glide.with(getApplicationContext()).load(profile_link).apply(RequestOptions.circleCropTransform()).into(logout_btn);

        String highlight_colour = pref.getString("highlight_colour", "#65cb81");
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.categories_toolbar);
        toolbar.setBackgroundColor(Color.parseColor(highlight_colour));

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignout();
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
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(Categories.this, R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.categories_items));  //for test

        // TODO: get the selected Asset's ID
        //TODO: get array of categories from {assetId}/Checklist



            //go to
            /*
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(Categories.this, Checklist.class);
                Intent.putExtra("ChecklistCategory", listView.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });
            * **/

        listView.setAdapter(listAdapter);

/***
 * list of categories type
        ArrayList<String> arrayList =new ArrayList<>();
        arrayList.add("checklist 1");
        arrayList.add("checklist 2");
        arrayList.add("checklist 3");
 **/

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

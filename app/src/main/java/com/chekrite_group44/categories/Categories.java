/*
 * Date: 2020.4.29
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.categories;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.chekrite_group44.R;

public class Categories extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager viewPager;
    LinearLayout sliderDotview;
    private int dotscount;
    private ImageView[] dots;

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);

        toolbar = (Toolbar) findViewById(R.id.categories_toolbar);
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

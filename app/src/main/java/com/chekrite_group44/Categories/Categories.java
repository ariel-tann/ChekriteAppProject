/*
 * Date: 2020.4.29
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Categories;

import android.os.Bundle;
import android.provider.ContactsContract;
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

import com.chekrite_group44.R;

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

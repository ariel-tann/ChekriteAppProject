/*
 * Date: 2020.5.16
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Categories;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.chekrite_group44.R;

public class Checklist extends AppCompatActivity {
    String selected_category;
    TextView category_test;
    @Override



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);

        Bundle bundle =getIntent().getExtras();

 //       selected_category = getIntent().getStringExtra("category");
        selected_category = "Test static";
        category_test = (TextView) findViewById(R.id.selected_category);
        category_test.setText(selected_category);
    }
}

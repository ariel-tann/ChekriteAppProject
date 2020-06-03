/*
 * Date: 2020.6.2
 * This file is created by Kai.
 * Summary:
 */

/*
 * Date: 2020.5.18
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.NewCheck;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chekrite_group44.Chekrite;
import com.chekrite_group44.Keyboard.KeyboardFragment;
import com.chekrite_group44.R;
import com.chekrite_group44.SelectAssetScreen.SelectTab;
import com.chekrite_group44.DashBoard.DashboardActivity;
import com.chekrite_group44.ChecklistSelection.SignoutActivity;
import com.google.android.material.tabs.TabLayout;


public class NewCheckActivity extends AppCompatActivity
        implements SearchAssetFragment.SearchAssetListener, KeyboardFragment.KeyboardFragmentListener {

    ImageButton back_btn;
    ImageButton logout_btn;
    TextView back;
    TextView toolbar_name;

    ConstraintLayout selectAssetLayout;

    private String screenName = "Select asset";

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    SearchAssetFragment saf = new SearchAssetFragment();
    SelectTab st = new SelectTab();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_check);

        //Set screen color to company's color
        selectAssetLayout = findViewById(R.id.selectAssetScreenLayout);
        selectAssetLayout.setBackgroundColor(Chekrite.getParseColor());

        //Set toolbar color to company's color & toolbar name to Select Asset
        Toolbar toolbar = (Toolbar) findViewById(R.id.SelectScreenToolbar);
        toolbar.setBackgroundColor(Chekrite.getParseColor());
        toolbar_name = findViewById(R.id.toolbar_name);
        toolbar_name.setText(screenName);

        //Back button press to go to Dashboard
        back_btn = findViewById(R.id.back_arrow);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDashboardScreen();
            }
        });
        back = findViewById(R.id.back_text);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewCheckActivity.super.onBackPressed();
            }
        });

        //Set logout button to show profile pic and logout when pressed
        SharedPreferences pref = getSharedPreferences(Chekrite.SHARED_PREFS, Context.MODE_PRIVATE);
        logout_btn = findViewById(R.id.logout_img_btn);
        String profile_link = pref.getString("profile_photo", "");
        if (!profile_link.equals("null")) {
            Glide.with(getApplicationContext()).load(profile_link).apply(RequestOptions.circleCropTransform()).into(logout_btn);
            Log.d("ArielTest", "onCreate: " + profile_link);
        }
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignoutScreen();
            }
        });

        // Create the adapter and viewpager for the three tab sections
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(mViewPager);

        //Set text in tablayout to company's color
        tabLayout.setTabTextColors(Color.parseColor("#ffffff"), Chekrite.getParseColor());
    }

    public void openDashboardScreen() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

    public void openSignoutScreen() {
        Intent intent = new Intent(this, SignoutActivity.class);
        startActivity(intent);
    }

    //handle keyboard pressed from keyboard fragmnent
    @Override
    public void onInputKeyboardSent(CharSequence input) {
        saf.updateEditText(input);
    }

    @Override
    public void onInputSearchAssetSent(CharSequence input) {

    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }


        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_search_asset, container, false);
            return rootView;
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = saf;

                    break;
                case 1:
                    fragment = new SeeFragment();
                    break;
                case 2:
                    fragment = st;
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Search";
                case 1:
                    return "See";
                case 2:
                    return "Select";
            }
            return null;
        }

    }
}

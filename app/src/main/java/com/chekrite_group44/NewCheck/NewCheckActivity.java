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
import com.chekrite_group44.ChecklistSelection.SignoutActivity;
import com.google.android.material.tabs.TabLayout;


public class NewCheckActivity extends AppCompatActivity
        implements SearchAssetFragment.SearchAssetListener, KeyboardFragment.KeyboardFragmentListener,
        SelectAssetCategoryFragment.categoryListener, SelectAssetMakeFragment.makeListener, SelectAssetModelFragment.modelListener{

    private static final String TAG = "New Check Activity";

    ImageButton back_btn;
    ImageButton logout_btn;
    TextView back_txt;
    TextView toolbar_name;

    ConstraintLayout selectAssetLayout;

    private String screenName = "Select asset";

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    SearchAssetFragment searchAssetFragment = new SearchAssetFragment();
    SeeFragment seeFragment = new SeeFragment();
    SelectAssetFragment selectAssetFragment = new SelectAssetFragment();


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

        //Back button & arrow press to go to Dashboard if no fragments are present
        back_btn = findViewById(R.id.back_arrow);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        back_txt = findViewById(R.id.back_text);
        back_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Set logout button to show profile pic and logout when pressed
        SharedPreferences pref = getSharedPreferences(Chekrite.SHARED_PREFS, Context.MODE_PRIVATE);
        logout_btn = findViewById(R.id.logout_img_btn);
        String profile_link = pref.getString("profile_photo", "");
        if (!profile_link.equals("null")) {
            Glide.with(getApplicationContext()).load(profile_link).apply(RequestOptions.circleCropTransform()).into(logout_btn);
            Log.d(TAG, "onCreate: " + profile_link);
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

        //Set search,see,select text in tablayout to company's color if selected, else set text color to white
        tabLayout.setTabTextColors(Color.parseColor("#ffffff"), Chekrite.getParseColor());
    }

    public void openSignoutScreen() {
        Intent intent = new Intent(this, SignoutActivity.class);
        startActivity(intent);
    }

    //handle keyboard pressed from keyboard fragment
    @Override
    public void onInputKeyboardSent(CharSequence input) {
        searchAssetFragment.updateEditText(input);
    }

    @Override
    public void onInputSearchAssetSent(CharSequence input) {

    }

    //changes current fragment to MakeFragment
    @Override
    public void goToMakeFrag() {
        selectAssetFragment.goToNextFragment("make");
    }

    //changes current fragment to ModelFragment
    @Override
    public void goToModelFrag() {
        selectAssetFragment.goToNextFragment("model");

    }

    //changes current fragment to UnitFragment
    @Override
    public void goToUnitFrag() {
        selectAssetFragment.goToNextFragment("unit");

    }

    //if there's fragment in back stack, then back press will display fragment in
    //back stack. If not, goes back to previous activity
    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        for (Fragment frag : fm.getFragments()) {
            if (frag.isVisible()) {
                FragmentManager childFm = frag.getChildFragmentManager();
                if (childFm.getBackStackEntryCount() > 0) {
                    childFm.popBackStack();
                    return;
                }
            }
        }
        super.onBackPressed();
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
                    fragment = searchAssetFragment;

                    break;
                case 1:
                    fragment = seeFragment;
                    break;
                case 2:
                    fragment = selectAssetFragment;
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

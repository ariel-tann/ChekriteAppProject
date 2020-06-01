/*
 * Date: 2020.5.6
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Inspection;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class InspectionPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "Inspection_PagerAdapter";
    ArrayList<Fragment> mFragmentList = new ArrayList<>();
    ArrayList<Integer> mFragmentID = new ArrayList<>();
    int previou_page = 0;
    public InspectionPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    public void addFragment(Fragment fragment, int item_id){

        mFragmentList.add(fragment);
        mFragmentID.add(item_id);
    }
    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}

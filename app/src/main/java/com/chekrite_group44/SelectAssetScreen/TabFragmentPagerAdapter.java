package com.chekrite_group44.SelectAssetScreen;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.chekrite_group44.Keyboard.KeyboardFragment;
import com.chekrite_group44.NewCheck.SearchAssetFragment;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter implements SearchAssetFragment.SearchAssetListener, KeyboardFragment.KeyboardFragmentListener {
    int PAGE_COUNT=3;
    String tabTitles[]=new String[]{"Search","See","Select"};
    Context context;
    private SearchAssetFragment sb = new SearchAssetFragment();

    public TabFragmentPagerAdapter(@NonNull FragmentManager fm, Context context ) {
        super(fm);
        this.context=context;


    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0) {



            return sb;
        }
        else if (position==1)
            return new SelectTab();
        else if (position==2)
            return new SelectTab();
       return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public void onInputKeyboardSent(CharSequence input) {
        sb.updateEditText(input);
    }

    @Override
    public void onInputSearchAssetSent(CharSequence input) {

    }


}

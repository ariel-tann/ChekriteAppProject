/*
 * Date: 2020.6.8
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.NewCheck;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.chekrite_group44.AssetProperties.SelectAssetAssets;
import com.chekrite_group44.AssetProperties.SelectAssetData;
import com.chekrite_group44.HttpRequest.APIsListener;
import com.chekrite_group44.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectAssetFragment extends Fragment implements SelectAssetCategoryFragment.categoryListener, SelectAssetMakeFragment.makeListener,
        SelectAssetModelFragment.modelListener {

    private static final String TAG = "Select Asset Fragment";

    FragmentTransaction fragmentTransaction;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_asset_main, container, false);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.select_asset_framelayout, new SelectAssetCategoryFragment());
        transaction.commit();

        return view;
    }

    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public void goToNextFragment(String fragType){
        addFragment(fragType);
    }

    public void addFragment(String fragType) {
        Fragment fragment;
        if (fragType.equals("make")) {
            fragment = new SelectAssetMakeFragment();
            fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.select_asset_framelayout,fragment,"demofragment").addToBackStack(null).commit();

        } else if (fragType.equals("model")) {
            fragment = new SelectAssetModelFragment();
            fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.select_asset_framelayout, fragment, "demofragment").addToBackStack(null).commit();

        } else if (fragType.equals("unit")) {
            fragment = new SelectAssetUnitFragment();
            fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.select_asset_framelayout, fragment, "demofragment").addToBackStack(null).commit();

        }

    }

    @Override
    public void goToMakeFrag() { }

    @Override
    public void goToModelFrag() { }

    @Override
    public void goToUnitFrag() { }
}

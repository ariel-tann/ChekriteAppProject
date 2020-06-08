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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chekrite_group44.AssetProperties.SelectAssetAssets;
import com.chekrite_group44.AssetProperties.SelectAssetData;


import com.chekrite_group44.R;

import java.util.ArrayList;

public class SelectAssetMakeFragment extends Fragment implements SelectAssetAdapter.OnAssetListener{

    private static final String TAG = "Select Asset Make Fragment";

    private static ArrayList<SelectAssetData> dataMakeList = new ArrayList<>();
    public static ArrayList<SelectAssetData> filteredModelList = new ArrayList<>();
    private static ArrayList<SelectAssetAssets> assetsMakeList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_asset_make, container, false);

        //prevent duplicates in filtered list
        if(filteredModelList.size() != 0){
            filteredModelList.clear();
        }

        //get filtered list from Select Category Fragment
        dataMakeList = SelectAssetCategoryFragment.filteredMakeList;

        mRecyclerView = view.findViewById(R.id.make_recyclerview);
        recyclerBuild();

        return view;
    }

    //handles building the recycler view
    public void recyclerBuild() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mAdapter = new SelectAssetAdapter(dataMakeList, assetsMakeList, this.getContext(), this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    //save select Asset data into a filtered list and call listener to go to next fragment
    @Override
    public void onAssetClick(int position) {
        int tempId = dataMakeList.get(position).getId();
        String tempMake = dataMakeList.get(position).getMake();
        String tempModel = dataMakeList.get(position).getModel();
        String tempCat = dataMakeList.get(position).getCategory();
        int tempOnTheFlyAssetsEnabled = dataMakeList.get(position).getOnTheFlyAssetsEnabled();
        ArrayList<SelectAssetAssets> tempAsset = dataMakeList.get(position).getAssets();
        filteredModelList.add(new SelectAssetData(tempId, tempMake, tempModel, tempCat, tempOnTheFlyAssetsEnabled, tempAsset));
        listener.goToModelFrag();


    }

    private makeListener listener;


    public interface makeListener {
        void goToModelFrag ();
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof makeListener) {
            listener = (makeListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement make listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


}
/*
 * Date: 2020.6.8
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.NewCheck;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chekrite_group44.AssetProperties.SelectAssetAssets;
import com.chekrite_group44.AssetProperties.SelectAssetData;
import com.chekrite_group44.ChecklistSelection.CategoriesActivity;
import com.chekrite_group44.R;

import java.util.ArrayList;

public class SelectAssetUnitFragment extends Fragment implements SelectAssetAdapter.OnAssetListener{

    private static final String TAG = "Select Asset Unit Fragment";

    private static ArrayList<SelectAssetData> dataUnitList = new ArrayList<>();
    private static ArrayList<SelectAssetAssets> assetUnitList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_asset_unit, container, false);

        //get filtered list from Select Model Fragment
        assetUnitList = SelectAssetModelFragment.assetsModelList;

        mRecyclerView = view.findViewById(R.id.unit_recyclerview);
       recyclerBuild();

        return view;
    }

    //handles building the recycler view
    public void recyclerBuild() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mAdapter = new SelectAssetAdapter(dataUnitList, assetUnitList, this.getContext(), this, 4);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    //Get data from SelectAssetAssets and pushes data to next activity
    @Override
    public void onAssetClick(int position) {
        Intent intent = new Intent(getActivity(), CategoriesActivity.class);
        intent.putExtra("asset_id", assetUnitList.get(position).getId());
        intent.putExtra("unit_number", assetUnitList.get(position).getUnitNumber());
        intent.putExtra("make", assetUnitList.get(position).getMake());
        intent.putExtra("model", assetUnitList.get(position).getModel());
        intent.putExtra("photo", assetUnitList.get(position).getPhoto());
        startActivity(intent);

    }


    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}

/*
 * Date: 2020.6.8
 * This file is created by Ariel.
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chekrite_group44.AssetProperties.SelectAssetAssets;
import com.chekrite_group44.AssetProperties.SelectAssetData;


import com.chekrite_group44.HttpRequest.APIs;
import com.chekrite_group44.HttpRequest.APIsListener;
import com.chekrite_group44.HttpRequest.APIsTask;
import com.chekrite_group44.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectAssetCategoryFragment extends Fragment implements SelectAssetAdapter.OnAssetListener{

    private static final String TAG = "Select Asset Category Fragment";

    private static ArrayList<SelectAssetData> dataCategoryList = new ArrayList<>();
    public static ArrayList<SelectAssetData> filteredMakeList = new ArrayList<>();
    private static ArrayList<SelectAssetAssets> assetsCategoryList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    APIsListener AssetsListener = new APIsListener() {
        @Override
        public void API_Completed(JSONObject jsonObject) {
            try {
                String status = (String) jsonObject.get("status");
                String message = (String) jsonObject.get("message");
                if (status.equals("success")) {
                    Log.d(TAG, "search asset success: " + message);
                    JSONArray data = jsonObject.getJSONArray("data");
                    if (data != null) {
                        Log.d(TAG, "data not null");
                        dataCategoryList.clear();

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject object = data.getJSONObject(i);
                            int id = object.getInt("id");
                            String make = object.getString("make");
                            String model = object.getString("model");
                            String category = object.getString("category");
                            int onTheFlyAssetsEnabled = object.getInt("on_the_fly_assets_enabled");
                            JSONArray assets = object.getJSONArray("asset");
                            if (assets != null) {
                                Log.d(TAG, "asset data not null");
                                for (int j = 0; j < assets.length(); j++) {
                                    JSONObject assetData = assets.getJSONObject(j);
                                    int assetId = assetData.getInt("id");
                                    String assetUnitNumber = assetData.getString("unit_number");
                                    String assetMake = assetData.getString("make");
                                    String assetModel = assetData.getString("model");
                                    String assetPhotoUrl = assetData.getString("photo");
                                    assetsCategoryList.add(new SelectAssetAssets(assetId, assetUnitNumber, assetMake, assetModel,
                                            assetPhotoUrl));
                                }

                            } else {
                                Log.d(TAG, "asset data null");
                            }
                            dataCategoryList.add(new SelectAssetData(id, make, model, category,
                                    onTheFlyAssetsEnabled, assetsCategoryList));

                            assetsCategoryList.clear();
                            mAdapter.notifyDataSetChanged();
                        }


                    } else {
                        Log.d(TAG, "data null");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_asset_category, container, false);
        Log.d(TAG, "in fragment");
        new APIsTask(AssetsListener).execute("GET", APIs.ASSETS,"","");

        //prevent duplicates in filtered list
        if(filteredMakeList.size() != 0){
            filteredMakeList.clear();
            }

        mRecyclerView = view.findViewById(R.id.category_recyclerview);
        recyclerBuild();

        return view;
    }

    //handles building the recycler view
    public void recyclerBuild() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mAdapter = new SelectAssetAdapter(dataCategoryList, assetsCategoryList, this.getContext(), this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }


    //save select Asset data into a filtered list and call listener to go to next fragment
    @Override
    public void onAssetClick(int position) {
        int tempId = dataCategoryList.get(position).getId();
        String tempMake = dataCategoryList.get(position).getMake();
        String tempModel = dataCategoryList.get(position).getModel();
        String tempCat = dataCategoryList.get(position).getCategory();
        int tempOnTheFlyAssetsEnabled = dataCategoryList.get(position).getOnTheFlyAssetsEnabled();
        ArrayList<SelectAssetAssets> tempAsset = dataCategoryList.get(position).getAssets();
        filteredMakeList.add(new SelectAssetData(tempId, tempMake, tempModel, tempCat, tempOnTheFlyAssetsEnabled, tempAsset));
        listener.goToMakeFrag();

    }

    private categoryListener listener;

    public interface categoryListener {
        void goToMakeFrag();
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof categoryListener) {
            listener = (categoryListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement category listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

}

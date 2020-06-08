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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chekrite_group44.AssetProperties.SelectAssetAssets;
import com.chekrite_group44.AssetProperties.SelectAssetData;
import com.chekrite_group44.R;

import java.util.ArrayList;

public class SelectAssetModelFragment extends Fragment implements SelectAssetAdapter.OnAssetListener{

    private static final String TAG = "Select Asset Model Fragment";

    private static ArrayList<SelectAssetData> dataModelList = new ArrayList<>();
    public static ArrayList<SelectAssetAssets> assetsModelList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_asset_model, container, false);

        //prevent duplicates in filtered list
        if(assetsModelList.size() != 0){
            assetsModelList.clear();
        }

        //get filtered list from Select Make Fragment
        dataModelList = SelectAssetMakeFragment.filteredModelList;

        mRecyclerView = view.findViewById(R.id.model_recyclerview);
        recyclerBuild();

        return view;
    }

    //handles building the recycler view
    public void recyclerBuild() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mAdapter = new SelectAssetAdapter(dataModelList, assetsModelList, this.getContext(), this, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }



    //save SelectAssetAsset into a filtered list and call listener to go to next fragment
    @Override
    public void onAssetClick(int position) {
        ArrayList<SelectAssetAssets> tempAsset = dataModelList.get(position).getAssets();
        for(int i = 0; i<tempAsset.size(); i++){
            int tempId = tempAsset.get(i).getId();
            String tempUnitNumber = tempAsset.get(i).getUnitNumber();
            String tempMake = tempAsset.get(i).getMake();
            String tempModel = tempAsset.get(i).getModel();
            String tempPhoto = tempAsset.get(i).getPhoto();
            assetsModelList.add(new SelectAssetAssets(tempId, tempUnitNumber, tempMake, tempModel, tempPhoto));
        }
        listener.goToUnitFrag();
    }

    private modelListener listener;

    public interface modelListener {
        void goToUnitFrag();
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof modelListener) {
            listener = (modelListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement model listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

}


/*
 * Date: 2020.5.16
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.SelectAssetScreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chekrite_group44.Asset_Properties.Asset_Classes;
import com.chekrite_group44.Asset_Properties.Select_Asset_Classes;
import com.chekrite_group44.Http_Request.APIs;
import com.chekrite_group44.Http_Request.APIsListener;
import com.chekrite_group44.Http_Request.APIsTask;
import com.chekrite_group44.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class VehicleScreen extends Fragment {
    View rootView;
    RecyclerView selectTab;
    ArrayList<Asset_Classes> asset_classes=new ArrayList<>();
    int position;
    Intent intent;
    APIsListener AssetsListener = new APIsListener() {
        @Override
        public void API_Completed(JSONObject jsonObject) {
            try {

                String status = (String) jsonObject.get("status");
                if(status.equals("success")){
                    Log.d("SHREY-ST", "GET assets success");
                    asset_classes = new Select_Asset_Classes(jsonObject).getAsset_classes();
                    Log.d("SHREY-ST","number of asset classes: "+asset_classes.size());
                    Log.d("SHREY-ST",""+asset_classes.get(0).getAssets().get(0).getUnit_number());
                }else{
                    // TODO logout fail
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.select_tab_fragment, container ,false);
        selectTab = rootView.findViewById(R.id.select_tab_rv);
        // linearLayoutManager=new LinearLayoutManager(getActivity());

        new APIsTask(AssetsListener, getContext()).execute("GET", APIs.ASSETS,"","");
        selectTab.setHasFixedSize(true);
        selectTab.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        selectTab.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        selectTab.setAdapter(new VehicleTabAdapter(this.getActivity(),asset_classes));
        if(asset_classes!=null){
            selectTab.setAdapter(new VehicleTabAdapter(this.getActivity(),asset_classes));
        }
        return rootView;
    }

}



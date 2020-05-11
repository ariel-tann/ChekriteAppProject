/*
 * Date: 2020.5.4
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.SelectAssetScreen;

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
import java.util.List;
import java.util.concurrent.locks.LockSupport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SelectTab extends Fragment {
    View rootView;
    RecyclerView selectTab;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Asset_Classes> asset_classes;
    public SelectTab() {
    }
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
        rootView = inflater.inflate(R.layout.fragment_select_tab, container, false);
        selectTab = rootView.findViewById(R.id.select_tab_recyclerview);
       // linearLayoutManager=new LinearLayoutManager(getActivity());
        new APIsTask(AssetsListener, getContext()).execute("GET", APIs.ASSETS,"","");
        selectTab.setHasFixedSize(true);
        selectTab.setAdapter(new SelectTabAdapter(getActivity().getApplicationContext(),asset_classes));
        selectTab.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(asset_classes!=null){
            selectTab.setAdapter(new SelectTabAdapter(getActivity().getApplicationContext(),asset_classes));
        }
        return rootView;
    }
}

/*
 * Date: 2020.5.16
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.SelectAssetScreen;

import android.content.Context;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chekrite_group44.Asset_Properties.Asset_Classes;
import com.chekrite_group44.R;


import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VehicleTabAdapter extends RecyclerView.Adapter<VehicleTabAdapter.ViewHolder> {
    Context context;
    ArrayList<Asset_Classes> asset_classes;
    TextView listview;
    public VehicleTabAdapter(Context context , ArrayList<Asset_Classes> asset_classes){
        this.context=context;
        this.asset_classes=asset_classes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.select_tab_layout,null);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HandlePosition handlePosition=new HandlePosition();
        position=handlePosition.getPosition();
        try {
            Log.d("TEST", "INSIDE onBindViewHolder");
            listview.setText(asset_classes.get(position).getAssets().get(position).getMake());
//            holder.setItemClickListener(new ItemClickListener() {
//                @Override
//                public void onItemClick(View view, int position) {
//                    listview.setText(asset_classes.get(position).getAssets().get(position).getMake());
//                    Log.d("CLICK"," :"+position);
//                }
//            });
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public int getItemCount() {
        System.out.println("Inside get item count :" + asset_classes);
        int size;
        if(asset_classes!=null)
        {
            size=asset_classes.size();
        }else{
            size=0;
        }
        return size;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView listview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listview=itemView.findViewById(R.id.textview_selectab);
        }
    }
}

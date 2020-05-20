/*
 * Date: 2020.5.4
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.SelectAssetScreen;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chekrite_group44.Asset_Properties.Asset_Classes;
import com.chekrite_group44.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class SelectTabAdapter extends RecyclerView.Adapter<SelectTabAdapter.ViewHolder>{
    Context context;
    ArrayList<Asset_Classes> asset_classes=new ArrayList<Asset_Classes>();
    TextView listview;
    Button button;
    int page=1;



    public SelectTabAdapter(Context context, ArrayList<Asset_Classes> asset_classes)
    {
        Log.d("SHREY","Isnide selecttabadapter");
        this.context=context;
        this.asset_classes=asset_classes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("TEST","INSIDE onCreateViewHolder"+asset_classes.get(0).getAssets().get(0).getUnit_number());
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.select_tab_layout,null);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final HandlePosition object=new HandlePosition();
        try {
            Log.d("TEST", "Inside onBindViewHolder"+page);
            listview.setText(asset_classes.get(position).getAssets().get(position).getModel());
            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Log.d("TEST", "psoitionr"+position);
//                    object.setPosition(position);
//                    AppCompatActivity appCompatActivity=(AppCompatActivity)view.getContext();
//                    VehicleScreen vehicleScreen=new VehicleScreen();
//                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_selectassetscreen,vehicleScreen)
//                            .addToBackStack(null).commit();
                }

            });
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

    public class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {
        ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("TEST","INSIDE ViewHolder constructor");
            listview=itemView.findViewById(R.id.textview_selectab);
          //  button=itemView.findViewById(R.id.button);
             itemView.setOnClickListener(this);
        }
        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener=itemClickListener;
        }
        @Override
        public void onClick(View v) {
                itemClickListener.onItemClick(v,getLayoutPosition());

        }
    }
}


/*
 * Date: 2020.5.4
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.SelectAssetScreen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chekrite_group44.Asset_Properties.Asset_Classes;
import com.chekrite_group44.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SelectTabAdapter extends RecyclerView.Adapter<SelectTabAdapter.ViewHolder> {
    Context context;
    ArrayList<Asset_Classes> asset_classes=new ArrayList<>();
    TextView listview;
    Button button;
    public SelectTabAdapter(Context context, ArrayList<Asset_Classes> asset_classes)
    {
        Log.d("SHREY","Isnide selecttabadapter");
        this.context=context;
        this.asset_classes=asset_classes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("TEST","INSIDE onCreateViewHolder");
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_select_tab_fragment,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("TEST","INSIDE onBindViewHolder");
        listview.setText(asset_classes.get(0).getAssets().get(0).getUnit_number());

    }


    @Override
    public int getItemCount() {
        try {
            System.out.println("Inside get item count :" + asset_classes.get(0).getAssets().get(0).getUnit_number());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("TEST","INSIDE ViewHolder constructor");
            listview=itemView.findViewById(R.id.textView);
            button=itemView.findViewById(R.id.button);
        }
    }
}


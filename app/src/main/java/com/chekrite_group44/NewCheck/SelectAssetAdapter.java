/*
 * Date: 2020.6.8
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.NewCheck;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.chekrite_group44.AssetProperties.SelectAssetAssets;
import com.chekrite_group44.AssetProperties.SelectAssetData;


import com.chekrite_group44.R;

import java.util.ArrayList;

public class SelectAssetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<SelectAssetData> dataList;
    private ArrayList<SelectAssetAssets> assetsList;

    Context context;
    private OnAssetListener mOnAssetListener;
    private int fragType;
    private static int TYPE_CATEGORY = 1;
    private static int TYPE_MAKE = 2;
    private static int TYPE_MODEL = 3;
    private static int TYPE_UNIT = 4;




    @Override
    public int getItemViewType(int position) {
        if (fragType == TYPE_CATEGORY) {
            return TYPE_CATEGORY;

        } else if (fragType == TYPE_MAKE){
            return TYPE_MAKE;

        } else if (fragType == TYPE_MODEL) {
            return TYPE_MODEL;

        } else {
            return TYPE_UNIT;
        }
    }


    public static class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView category;
        TextView numOfAssets;
        OnAssetListener onAssetListener;

        public CategoryViewHolder(@NonNull View itemView, OnAssetListener onAssetListener) {
            super(itemView);
            category = itemView.findViewById(R.id.select_asset_text);
            numOfAssets = itemView.findViewById(R.id.num_assets);
            this.onAssetListener = onAssetListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onAssetListener.onAssetClick(getAdapterPosition());
        }

        private void setCategoryDetails(SelectAssetData mCategory) {
            category.setText(mCategory.getCategory());
            numOfAssets.setText(Integer.toString(mCategory.getAssets().size()));
        }
    }

    class MakeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView make;
        TextView numOfAssets;
        OnAssetListener onAssetListener;

        public MakeViewHolder(@NonNull View itemView, OnAssetListener onAssetListener) {
            super(itemView);
            make = itemView.findViewById(R.id.select_asset_text);
            numOfAssets = itemView.findViewById(R.id.num_assets);
            this.onAssetListener = onAssetListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onAssetListener.onAssetClick(getAdapterPosition());
        }

        private void setMakeDetails(SelectAssetData mMake) {
            make.setText(mMake.getMake());
            numOfAssets.setText(Integer.toString(mMake.getAssets().size()));

        }

    }

    class ModelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView model;
        TextView numOfAssets;
        OnAssetListener onAssetListener;

        public ModelViewHolder(@NonNull View itemView, OnAssetListener onAssetListener) {
            super(itemView);
            model = itemView.findViewById(R.id.select_asset_text);
            numOfAssets = itemView.findViewById(R.id.num_assets);
            this.onAssetListener = onAssetListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onAssetListener.onAssetClick(getAdapterPosition());
        }

        private void setModelDetails(SelectAssetData mModel) {
            model.setText(mModel.getModel());
            numOfAssets.setText(Integer.toString(mModel.getAssets().size()));

        }

    }

    class UnitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView unit;
        TextView numOfAssets;
        OnAssetListener onAssetListener;

        public UnitViewHolder(@NonNull View itemView, OnAssetListener onAssetListener) {
            super(itemView);
            unit = itemView.findViewById(R.id.select_asset_text);
            numOfAssets = itemView.findViewById(R.id.num_assets);
            this.onAssetListener = onAssetListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onAssetListener.onAssetClick(getAdapterPosition());
        }

        private void setUnitDetails(SelectAssetAssets mUnit) {
            unit.setText(mUnit.getUnitNumber());
        }

    }


    public SelectAssetAdapter(ArrayList<SelectAssetData> selectAssetList, ArrayList<SelectAssetAssets> assetList, Context context_,
                              OnAssetListener onAssetListener, int frag) {
        dataList = selectAssetList;
        this.assetsList = assetList;
        context = context_;
        this.mOnAssetListener = onAssetListener;
        this.fragType = frag;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_CATEGORY) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_select_asset, parent, false);
            CategoryViewHolder categoryViewHolder = new CategoryViewHolder(v, mOnAssetListener);
            return categoryViewHolder;

        } else if (viewType == TYPE_MAKE){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_select_asset, parent, false);
            MakeViewHolder makeViewHolder = new MakeViewHolder(v, mOnAssetListener);
            return makeViewHolder;

        } else if (viewType == TYPE_MODEL){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_select_asset, parent, false);
            ModelViewHolder modelViewHolder = new ModelViewHolder(v, mOnAssetListener);
            return modelViewHolder;
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_select_asset, parent, false);
            UnitViewHolder unitViewHolder = new UnitViewHolder(v, mOnAssetListener);
            return unitViewHolder;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_CATEGORY) {
            ((CategoryViewHolder) holder).setCategoryDetails(dataList.get(position));

        } else if (getItemViewType(position) == TYPE_MAKE){
            ((MakeViewHolder) holder).setMakeDetails(dataList.get(position));

        } else if (getItemViewType(position) == TYPE_MODEL){
            ((ModelViewHolder) holder).setModelDetails(dataList.get(position));

        } else {
            ((UnitViewHolder) holder).setUnitDetails(assetsList.get(position));

        }

    }


    public interface OnAssetListener {
        void onAssetClick(int position);
    }

    @Override
    public int getItemCount() {
        if (fragType == TYPE_UNIT){
            return assetsList.size();
        } else {
            return dataList.size();
        }
    }
}
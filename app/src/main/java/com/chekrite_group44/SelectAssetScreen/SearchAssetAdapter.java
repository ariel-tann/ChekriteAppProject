/*
 * Date: 2020.5.18
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.SelectAssetScreen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import com.chekrite_group44.R;

public class SearchAssetAdapter extends RecyclerView.Adapter<SearchAssetAdapter.SearchAssetViewHolder> {

    private static final String TAG = "SearchAssetAdapter";
    private ArrayList<SearchAssetItems> mSearchAssetList;
    private OnAssetListener mOnAssetListener;

    Context context;

    public static class SearchAssetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView assetPhoto;
        public TextView unitNumber;
        public TextView makeAndModel;
//        public ImageView getImage(){ return this.assetPhoto;}
        OnAssetListener onAssetListener;

        public SearchAssetViewHolder(@NonNull View itemView, OnAssetListener onAssetListener) {
            super(itemView);
            assetPhoto = (ImageView) itemView.findViewById(R.id.assetPhoto);
            unitNumber = itemView.findViewById(R.id.unitNumber);
            makeAndModel = itemView.findViewById(R.id.makeAndModel);

            //to handle clicks in recycle view
            this.onAssetListener = onAssetListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onAssetListener.onAssetClick(getAdapterPosition());
        }
    }

    public SearchAssetAdapter(ArrayList<SearchAssetItems> searchAssetList, Context context_, OnAssetListener onAssetListener) {
        this.mSearchAssetList = searchAssetList;
        context = context_;
        this.mOnAssetListener = onAssetListener;
    }

    @NonNull
    @Override
    public SearchAssetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_asset_layout, parent, false);
        SearchAssetViewHolder searchAssetVH = new SearchAssetViewHolder(v, mOnAssetListener);
        return searchAssetVH;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAssetViewHolder holder, int position) {
        final SearchAssetItems currentItem = mSearchAssetList.get(position);
        holder.unitNumber.setText(currentItem.getUnitNumber());
        String makeAndModel = currentItem.getMake() + " " + currentItem.getModel();
        holder.makeAndModel.setText(makeAndModel);
        Glide.with(context).load(currentItem.getPhoto()).apply(RequestOptions.circleCropTransform())
                .diskCacheStrategy(DiskCacheStrategy.ALL).fitCenter().into(holder.assetPhoto);
    }

    @Override
    public int getItemCount() {
        return mSearchAssetList.size();
    }

    public interface OnAssetListener {
        void onAssetClick(int position);
    }

}

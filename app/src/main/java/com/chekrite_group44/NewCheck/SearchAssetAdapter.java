/*
 * Date: 2020.6.2
 * This file is created by Kai.
 * Summary:
 */

/*
 * Date: 2020.5.18
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.NewCheck;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import com.chekrite_group44.AssetProperties.SearchAssetItems;
import com.chekrite_group44.Chekrite;
import com.chekrite_group44.R;

public class SearchAssetAdapter extends RecyclerView.Adapter<SearchAssetAdapter.SearchAssetViewHolder> {

    private static final String TAG = "SearchAssetAdapter";
    private static String editText ;
    private ArrayList<SearchAssetItems> mSearchAssetList;
    private OnAssetListener mOnAssetListener;
//     String COLOR = "";

    Context context;

    public static class SearchAssetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView asset_photo;
        public TextView unit_number;
        public TextView make_and_model;
//        public ImageView getImage(){ return this.asset_photo;}
        OnAssetListener onAssetListener;
//        String COLOR = "";

        public SearchAssetViewHolder(@NonNull View itemView, OnAssetListener onAssetListener) {
            super(itemView);
            asset_photo = (ImageView) itemView.findViewById(R.id.assetPhoto);
            unit_number = itemView.findViewById(R.id.unitNumber);
            make_and_model = itemView.findViewById(R.id.makeAndModel);

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_asset, parent, false);
        SearchAssetViewHolder searchAssetVH = new SearchAssetViewHolder(v, mOnAssetListener);
        return searchAssetVH;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAssetViewHolder holder, int position) {
        final SearchAssetItems currentItem = mSearchAssetList.get(position);
        String unitNumber = currentItem.getUnitNumber();
        String makeAndModel = currentItem.getMake() + " " + currentItem.getModel();

        holder.unit_number.setText(unitNumber);
        holder.make_and_model.setText(makeAndModel);
        Glide.with(context).load(currentItem.getPhoto()).centerCrop().apply(RequestOptions.circleCropTransform())
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.asset_photo);


        //Check if unit number contains search query. If true, colors the letters
        if (unitNumber.contains(editText)) {
            holder.unit_number.setText(setTextColor(unitNumber));

        } else {
            holder.unit_number.setTextColor(Color.parseColor("#7f7f7f"));

        }

        //Check if make and model contains search query. If true, colors the letters
        if (makeAndModel.toUpperCase().contains(editText)) {

            holder.make_and_model.setText(setTextColor(makeAndModel));
        } else {

            holder.make_and_model.setTextColor(Color.parseColor("#7f7f7f"));
        }

    }


    @Override
    public int getItemCount() {
        return mSearchAssetList.size();
    }

    //Gets search query from SearchAssetFragment and stores it in global variable
    public void filterList(String text) {
        editText = text;
        notifyDataSetChanged();
    }

    public interface OnAssetListener {
        void onAssetClick(int position);
    }

    //If text contains search query, colors the letters matching the query
    public SpannableString setTextColor (String text) {
        SpannableString ss = new SpannableString(text);
        String upperCaseText = text.toUpperCase();
        ForegroundColorSpan companyColor = new ForegroundColorSpan(Chekrite.getParseColor());
        int textSize = editText.length();
        ss.setSpan(companyColor, upperCaseText.indexOf(editText),
                upperCaseText.indexOf(editText)+textSize, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return ss;
    }

}

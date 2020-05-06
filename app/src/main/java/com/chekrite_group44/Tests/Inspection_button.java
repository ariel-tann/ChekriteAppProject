/*
 * Date: 2020.5.6
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Tests;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chekrite_group44.Asset_Properties.Inspection_checklist_item;
import com.chekrite_group44.R;

public class Inspection_button extends Fragment {
    private ImageButton mbtn_pass;
    private ImageButton mbtn_fail;
    private Inspection_checklist_item mItem;
    TextView mDescription;
    TextView mTitle;
    ProgressBar mProgressBar;
    int total_items;
    int position;
    public Inspection_button(Inspection_checklist_item item, int total_items,int position, InspectionListener listener) {
        mItem = item;
        this.total_items = total_items;
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.inspection_btn,container, false);
        // set Description
        mDescription = view.findViewById(R.id.item_description);
        mDescription.setText(mItem.getDescription());
        mTitle = view.findViewById(R.id.item_title);
        mTitle.setText(mItem.getName());
        mProgressBar = view.findViewById(R.id.inspection_progress);
        mProgressBar.setProgress((int)(((double)position+1)/(double)total_items*100));


        mbtn_pass = view.findViewById(R.id.btn_pass);
        mbtn_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO　call response api and navigate to next fragment

            }
        });
        mbtn_fail = view.findViewById(R.id.btn_fail);
        mbtn_fail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO　call response api and navigate to next fragment

            }
        });
        return view;
    }
}

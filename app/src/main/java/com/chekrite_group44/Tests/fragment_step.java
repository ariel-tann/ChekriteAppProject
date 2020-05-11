/*
 * Date: 2020.5.6
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Tests;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chekrite_group44.Asset_Properties.Control_Type;
import com.chekrite_group44.Asset_Properties.Inspection_checklist_item;
import com.chekrite_group44.MainActivity;
import com.chekrite_group44.R;

public class fragment_step extends Fragment {
    private Button mStep;
    private Inspection_checklist_item mItem;
    TextView mDescription;
    TextView mTitle;
    ProgressBar mProgressBar;
    int total_items;
    int position;
    private static final String TAG = "Inspection_step";
    private InspectionListener mlistener;
    Long start;
    public fragment_step(Inspection_checklist_item item, int total_items, int position, InspectionListener listener) {
        mItem = item;
        this.total_items = total_items;
        this.position = position;
        this.mlistener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inspection_step,container, false);
        // set Description
        mDescription = view.findViewById(R.id.item_description);
        mDescription.setText(mItem.getDescription());
        mTitle = view.findViewById(R.id.item_title);
        mTitle.setText(mItem.getName());
        mProgressBar = view.findViewById(R.id.inspection_progress);
        mProgressBar.setProgress((int)(((double)position+1)/(double)total_items*100));
        // get item start timestamp
        start = System.currentTimeMillis();
        mStep = view.findViewById(R.id.btn_step);
        // set button features
        String btn1_color = mItem.getControl().getButtons().get(0).getColor();
        String lbl1 = mItem.getControl().getButtons().get(0).getLabel();
        mStep.setText(lbl1);
        mStep.setTextSize(20);
        mStep.setAllCaps(false);
        mStep.setTextColor(view.getContext().getColor(R.color.white));
        mStep.setBackgroundColor(Color.parseColor(btn1_color));

        mStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long end = System.currentTimeMillis();
                mlistener.Completed(Control_Type.BUTTONS,0,0,
                        start, end);
            }
        });
        return view;
    }
}

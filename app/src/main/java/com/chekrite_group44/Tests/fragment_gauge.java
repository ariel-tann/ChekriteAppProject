/*
 * Date: 2020.5.10
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Tests;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chekrite_group44.Asset_Properties.Control_Type;
import com.chekrite_group44.Asset_Properties.Inspection_band;
import com.chekrite_group44.Asset_Properties.Inspection_checklist_item;
import com.chekrite_group44.Asset_Properties.Inspection_gauge;
import com.chekrite_group44.R;

import java.util.ArrayList;

public class fragment_gauge extends Fragment implements SeekBar.OnSeekBarChangeListener,
        View.OnClickListener{
    private static final String TAG = "fragment_gauge";
    private Inspection_checklist_item mItem;
    TextView mDescription;
    TextView mTitle;
    ProgressBar mProgressBar;
    int total_items;
    int position;
    Long start;
    InspectionListener mlistener;
    LinearLayout l_layout;
    int l_width;
    int l_height;
    View mView;
    int current_value = 0;
    int current_status = 0;

    public fragment_gauge(Inspection_checklist_item item, int total_items, int position, InspectionListener listener) {
        mItem = item;
        this.total_items = total_items;
        this.position = position;
        mlistener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.inspection_gauge, container, false);
        // set Description
        mDescription = mView.findViewById(R.id.item_description);
        mDescription.setText(mItem.getDescription());
        mTitle = mView.findViewById(R.id.item_title);
        mTitle.setText(mItem.getName());
        mProgressBar = mView.findViewById(R.id.inspection_progress);
        mProgressBar.setProgress((int)(((double)position+1)/(double)total_items*100));
        // get item start timestamp
        start = System.currentTimeMillis();
        //
        l_layout = mView.findViewById(R.id.l_layout);
        // set a Listener to monitor when layout finished creating
        ViewTreeObserver vto = l_layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                l_layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                l_width  = l_layout.getMeasuredWidth();
                l_height = l_layout.getMeasuredHeight();
                seekbarCreate();
            }
        });

        //
        Button btn_done = mView.findViewById(R.id.btn_done);
        btn_done.setOnClickListener(this);
        return mView;
    }
    private void seekbarCreate(){
        Inspection_gauge gauge = mItem.getControl().getGauges().get(0);
        GuageView seekBar = new GuageView(mView.getContext(), gauge);

        seekBar.setLayoutParams(new LinearLayout.LayoutParams(l_width,l_height));
        seekBar.setSplitTrack(true);
        seekBar.setOnSeekBarChangeListener(this);
        l_layout.addView(seekBar);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Inspection_gauge gauge = mItem.getControl().getGauges().get(0);
        long ratio = Math.round(gauge.getUpper()/gauge.getMarks_count());
        current_value = progress * (int)ratio;
        Log.d(TAG, "current_value: "+current_value);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View v) {
        // calling inspectionListener in inspection main java
        Long end = System.currentTimeMillis();
        mlistener.Completed(Control_Type.GAUGE, 0, current_value,
                "", start, end);
    }
}

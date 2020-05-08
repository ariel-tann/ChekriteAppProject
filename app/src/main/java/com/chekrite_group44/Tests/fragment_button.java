/*
 * Date: 2020.5.6
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Tests;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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

public class fragment_button extends Fragment {
    private Button mbtn_1;
    private Button mbtn_2;
    private Inspection_checklist_item mItem;
    TextView mDescription;
    TextView mTitle;
    ProgressBar mProgressBar;
    int total_items;
    int position;
    Long start;
    InspectionListener mlistener;
    public fragment_button(Inspection_checklist_item item, int total_items, int position, InspectionListener listener) {
        mItem = item;
        this.total_items = total_items;
        this.position = position;
        mlistener = listener;
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
        // get item start timestamp
        start = System.currentTimeMillis();
        String btn1_color = mItem.getControl().getButtons().get(0).getColor();
        String lbl1 = mItem.getControl().getButtons().get(0).getLabel();
        String btn2_color = mItem.getControl().getButtons().get(1).getColor();
        String lbl2 = mItem.getControl().getButtons().get(1).getLabel();
        mbtn_1 = view.findViewById(R.id.inspection_btn1);
        mbtn_1.setText(lbl1);
        mbtn_1.setTextSize(20);
        mbtn_1.setAllCaps(false);
        mbtn_1.setTextColor(view.getContext().getColor(R.color.white));
        // set background color and stroke color
        mbtn_1.setBackground(drawCircle(Color.parseColor(btn1_color),
                view.getContext().getColor(R.color.dark_gray)));
        mbtn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO　call response api and navigate to next fragment
                Long end = System.currentTimeMillis();
                mlistener.Completed(Control_TYPE.button,0,0,
                        start, end);
            }
        });
        mbtn_2 = view.findViewById(R.id.inspection_btn2);
        mbtn_2.setText(lbl2);
        mbtn_2.setTextSize(20);
        mbtn_2.setAllCaps(false);
        mbtn_2.setTextColor(view.getContext().getColor(R.color.white));
        // set background color and stroke color
        mbtn_2.setBackground(drawCircle(Color.parseColor(btn2_color),
                view.getContext().getColor(R.color.dark_gray)));
        mbtn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO　call response api and navigate to next fragment
                Long end = System.currentTimeMillis();
                mlistener.Completed(Control_TYPE.button,1,0,
                        start, end);
            }
        });
        return view;
    }
    private static GradientDrawable drawCircle(int backgroundColor, int borderColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setCornerRadii(new float[]{0, 0, 0, 0, 0, 0, 0, 0});
        shape.setColor(backgroundColor);
        shape.setStroke(25, borderColor);
        return shape;
    }
}

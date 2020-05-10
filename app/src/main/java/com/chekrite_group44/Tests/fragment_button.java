/*
 * Date: 2020.5.6
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Tests;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chekrite_group44.Asset_Properties.Inspection_checklist_item;
import com.chekrite_group44.R;

import java.util.ArrayList;

public class fragment_button extends Fragment implements View.OnClickListener{
    private Inspection_checklist_item mItem;
    TextView mDescription;
    TextView mTitle;
    ProgressBar mProgressBar;
    int total_items;
    int position;
    Long start;
    InspectionListener mlistener;
    int col = 2; // define number of buttons should be in one row

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
        //
        TableLayout tableLayout = view.findViewById(R.id.insp_t_layout);
        // create 2 rows
        ArrayList<TableRow> rows = new ArrayList<>();
        // num of rows
        int num_btn = mItem.getControl().getButtons().size();
        // num of column

        int num_rows = (int)Math.ceil(num_btn/(double)col);

        for (int i = 0; i< num_rows; i++){
            TableRow row = new TableRow(view.getContext());
            row.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            row.setGravity(Gravity.CENTER);
            rows.add(i, row);
            tableLayout.addView(row);
        }
        // use TableRow LayoutParams to set width and height
        TableRow.LayoutParams layoutParams =
                new TableRow.LayoutParams(300, 300);
        layoutParams.setMargins(50, 50, 50, 50);

        // add 2 button in each row
        ArrayList<Button> btns = new ArrayList<>();
        int btn_id = 0;
        for (int i = 0; i < rows.size(); i++){
            for (int j = 0; j < col ; j++){
                // get info from button obj
                String btn_color = mItem.getControl().getButtons().get(btn_id).getColor();
                String lbl = mItem.getControl().getButtons().get(btn_id).getLabel();

                Button btn = new Button(view.getContext());
                btn.setId(btn_id);
                btn.setText(lbl);
                btn.setTextSize(20);
                btn.setAllCaps(false);
                btn.setLayoutParams(layoutParams);
                btn.setTextColor(view.getContext().getColor(R.color.white));
                btn.setBackground(drawCircle(Color.parseColor(btn_color),
                        view.getContext().getColor(R.color.dark_gray)));
                if(btn_id + 1 <= num_btn){
                    btn.setOnClickListener(this);
                }else{
                    btn.setVisibility(Button.INVISIBLE);
                }
                // add btn in table row
                rows.get(i).addView(btn);
                btns.add(btn);
                btn_id += 1;
            }
        }
        //
        return view;
    }
    private static GradientDrawable drawCircle(int backgroundColor, int borderColor) {
        // plot rounded button
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setCornerRadii(new float[]{0, 0, 0, 0, 0, 0, 0, 0});
        shape.setColor(backgroundColor);
        // stroke width and color
        shape.setStroke(25, borderColor);
        return shape;
    }

    @Override
    public void onClick(View v) {
        // get button id via view
        int click_btn_id = v.getId();
        // get info from checklist item
        int btn_order = mItem.getControl().getButtons().get(click_btn_id).getOrder();
        int btn_status = mItem.getControl().getButtons().get(click_btn_id).getStatus();
        Long end = System.currentTimeMillis(); // end time for response
        mlistener.Completed(Control_TYPE.button,btn_order,btn_status,
                start, end);
    }
}

/*
 * Date: 2020.5.6
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Inspection;

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

import com.chekrite_group44.AssetProperties.Control_Type;
import com.chekrite_group44.AssetProperties.Inspection_checklist_item;
import com.chekrite_group44.R;

import java.util.ArrayList;

public class ButtonFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "fragment_button";
    private Inspection_checklist_item mItem;
    TextView mDescription;
    TextView mTitle;
    ProgressBar mProgressBar;
    int total_items;
    int position;
    Long start;
    InspectionListener mlistener;
    int col = 2; // define number of buttons should be in one row

    public ButtonFragment(Inspection_checklist_item item, int total_items, int position, InspectionListener listener) {
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
        // create table row
        ArrayList<TableRow> rows = new ArrayList<>();
        // num of btns
        int num_btn = mItem.getControl().getButtons().size();
        //
        if (mItem.getControl().getButtons_shape().equals("circle")) {
            // num of column
            int num_rows = (int) Math.ceil(num_btn / (double) col);
            for (int i = 0; i < num_rows; i++) {
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
            for (int i = 0; i < rows.size(); i++) {
                for (int j = 0; j < col; j++) {
                    // get info from button obj
                    String btn_color = "#FFFFFF";
                    String lbl = "";
                    try{
                        btn_color = mItem.getControl().getButtons().get(btn_id).getColor();
                        lbl = mItem.getControl().getButtons().get(btn_id).getLabel();
                    }catch (Exception e){
                        btn_color = "#FFFFFF";
                        lbl = "";
                    }

                    Button btn = new Button(view.getContext());
                    btn.setId(btn_id);
                    btn.setText(lbl);
                    btn.setTextSize(20);
                    btn.setAllCaps(false);
                    btn.setLayoutParams(layoutParams);
                    btn.setTextColor(view.getContext().getColor(R.color.white));
                    btn.setBackground(drawCircle(Color.parseColor(btn_color),
                            view.getContext().getColor(R.color.dark_gray)));
                    if (btn_id + 1 <= num_btn) {
                        btn.setOnClickListener(this);
                    } else {
                        btn.setVisibility(Button.INVISIBLE);
                    }
                    // add btn in table row
                    rows.get(i).addView(btn);
                    btns.add(btn);
                    btn_id += 1;
                }
            }
        }else if (mItem.getControl().getButtons_shape().equals("rectangle")){
            // num of column
            int num_rows = num_btn;
            for (int i = 0; i < num_rows; i++) {
                TableRow row = new TableRow(view.getContext());
                row.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                row.setGravity(Gravity.CENTER);
                rows.add(i, row);
                tableLayout.addView(row);
            }
            TableRow.LayoutParams layoutParams =
                    new TableRow.LayoutParams(1000, 150);
            layoutParams.setMargins(0, 25, 0, 25);
            // create rectangle btn for each row
            ArrayList<Button> btns = new ArrayList<>();
            for (int i = 0; i < rows.size(); i++) {
                String btn_color = mItem.getControl().getButtons().get(i).getColor();
                String lbl = mItem.getControl().getButtons().get(i).getLabel();
                Button btn = new Button(view.getContext());
                btn.setId(i);
                btn.setText(lbl);
                btn.setTextSize(20);
                btn.setAllCaps(false);
                btn.setTextColor(view.getContext().getColor(R.color.white));
                //
                GradientDrawable shape =  new GradientDrawable();
                shape.setCornerRadius(10);
                shape.setColor(Color.parseColor(btn_color));
                btn.setBackground(shape);
                //
                btn.setOnClickListener(this);
                btn.setLayoutParams(layoutParams);
                rows.get(i).addView(btn);
                btns.add(btn);
            }
        }
        //
        return view;
    }
    private GradientDrawable drawCircle(int backgroundColor, int borderColor) {
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
        String txt_value = mItem.getControl().getButtons().get(click_btn_id).getValue();
        Long end = System.currentTimeMillis(); // end time for response

        mlistener.Completed(Control_Type.BUTTONS,btn_order,btn_status,txt_value,
                start, end);
    }
}

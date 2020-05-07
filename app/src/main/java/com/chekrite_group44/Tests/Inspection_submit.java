/*
 * Date: 2020.5.6
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Tests;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.chekrite_group44.Chekrite;
import com.chekrite_group44.DashBoard.Dashboard;
import com.chekrite_group44.DashBoard.WelcomeSplash;
import com.chekrite_group44.R;

public class Inspection_submit extends DialogFragment {

    InspectionListener mlistener;
    Long start;
    public Inspection_submit(InspectionListener mlistener) {
        this.mlistener = mlistener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.inspection_submit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // find id
        SharedPreferences pref = view.getContext().getSharedPreferences(Chekrite.SHARED_PREFS, Context.MODE_PRIVATE);
        String highlight_colour = pref.getString("highlight_colour", "#65cb81");
        androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.submit_toolbar);
        toolbar.setBackgroundColor(Color.parseColor(highlight_colour));
        TextView txt_complete = view.findViewById(R.id.txt_checkcomplete);
        // convert color to drawable
        ColorDrawable cd = new ColorDrawable(Color.parseColor(highlight_colour));
        txt_complete.setBackground(cd);
        //
        start = System.currentTimeMillis();
        // cancel button
        Button mbtn_Cancel = view.findViewById(R.id.submit_cancel);
        mbtn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        // TODO submit function
        Button mbtn_Submit = view.findViewById(R.id.btn_submit);
        mbtn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Long end = System.currentTimeMillis();
                mlistener.Completed(Control_TYPE.button,0,0,
                        start, end);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.PopupOverlay);
    }
}

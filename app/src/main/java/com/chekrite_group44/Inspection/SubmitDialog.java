/*
 * Date: 2020.5.6
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Inspection;

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

import com.chekrite_group44.AssetProperties.ControlType;
import com.chekrite_group44.Chekrite;
import com.chekrite_group44.R;

public class SubmitDialog extends DialogFragment {

    InspectionListener mlistener;
    Long start;
    public SubmitDialog(InspectionListener mlistener) {
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
        androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.submit_toolbar);
        toolbar.setBackgroundColor(Chekrite.getParseColor());
        TextView txt_complete = view.findViewById(R.id.txt_checkcomplete);
        // convert color to drawable
        ColorDrawable cd = new ColorDrawable(Chekrite.getParseColor());
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
                mlistener.onCompleted(ControlType.BUTTONS,0,0, "",
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

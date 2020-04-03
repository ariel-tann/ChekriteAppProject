/*
 * Date: 2020.4.3
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite;

import android.app.Activity;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Chekrite_PinView {
    //TODO save data for device rotation
    //TODO toolbar Cancel
    private Activity mActivity;
    private int mPinWidth;
    private int mTextSize;
    private List<String> CurrentPin = new ArrayList<>();
    private int CurrentCursor = 0;
    private TextView PinTxt;
    private ImageButton mDigit0;
    private ImageButton mDigit1;
    private ImageButton mDigit2;
    private ImageButton mDigit3;
    private ImageButton mDigit4;
    private ImageButton mDigit5;
    private ImageButton mDigit6;
    private ImageButton mDigit7;
    private ImageButton mDigit8;
    private ImageButton mDigit9;
    private ImageButton mBackSpace;
    private Button mbtn_submit;
    private LinearLayout mLinearLayout;
    private List<TextView> mTextViews   = new ArrayList<>();
    private View.OnClickListener myDigitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.digit_0:
                    AddPin("0");
                    break;
                case R.id.digit_1:
                    AddPin("1");
                    break;
                case R.id.digit_2:
                    AddPin("2");
                    break;
                case R.id.digit_3:
                    AddPin("3");
                    break;
                case R.id.digit_4:
                    AddPin("4");
                    break;
                case R.id.digit_5:
                    AddPin("5");
                    break;
                case R.id.digit_6:
                    AddPin("6");
                    break;
                case R.id.digit_7:
                    AddPin("7");
                    break;
                case R.id.digit_8:
                    AddPin("8");
                    break;
                case R.id.digit_9:
                    AddPin("9");
                    break;
                case R.id.digit_back:
                    BackSpace();
                    break;
                case R.id.pin_submit:
                    break;
            }
        }
    };
    public Chekrite_PinView(Activity activity, int pinWidth, int textSize, String pinTxt) {
        mActivity = activity;
        mActivity.setContentView(R.layout.pin_layout);
        mPinWidth = pinWidth;
        mTextSize = textSize;
        PinTxt = activity.findViewById(R.id.PinTxt);
        PinTxt.setText(pinTxt);
        // initial digit and assign a listener
        mDigit0 = activity.findViewById(R.id.digit_0);
        mDigit0.setOnClickListener(myDigitListener);
        mDigit1 = activity.findViewById(R.id.digit_1);
        mDigit1.setOnClickListener(myDigitListener);
        mDigit2 = activity.findViewById(R.id.digit_2);
        mDigit2.setOnClickListener(myDigitListener);
        mDigit3 = activity.findViewById(R.id.digit_3);
        mDigit3.setOnClickListener(myDigitListener);
        mDigit4 = activity.findViewById(R.id.digit_4);
        mDigit4.setOnClickListener(myDigitListener);
        mDigit5 = activity.findViewById(R.id.digit_5);
        mDigit5.setOnClickListener(myDigitListener);
        mDigit6 = activity.findViewById(R.id.digit_6);
        mDigit6.setOnClickListener(myDigitListener);
        mDigit7 = activity.findViewById(R.id.digit_7);
        mDigit7.setOnClickListener(myDigitListener);
        mDigit8 = activity.findViewById(R.id.digit_8);
        mDigit8.setOnClickListener(myDigitListener);
        mDigit9 = activity.findViewById(R.id.digit_9);
        mDigit9.setOnClickListener(myDigitListener);
        mBackSpace = activity.findViewById(R.id.digit_back);
        mBackSpace.setOnClickListener(myDigitListener);
        mbtn_submit = activity.findViewById(R.id.pin_submit);
        mbtn_submit.setOnClickListener(myDigitListener);
        mLinearLayout = activity.findViewById(R.id.PinView_Linear);
        CreateTxtView();
    }
    private void CreateTxtView() {
        TextView textView;
        for (int i = 0; i < mPinWidth; i++) {
            textView = new TextView(mLinearLayout.getContext());
            textView.setTextSize(mTextSize);
            textView.setTextColor(mActivity.getColor(R.color.dark_blue));
            textView.setTypeface(null, Typeface.BOLD);
            textView.setPadding(15,10,15,10);
            textView.setBackground(mActivity.getDrawable(R.drawable.emp_edtxt));
            mTextViews.add(i, textView);
            mLinearLayout.addView(textView);
        }
    }
    private void AddPin(String i){
        if(CurrentPin.size()<mPinWidth) {
            CurrentPin.add(i);
            CurrentCursor+=1;
        }
        Log.d("KAI ", CurrentPin.toString());
        update();
}
    private void BackSpace(){
        if(CurrentCursor>0){
            CurrentCursor-=1;
            CurrentPin.remove(CurrentCursor);
        }
        update();
    }
    private void update(){
        for (int i = 0; i<mPinWidth;i++){
            if(CurrentPin.size()>i){
                String pin = CurrentPin.get(i);
                TextView view = mTextViews.get(i);
                view.setText(pin);
            }else{
                TextView view = mTextViews.get(i);
                view.setText("");
            }
        }
        if(CurrentCursor>0){
            mbtn_submit.setTextColor(mActivity.getColor(R.color.white));
            mbtn_submit.setBackground(mActivity.getDrawable(R.drawable.btn_blue));
        }else{
            mbtn_submit.setTextColor(mActivity.getColor(R.color.dark_gray));
            mbtn_submit.setBackground(mActivity.getDrawable(R.drawable.btn_gray));
        }
    }
}

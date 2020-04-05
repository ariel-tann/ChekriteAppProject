/*
 * Date: 2020.4.4
 * This file is created by Kai.
 * Summary:
 */

/*
 * Date: 2020.4.3
 * This file is created by Kai.
 * Summary: this class create flexible length of pin according to user needs
 */

package com.chekrite.PinView;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.chekrite.R;

import java.util.ArrayList;
import java.util.List;

public class Chekrite_PinView extends DialogFragment {
    //TODO Kai save data for device rotation
    private Context mContent;
    private int mPinWidth;
    private int mTextSize;
    private List<String> CurrentPin = new ArrayList<>();
    private int CurrentCursor = 0;
    private TextView mPinTxtView;
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
    private String mPinTxt;
    private String mBtnTxt;
    private String mPinTitle;
    private LinearLayout mLinearLayout;
    private List<TextView> mTextViews   = new ArrayList<>();
    /*
    * create button listener for image buttons
     */
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
                    //TODO Kai submit func
                    break;
                case R.id.pin_cancel:
                    dismiss();
                    break;
            }
        }
    };
    /*
    * para activity: given from main activity
    * para pinWidth: pin length
    * para textSize: text size of TextView
    * para pinTxt: description of pin layout
     */
    public Chekrite_PinView(Context context, int pinWidth, int textSize, String pinTxt, String btn_txt, String pin_title) {
        mContent = context;
        mPinWidth = pinWidth;
        mTextSize = textSize;
        mPinTxt = pinTxt;
        mBtnTxt = btn_txt;
        mPinTitle = pin_title;
    }

    /*
    * Add a string to List of String
    * and update cursor position
     */
    private void AddPin(String i){
        if(CurrentPin.size()<mPinWidth) {
            CurrentPin.add(i);
            CurrentCursor+=1;
        }
        update();
    }
    /*
    * remove a string from list of String
     */
    private void BackSpace(){
        if(CurrentCursor>0){
            CurrentCursor-=1;
            CurrentPin.remove(CurrentCursor);
        }
        update();
    }
    /*
    * update pin change, when click image button
    */
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
            mbtn_submit.setTextColor(mContent.getColor(R.color.white));
            mbtn_submit.setBackground(mContent.getDrawable(R.drawable.btn_blue));
        }else{
            mbtn_submit.setTextColor(mContent.getColor(R.color.dark_gray));
            mbtn_submit.setBackground(mContent.getDrawable(R.drawable.btn_gray));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        set style for dialog add
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.pin_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // initial digit and assign a listener
        mDigit0 = view.findViewById(R.id.digit_0);
        mDigit0.setOnClickListener(myDigitListener);
        mDigit1 = view.findViewById(R.id.digit_1);
        mDigit1.setOnClickListener(myDigitListener);
        mDigit2 = view.findViewById(R.id.digit_2);
        mDigit2.setOnClickListener(myDigitListener);
        mDigit3 = view.findViewById(R.id.digit_3);
        mDigit3.setOnClickListener(myDigitListener);
        mDigit4 = view.findViewById(R.id.digit_4);
        mDigit4.setOnClickListener(myDigitListener);
        mDigit5 = view.findViewById(R.id.digit_5);
        mDigit5.setOnClickListener(myDigitListener);
        mDigit6 = view.findViewById(R.id.digit_6);
        mDigit6.setOnClickListener(myDigitListener);
        mDigit7 = view.findViewById(R.id.digit_7);
        mDigit7.setOnClickListener(myDigitListener);
        mDigit8 = view.findViewById(R.id.digit_8);
        mDigit8.setOnClickListener(myDigitListener);
        mDigit9 = view.findViewById(R.id.digit_9);
        mDigit9.setOnClickListener(myDigitListener);
        mBackSpace = view.findViewById(R.id.digit_back);
        mBackSpace.setOnClickListener(myDigitListener);
        mbtn_submit = view.findViewById(R.id.pin_submit);
        mbtn_submit.setText(mBtnTxt);
        mbtn_submit.setOnClickListener(myDigitListener);
        Button mbtn_pinCancel = view.findViewById(R.id.pin_cancel);
        mbtn_pinCancel.setOnClickListener(myDigitListener);

        mPinTxtView = view.findViewById(R.id.PinTxtView);
        mPinTxtView.setText(mPinTxt);
        TextView pintitle = view.findViewById(R.id.pin_title);
        pintitle.setText(mPinTitle);
        mLinearLayout = view.findViewById(R.id.PinView_Linear);
        CreateTxtView();
    }
    /*
     * create mPinWidth of TextView
     */
    private void CreateTxtView() {
        TextView textView;
        for (int i = 0; i < mPinWidth; i++) {
            textView = new TextView(mLinearLayout.getContext());
            textView.setTextSize(mTextSize);
            textView.setTextColor(mContent.getColor(R.color.dark_blue));
            textView.setTypeface(null, Typeface.BOLD);
            textView.setGravity(Gravity.CENTER);
            textView.setBackground(mContent.getDrawable(R.drawable.pin_TxtViewBorder));
            mTextViews.add(i, textView);
            mLinearLayout.addView(textView);
        }
    }
}

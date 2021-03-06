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

package com.chekrite_group44.PinView;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.chekrite_group44.Chekrite;
import com.chekrite_group44.R;

import java.util.ArrayList;
import java.util.List;

public class PinViewDialog extends DialogFragment {
    // Digit Type
    public static final int PAIR = 0;
    public static final int EMPLOY_ID = 1;
    public static final int EMPLOY_PIN = 2;
    //
    private int Type_PinView;
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
    private boolean IsPassWord;
    private LinearLayout mLinearLayout;
    private List<EditText> mEditViews = new ArrayList<>();
    private AudioManager audioManager;
    private PinListener mPinListener;
    /*
    * create button listener for image buttons
     */
    private View.OnClickListener DigitUpdateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.digit_0:
                    audioManager.playSoundEffect(SoundEffectConstants.CLICK,0.8f);
                    addPin("0");
                    break;
                case R.id.digit_1:
                    audioManager.playSoundEffect(SoundEffectConstants.CLICK,0.8f);
                    addPin("1");
                    break;
                case R.id.digit_2:
                    audioManager.playSoundEffect(SoundEffectConstants.CLICK,0.8f);
                    addPin("2");
                    break;
                case R.id.digit_3:
                    audioManager.playSoundEffect(SoundEffectConstants.CLICK,0.8f);
                    addPin("3");
                    break;
                case R.id.digit_4:
                    audioManager.playSoundEffect(SoundEffectConstants.CLICK,0.8f);
                    addPin("4");
                    break;
                case R.id.digit_5:
                    audioManager.playSoundEffect(SoundEffectConstants.CLICK,0.8f);
                    addPin("5");
                    break;
                case R.id.digit_6:
                    audioManager.playSoundEffect(SoundEffectConstants.CLICK,0.8f);
                    addPin("6");
                    break;
                case R.id.digit_7:
                    audioManager.playSoundEffect(SoundEffectConstants.CLICK,0.8f);
                    addPin("7");
                    break;
                case R.id.digit_8:
                    audioManager.playSoundEffect(SoundEffectConstants.CLICK,0.8f);
                    addPin("8");
                    break;
                case R.id.digit_9:
                    audioManager.playSoundEffect(SoundEffectConstants.CLICK,0.8f);
                    addPin("9");
                    break;
                case R.id.digit_back:
                    audioManager.playSoundEffect(SoundEffectConstants.CLICK,0.8f);
                    backSpace();
                    break;
                case R.id.pin_submit:
                    // convert ArrayList of PIN to String
                    String PIN = "";
                    for (String tmp:CurrentPin) {
                        PIN += tmp;
                    }
                    // Tell main activity, user click finish btn
                    mPinListener.onSubmit(PIN);
                    // close dialog, except employ ID should not close
                    if(Type_PinView != EMPLOY_ID){
                        dismiss();
                    }
                    break;
                case R.id.pin_cancel:
                    // close dialog
                    dismiss();
                    break;
            }

        }
    };

    public PinViewDialog() {
    }

    public PinViewDialog(int select, PinListener pinListener) {
        Type_PinView = select;
        mPinListener = pinListener;
    }

    /*
    * Add a string to List of String
    * and update cursor position
     */
    private void addPin(String aDigit){
        if(CurrentPin.size()<mPinWidth) {
            CurrentPin.add(aDigit);
            CurrentCursor+=1;
        }
        updateEditviews();
    }
    /*
    * remove a string from list of String
     */
    private void backSpace(){
        if(CurrentCursor>0){
            CurrentCursor-=1;
            CurrentPin.remove(CurrentCursor);
        }
        updateEditviews();
    }
    /*
    * update pin change, when click image button
    */
    private void updateEditviews(){
        for (int i = 0; i<mPinWidth;i++){
            if(CurrentPin.size()>i){
                String pin = CurrentPin.get(i);
                EditText view = mEditViews.get(i);
                view.setText(pin);
            }else{
                TextView view = mEditViews.get(i);
                view.setText("");
            }
        }
        if(CurrentCursor > 1){

//            mbtn_submit.setBackgroundColor(Chekrite.getParseColor());
            mbtn_submit.setTextColor(getActivity().getColor(R.color.white));
            GradientDrawable shape =  new GradientDrawable();
            shape.setCornerRadius(10);
            shape.setColor(Chekrite.getParseColor());
            mbtn_submit.setBackground(shape);
            mbtn_submit.setEnabled(true);
        }else{
            mbtn_submit.setTextColor(getActivity().getColor(R.color.dark_gray));
            mbtn_submit.setBackground(getActivity().getDrawable(R.drawable.btn_gray));
            mbtn_submit.setEnabled(false);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
//        set style for dialog add
        setStyle(DialogFragment.STYLE_NORMAL, R.style.PopupOverlay);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        switch (Type_PinView){
            case EMPLOY_ID:
                return inflater.inflate(R.layout.layout_pin, container, false);
            case EMPLOY_PIN:
                return inflater.inflate(R.layout.layout_pin, container, false);
            case PAIR:
                return inflater.inflate(R.layout.layout_setup, container, false);
            default:
                return inflater.inflate(R.layout.layout_setup, container, false);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switch (Type_PinView){
            case PAIR:
                mPinWidth = 6;
                mTextSize = 42;
                mPinTxt = getActivity().getString(R.string.pair_txt);
                mBtnTxt = getActivity().getString(R.string.btn_pin_pair);
                mPinTitle = getActivity().getString(R.string.pin_title_setup);
                IsPassWord = false;
                break;
            case EMPLOY_ID:
                mPinWidth = 8;
                mTextSize = 42;
                mPinTxt = getActivity().getString(R.string.employeeID_txt);
                mBtnTxt = getActivity().getString(R.string.btn_pin_enter);
                mPinTitle = getActivity().getString(R.string.pin_title_singin);
                IsPassWord = false;
                break;
            case EMPLOY_PIN:
                mPinWidth = 4;
                mTextSize = 42;
                mPinTxt = getActivity().getString(R.string.employeePIN_txt);
                mBtnTxt = getActivity().getString(R.string.btn_pin_enter);
                mPinTitle = getActivity().getString(R.string.pin_title_singin);
                IsPassWord = true;
                break;
        }
        // get color and set to btn background

        // initial digit and assign a listener
        mDigit0 = view.findViewById(R.id.digit_0);
        mDigit0.setOnClickListener(DigitUpdateListener);
        mDigit1 = view.findViewById(R.id.digit_1);
        mDigit1.setOnClickListener(DigitUpdateListener);
        mDigit2 = view.findViewById(R.id.digit_2);
        mDigit2.setOnClickListener(DigitUpdateListener);
        mDigit3 = view.findViewById(R.id.digit_3);
        mDigit3.setOnClickListener(DigitUpdateListener);
        mDigit4 = view.findViewById(R.id.digit_4);
        mDigit4.setOnClickListener(DigitUpdateListener);
        mDigit5 = view.findViewById(R.id.digit_5);
        mDigit5.setOnClickListener(DigitUpdateListener);
        mDigit6 = view.findViewById(R.id.digit_6);
        mDigit6.setOnClickListener(DigitUpdateListener);
        mDigit7 = view.findViewById(R.id.digit_7);
        mDigit7.setOnClickListener(DigitUpdateListener);
        mDigit8 = view.findViewById(R.id.digit_8);
        mDigit8.setOnClickListener(DigitUpdateListener);
        mDigit9 = view.findViewById(R.id.digit_9);
        mDigit9.setOnClickListener(DigitUpdateListener);
        mBackSpace = view.findViewById(R.id.digit_back);
        mBackSpace.setOnClickListener(DigitUpdateListener);
        mbtn_submit = view.findViewById(R.id.pin_submit);
        mbtn_submit.setText(mBtnTxt);
        mbtn_submit.setOnClickListener(DigitUpdateListener);
        Button mbtn_pinCancel = view.findViewById(R.id.pin_cancel);
        mbtn_pinCancel.setOnClickListener(DigitUpdateListener);
        mPinTxtView = view.findViewById(R.id.PinTxtView);
        mPinTxtView.setText(mPinTxt);
        TextView pintitle = view.findViewById(R.id.pin_title);
        pintitle.setText(mPinTitle);
        mLinearLayout = view.findViewById(R.id.PinView_Linear);
        CreateTxtView();
        // reload pin to screen
        load(savedInstanceState);
        audioManager = (AudioManager)view.getContext().getSystemService(Context.AUDIO_SERVICE);
        if(Type_PinView != PAIR) {
            androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.pin_toolbar);
            toolbar.setBackgroundColor(Chekrite.getParseColor());
        }
    }
    /*
     * create mPinWidth of TextView
     */
    private void CreateTxtView() {
        EditText editText;
        for (int i = 0; i < mPinWidth; i++) {
            editText = new EditText(mLinearLayout.getContext());
            editText.setTextSize(mTextSize);
            editText.setEnabled(false);
            editText.setTextColor(Chekrite.getParseColor());
            editText.setTypeface(null, Typeface.BOLD);
            editText.setGravity(Gravity.CENTER);
            editText.setFocusable(false);
            editText.setCursorVisible(false);
            if (IsPassWord){
                editText.setInputType(InputType.TYPE_CLASS_NUMBER |
                                        InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            }
            editText.setBackground(getActivity().getDrawable(R.drawable.pin_txtview_border));
            mEditViews.add(i, editText);
            mLinearLayout.addView(editText);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        // save info when device is rotated
        super.onSaveInstanceState(outState);
        if(CurrentPin.size()>0) {
            String pin = "";
            for (int i = 0; i < CurrentPin.size(); i++) {
                pin += CurrentPin.get(i);
            }
            outState.putString("PIN", pin);
            outState.putInt("Cursor", CurrentCursor);
            outState.putInt("Type", Type_PinView);
            Log.d("KAI", "save PIN: " + pin);
        }
    }

    private void load(Bundle bundle){
        // load CurrentPin, when user rotate device
        // CurrentPin still keep same pin
        if(!CurrentPin.isEmpty()){
            CurrentPin.clear();
        }
        if(bundle!=null) {
            String pin = bundle.getString("PIN", "");
            Type_PinView = bundle.getInt("Type", 0);
            CurrentCursor = bundle.getInt("Cursor", 0);
            if (pin.length() > 0) {
                for (int i = 0; i < pin.length(); i++) {
                    CurrentPin.add(pin.charAt(i) + "");
                }
            }
            updateEditviews();
        }
    }
}

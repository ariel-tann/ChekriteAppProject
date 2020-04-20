/*
 * Date: 2020.4.12
 * This file is created by Kai.
 * Summary:
 */

/*
 * Date: 2020.4.12
 * This file is created by Kai.
 * Summary:
 */

/*
 * Date: 2020.4.12
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite.PinView;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.chekrite.R;

//  extends LinearLayout implements View.OnclickListener


public class NumInputPanel extends GridLayout implements View.OnClickListener{
    private ImageButton numOne, numTwo, numThree, numFour,
            numFive, numSix, numSeven, numEight,
            numNine, numZero, buttonDelete;
    private SparseArray<String> keyValues = new SparseArray<>();
    private InputConnection inputConnection;



    public  NumInputPanel(Context context){
        this(context, null, 0);
    }
    public  NumInputPanel(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }
    public NumInputPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    public void init(Context context, AttributeSet attr){
        LayoutInflater.from(context).inflate(R.layout.num_input_panel,this,true);
        numOne = (ImageButton) findViewById(R.id.digit_1);
        numOne.setOnClickListener(this);
        numTwo = (ImageButton) findViewById(R.id.digit_2);
        numTwo.setOnClickListener(this);
        numThree = (ImageButton) findViewById(R.id.digit_3);
        numThree.setOnClickListener(this);
        numFour = (ImageButton) findViewById(R.id.digit_4);
        numFour.setOnClickListener(this);
        numFive = (ImageButton) findViewById(R.id.digit_5);
        numFive.setOnClickListener(this);
        numSix = (ImageButton) findViewById(R.id.digit_6);
        numSix.setOnClickListener(this);
        numSeven = (ImageButton) findViewById(R.id.digit_7);
        numSeven.setOnClickListener(this);
        numEight = (ImageButton) findViewById(R.id.digit_8);
        numEight.setOnClickListener(this);
        numNine = (ImageButton) findViewById(R.id.digit_9);
        numNine.setOnClickListener(this);
        numZero = (ImageButton) findViewById(R.id.digit_0);
        numZero.setOnClickListener(this);
        buttonDelete = (ImageButton) findViewById(R.id.digit_back);
        buttonDelete.setOnClickListener(this);

        keyValues.put(R.id.digit_1, "1");
        keyValues.put(R.id.digit_2, "2");
        keyValues.put(R.id.digit_3, "3");
        keyValues.put(R.id.digit_4, "4");
        keyValues.put(R.id.digit_5, "5");
        keyValues.put(R.id.digit_6, "6");
        keyValues.put(R.id.digit_7, "7");
        keyValues.put(R.id.digit_8, "8");
        keyValues.put(R.id.digit_9, "9");
        keyValues.put(R.id.digit_0, "0");

    }


    /*
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.num_input_panel, container, false);
        numOne = v.findViewById(R.id.digit_1);
        numTwo = v.findViewById(R.id.digit_2);
        numThree = v.findViewById(R.id.digit_3);
        numFour = v.findViewById(R.id.digit_4);
        numFive = v.findViewById(R.id.digit_5);
        numSix = v.findViewById(R.id.digit_6);
        numSeven = v.findViewById(R.id.digit_7);
        numEight = v.findViewById(R.id.digit_8);
        numNine = v.findViewById(R.id.digit_9);
        numZero = v.findViewById(R.id.digit_0);
        buttonDelete = v.findViewById(R.id.digit_back);
        return v;
    }

     */






    @Override
    public void onClick(View v) {
        if (inputConnection == null)
            return;
        //set delete button
        if(v.getId() == R.id.digit_back) {
            CharSequence selectedText = inputConnection.getSelectedText(0);
            if(TextUtils.isEmpty(selectedText)){
                inputConnection.deleteSurroundingText(1,0);
            }else {
                inputConnection.commitText("", 1);
            }
        }else {
            String value = keyValues.get(v.getId());
            inputConnection.commitText(value, 1);
        }
    }
    public void setInputConnection(InputConnection ic){
        inputConnection = ic;
    }
}


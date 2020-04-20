/*
 * Date: 2020.4.17
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite.userLogin;

import android.os.Bundle;

import com.chekrite.PinView.NumInputPanel;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

import com.chekrite.R;

public class IdInput extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_id_input, container, false);

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceStage){
        super.onViewCreated(view, savedInstanceStage);

/**
        EditText editText = (EditText) view.findViewById(R.id.editText_id);
        NumInputPanel numberinput = (NumInputPanel) view.findViewById(R.id.digit_panel);
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editText.setTextIsSelectable(false);

        InputConnection ic = editText.onCreateInputConnection(new EditorInfo());
        numberinput.setInputConnection(ic);
**/
        view.findViewById(R.id.go_to_pw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(IdInput.this)
                        .navigate(R.id.action_id_to_pw);

            }
        });
    }



}

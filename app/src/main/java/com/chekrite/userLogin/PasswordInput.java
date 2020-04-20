/*
 * Date: 2020.4.18
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite.userLogin;


import android.os.Bundle;

import com.chekrite.PinView.NumInputPanel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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


public class PasswordInput extends Fragment {
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_pw_input, container, false);

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceStage){
        super.onViewCreated(view, savedInstanceStage);

        view.findViewById(R.id.pin_cancel)
                 .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to welcome  viewgroup
                NavHostFragment.findNavController(PasswordInput.this)
                        .navigate(R.id.action_pw_to_id);

            }
        });
/**

        EditText editText = (EditText) view.findViewById(R.id.editText_pw);
        NumInputPanel numberinput = (NumInputPanel) view.findViewById(R.id.digit_panel);
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editText.setTextIsSelectable(false);

        InputConnection ic = editText.onCreateInputConnection(new EditorInfo());
        numberinput.setInputConnection(ic);

 **/
    }


}

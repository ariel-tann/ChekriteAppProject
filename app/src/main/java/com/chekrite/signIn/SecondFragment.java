/*
 * Date: 2020.4.21
 * This file is created by Kai.
 * Summary:
 */

/*
 * Date: 2020.4.19
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite.signIn;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.chekrite.PinView.NumInputPanel;
import com.chekrite.R;

public class SecondFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText editText = (EditText) view.findViewById(R.id.editText_id);
        NumInputPanel numberinput = (NumInputPanel) view.findViewById(R.id.digit_panel);
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editText.setTextIsSelectable(false);

        InputConnection ic = editText.onCreateInputConnection(new EditorInfo());
        numberinput.setInputConnection(ic);

        view.findViewById(R.id.pw_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }
}

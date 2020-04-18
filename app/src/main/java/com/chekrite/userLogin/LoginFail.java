/*
 * Date: 2020.4.19
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite.userLogin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chekrite.R;

public class LoginFail extends Fragment {
    private EditText failinfo;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fail,container, false);


    }

    // show page in secs set time


    //go back



}

/*
 * Date: 2020.4.17
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite.userLogin;

import android.os.Bundle;

import com.chekrite.PinView.NumInputPanel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

import com.chekrite.R;

public class UserLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EditText editText = (EditText) findViewById(R.id.editText_num);
        NumInputPanel numberinput = (NumInputPanel) findViewById(R.id.digit_panel);
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editText.setTextIsSelectable(false);

        InputConnection ic = editText.onCreateInputConnection(new EditorInfo());
        numberinput.setInputConnection(ic);
    }

}

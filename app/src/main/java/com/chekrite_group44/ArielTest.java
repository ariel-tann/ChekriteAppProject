/*
 * Date: 2020.5.18
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;


import com.chekrite_group44.Keyboard.KeyboardFragment;
import com.chekrite_group44.SelectAssetScreen.SearchAssetFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class ArielTest extends AppCompatActivity
        implements SearchAssetFragment.searchAssetListener, KeyboardFragment.keyboardFragmentListener {

    private SearchAssetFragment SearchAssetFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ariel_test);


        SearchAssetFragment = new SearchAssetFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.your_placeholder, SearchAssetFragment);
        ft.commit();

    }

    @Override
    public void onInputKeyboardSent(CharSequence input) {
        SearchAssetFragment.updateEditText(input);
    }

    @Override
    public void onInputSearchAssetSent(CharSequence input) {

    }
}

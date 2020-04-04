package com.chekrite.select_asset_screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chekrite.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Select_asset_screen extends Fragment {
    View view;
    Bundle bundle;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view=inflater.inflate(R.layout.select_tab,container,false);
       return view;
    }
}

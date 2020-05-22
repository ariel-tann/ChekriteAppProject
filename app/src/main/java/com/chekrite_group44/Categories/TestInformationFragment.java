/*
 * Date: 2020.5.22
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chekrite_group44.R;

public class TestInformationFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      //  return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.test_information, container, false);
        return view;
    }
}

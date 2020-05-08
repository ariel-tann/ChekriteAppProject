/*
 * Date: 2020.4.11
 * This file is created by Kai.
 * Summary:
 */

/*
 * Date: 2020.4.5
 * This file is created by Kai.
 * Summary:
 */

/*
 * Date: 2020.4.5
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.DashBoard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chekrite_group44.R;

public class Sync extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View displayTime = inflater.inflate(R.layout.sync, container,false);
        return displayTime;
    }

    public void getSyncTime(){

    }
}
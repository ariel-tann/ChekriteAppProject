/*
 * Date: 2020.5.4
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.SelectAssetScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chekrite_group44.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SelectTab extends Fragment {
    View rootView;
    RecyclerView selectTab;
    LinearLayoutManager linearLayoutManager;
    ArrayList list=new ArrayList();
    public SelectTab() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_select_tab, container, false);
        selectTab = rootView.findViewById(R.id.select_tab_recyclerview);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        selectTab.setLayoutManager(new LinearLayoutManager(getActivity()));
        selectTab.setAdapter(new SelectTabAdapter(getActivity().getApplicationContext(),list));
        return rootView;
    }
}

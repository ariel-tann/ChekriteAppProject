package com.chekrite_group44.SelectAssetScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chekrite_group44.R;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SelectAssetScreenFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    int mPage;
    View view;
    Bundle bundle;
    TextView textView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage=getArguments().getInt(ARG_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view=inflater.inflate(R.layout.layout_select_tab_fragment,container,false);
       return view;
    }

    public static  SelectAssetScreenFragment newInstance(int page){
        Bundle args=new Bundle();
        args.putInt(ARG_PAGE,page);
        SelectAssetScreenFragment selectAssetScreenFragment=new SelectAssetScreenFragment();
        selectAssetScreenFragment.setArguments(args);
        return selectAssetScreenFragment;
    }
}

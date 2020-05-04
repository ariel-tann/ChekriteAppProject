/*
 * Date: 2020.5.4
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.SelectAssetScreen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chekrite_group44.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SelectTabAdapter extends RecyclerView.Adapter<SelectTabAdapter.MyViewHolder> {
    Context context;
    ArrayList data;
    TextView listview;
    public SelectTabAdapter(Context context, ArrayList list)
    {
        Log.d("SHREY","Isnide selecttabadapter");
        this.context=context;
        data=list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_select_tab_fragment,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        listview.setText(data.get(getItemCount()-position-1).toString());
    }
    @Override
    public int getItemCount() {
        int size;
        if(data!=null)
        {
            size=data.size();
        }else{
            size=0;
        }
        return size;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            listview=itemView.findViewById(R.id.textView);

        }
    }
}


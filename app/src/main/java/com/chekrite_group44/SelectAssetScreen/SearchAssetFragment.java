/*
 * Date: 2020.5.12
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.SelectAssetScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.chekrite_group44.Asset_Properties.Search_Asset;
import com.chekrite_group44.DashBoard.Dashboard;
import com.chekrite_group44.Categories.Categories;
import com.chekrite_group44.Chekrite;
import com.chekrite_group44.Http_Request.APIs;
import com.chekrite_group44.Http_Request.APIsListener;
import com.chekrite_group44.Http_Request.APIsTask;
import com.chekrite_group44.Keyboard.KeyboardFragment;
import com.chekrite_group44.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchAssetFragment extends Fragment implements SearchAssetAdapter.OnAssetListener {

    private static final String TAG = "SearchAsset";

    private searchAssetListener listener;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private EditText editText;
    ImageButton deleteAllBtn;
    TextView found;

    private List<String> SearchText = new ArrayList<>(); //hold the text that displays in editText
    ArrayList<SearchAssetItems> searchAssetList = new ArrayList<>();

    private int searchLength = 10;

    APIsListener SearchAssetListener = new APIsListener() {
        @Override
        public void API_Completed(JSONObject jsonObject) {
            String status = null;
            try {
                status = (String) jsonObject.get("status");
                String message = (String) jsonObject.get("message");
                if (status.equals("success")) {
                    Log.d(TAG, "search asset success: " + message);
                    JSONArray data = jsonObject.getJSONArray("data");
                    if (data != null) {

                        Log.d(TAG, "data not null");

                       // JSONObject object = data.getJSONObject(i);
                        //Search_Asset sa_list = new Search_Asset(object);


                        for (int i = 0; i < data.length(); i++) {
                            JSONObject object = data.getJSONObject(i);
//                            Search_Asset sa_list = new Search_Asset(object);
                            String id = Integer.toString(object.getInt("id"));
                            String unitNumber = object.getString("unit_number");
                            String make = object.getString("make");
                            String model = object.getString("model");
                            String photoUrl = object.getString("photo");
                            searchAssetList.add(new SearchAssetItems(id, unitNumber, make, model, photoUrl));
                            Log.d(TAG, "searchassetlist.size(): " + searchAssetList.size());
//                            Log.d(TAG, "Part 2: " + searchAssetList.size());

                        }
                        mAdapter.notifyDataSetChanged();
                        String numAssetFound = Integer.toString(searchAssetList.size()) + " found";
                        found.setText(setFoundTextColor(numAssetFound));

                    }else
                        {
                            Log.d(TAG, "jsonDATA = null");
                        }

                    }
                    }catch(JSONException e){
                         e.printStackTrace();}
            }
        };





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_asset_fragment, container, false);
        view.setBackgroundColor(Chekrite.getParseColor());

        editText = view.findViewById(R.id.editText);
        deleteAllBtn = view.findViewById(R.id.deleteAllBtn);
        found = view.findViewById(R.id.numAssetFoundText);

        deleteAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllText();
            }
        });

        mRecyclerView = view.findViewById(R.id.searchAssetRecyclerView);
        recyclerBuild();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Fragment childFragment = new KeyboardFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.keyboardFragment, childFragment).commit();
    }

    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof searchAssetListener) {
            listener = (searchAssetListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement searchAsset listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    //handles building the recycler view
    public void recyclerBuild() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mAdapter = new SearchAssetAdapter(searchAssetList, this.getContext(), this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onAssetClick(int position) {
        Log.d(TAG, "onAssetClick: clicked. Id number: " + searchAssetList.get(position).getUnitNumber());
        Intent intent = new Intent(getActivity(), Categories.class);
        intent.putExtra("asset_id", searchAssetList.get(position).getId());
        intent.putExtra("unit_number", searchAssetList.get(position).getUnitNumber());
        intent.putExtra("make", searchAssetList.get(position).getMake());
        intent.putExtra("model", searchAssetList.get(position).getModel());
        intent.putExtra("photo", searchAssetList.get(position).getPhoto());
        startActivity(intent);
    }

    //listen for letters or numbers clicked from keyboard
    public interface searchAssetListener {
        void onInputSearchAssetSent (CharSequence input);
    }

    //edit the number of asset found text to the same as ios version
    public SpannableString setFoundTextColor (String text) {
        SpannableString ss = new SpannableString(text);
        ForegroundColorSpan color = new ForegroundColorSpan(Chekrite.getParseColor());
        StyleSpan bold = new StyleSpan(Typeface.BOLD);
        ss.setSpan(color, 0, 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ss.setSpan(bold, 0, 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return ss;
    }

    //gets input from keyboard and updates the edit text view
    public void updateEditText(CharSequence newText) {
        mAdapter.notifyDataSetChanged();

        if(newText != "/") {
            addNewText(newText.toString());
        } else {
            if(SearchText.size()>0) {
                deleteOneChar();
            }
        }
    }

    //if there is no text, clears the array and set text to default text and color
    public void checkEditText() {
        if (SearchText.size() == 0) {
            editText.setText("type to search");
            editText.setTextColor(Color.parseColor("#dcdcdc"));
            editText.setTextSize(16);
            editText.setTypeface(null, Typeface.NORMAL);
            searchAssetList.clear();
            found.setText("");
            mAdapter.notifyDataSetChanged();


        }
    }

    //adds new text to array and displays
    public void addNewText(String newText) {
        SearchText.add(newText.toString());
        displayText();
        checkEditText();

    }

    //handle deleting one character
    public void deleteOneChar() {
        SearchText.remove(SearchText.size() - 1);
        displayText();
        checkEditText();
    }

    //handle delete all button press
    public void clearAllText() {
        SearchText.clear();
        searchAssetList.clear();
        displayText();
        checkEditText();
        mAdapter.notifyDataSetChanged();
    }


    //get string from arraylist and displays. Calls to API if text is >= 3
    public void displayText() {
        String joined = TextUtils.join("", SearchText);
        if(SearchText.size() >= 3){
            new APIsTask(SearchAssetListener).execute("GET", APIs.SEARCH, joined, "");
            mAdapter.notifyDataSetChanged();

        }
        editText.setText(joined);
        editText.setTextColor(Chekrite.getParseColor());
        editText.setTextSize(20);
        editText.setTypeface(null, Typeface.BOLD);

    }

}

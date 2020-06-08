/*
 * Date: 2020.6.2
 * This file is created by Kai.
 * Summary:
 */

/*
 * Date: 2020.5.12
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.NewCheck;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
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
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.chekrite_group44.AssetProperties.SearchAssetItems;
import com.chekrite_group44.ChecklistSelection.CategoriesActivity;
import com.chekrite_group44.Chekrite;
import com.chekrite_group44.HttpRequest.APIs;
import com.chekrite_group44.HttpRequest.APIsListener;
import com.chekrite_group44.HttpRequest.APIsTask;
import com.chekrite_group44.Keyboard.KeyboardFragment;
import com.chekrite_group44.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchAssetFragment extends Fragment implements SearchAssetAdapter.OnAssetListener {

    private static final String TAG = "SearchAsset";

    private SearchAssetFragment.SearchAssetListener listener;
    private RecyclerView mRecyclerView;
    private SearchAssetAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private EditText editText;
    private ImageButton deleteAllBtn;
    private TextView found;

    private String joined = "";

    private List<String> searchText = new ArrayList<>(); //hold the text that displays in editText
    public ArrayList<SearchAssetItems> searchAssetList = new ArrayList<>();


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

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject object = data.getJSONObject(i);
                            int id = object.getInt("id");
                            String unitNumber = object.getString("unit_number");
                            String make = object.getString("make");
                            String model = object.getString("model");
                            String photoUrl = object.getString("photo");
                            searchAssetList.add(new SearchAssetItems(id, unitNumber, make, model, photoUrl));
                        }

                        mAdapter.notifyDataSetChanged();
                        String numAssetFound = Integer.toString(searchAssetList.size()) + " found";
                        found.setText(setFoundTextColor(numAssetFound));
                        Log.d(TAG, "API_Completed: search asset size" + searchAssetList.size());
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
        View view = inflater.inflate(R.layout.fragment_search_asset, container, false);
        view.setBackgroundColor(Chekrite.getParseColor());

        editText = view.findViewById(R.id.editText);
        deleteAllBtn = view.findViewById(R.id.deleteAllBtn);
        deleteAllBtn.setVisibility(View.GONE);
        found = view.findViewById(R.id.numAssetFoundText);

        deleteAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllText();
            }
        });

        mRecyclerView = view.findViewById(R.id.searchAssetRecyclerView);
        recyclerBuild();

        //Listens when edit has changes
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                changeSearchItemColor(s.toString());
            }
        });

        return view;
    }

    //Sends search query to adapter for search item color change
    private void changeSearchItemColor(String text ) {
        mAdapter.filterList(joined);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Fragment childFragment = new KeyboardFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.keyboardFragment, childFragment).commit();
    }

    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof SearchAssetFragment.SearchAssetListener) {
            listener = (SearchAssetFragment.SearchAssetListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement SearchAsset listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    //handles building the recycler view
    private void recyclerBuild() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mAdapter = new SearchAssetAdapter(searchAssetList, this.getContext(), this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    //handles clicks on list item
    @Override
    public void onAssetClick(int position) {
        Log.d(TAG, "onAssetClick: clicked. Id number: " + searchAssetList.get(position).getUnitNumber());
        Intent intent = new Intent(getActivity(), CategoriesActivity.class);
        intent.putExtra("asset_id", searchAssetList.get(position).getId());
        intent.putExtra("unit_number", searchAssetList.get(position).getUnitNumber());
        intent.putExtra("make", searchAssetList.get(position).getMake());
        intent.putExtra("model", searchAssetList.get(position).getModel());
        intent.putExtra("photo", searchAssetList.get(position).getPhoto());
        startActivity(intent);
    }

    //listen for letters or numbers clicked from keyboard
    public interface SearchAssetListener {
        void onInputSearchAssetSent (CharSequence input);
    }

    //edit the number of asset found text to the same as ios version
    private SpannableString setFoundTextColor(String text) {
        SpannableString ss = new SpannableString(text);
        ForegroundColorSpan color = new ForegroundColorSpan(Chekrite.getParseColor());
        StyleSpan bold = new StyleSpan(Typeface.BOLD);
        ss.setSpan(color, 0, 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ss.setSpan(bold, 0, 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return ss;
    }

    //gets input from keyboard and updates the edit text view
    public void updateEditText(CharSequence newText) {
        if(newText != "/") {
            addNewText(newText.toString());
        } else {
            if(searchText.size()>0) {
                deleteOneChar();
            }
        }
    }

    //checks if edit text is empty, clears the array and set text to default text and color
    private void checkEditTextIsEmpty() {
        if (searchText.size() == 0) {
            editText.setText("type to search");
            editText.setTextColor(Color.parseColor("#dcdcdc"));
            editText.setTextSize(16);
            editText.setTypeface(null, Typeface.NORMAL);
            searchAssetList.clear();
            found.setVisibility(View.GONE);
            deleteAllBtn.setVisibility(View.GONE);
            mAdapter.notifyDataSetChanged();


        }
    }

    //adds new text to array and displays
    private void addNewText(String newText) {
        searchText.add(newText.toString());
        displayText();
        checkEditTextIsEmpty();
        for(int i = 0; i<searchAssetList.size(); i++){
        }

    }

    //handle deleting one character
    private void deleteOneChar() {
        searchText.remove(searchText.size() - 1);
        displayText();
        checkEditTextIsEmpty();
    }

    //handle delete all button press
    private void clearAllText() {
        searchText.clear();
        searchAssetList.clear();
        displayText();
        checkEditTextIsEmpty();
        mAdapter.notifyDataSetChanged();
    }


    //get string from arraylist and displays. Calls to API if text is >= 3
    private void displayText() {
         joined = TextUtils.join("", searchText);
            searchAssetList.clear();
            mAdapter.notifyDataSetChanged();
            new APIsTask(SearchAssetListener).execute("GET", APIs.SEARCH, joined, "");
            mAdapter.notifyDataSetChanged();
        if(searchText.size() != 0) {
            deleteAllBtn.setVisibility(View.VISIBLE);
            found.setVisibility(View.VISIBLE);
        }
        editText.setText(joined);
        editText.setTextColor(Chekrite.getParseColor());
        editText.setTextSize(24);
        editText.setTypeface(null, Typeface.BOLD);


    }

}

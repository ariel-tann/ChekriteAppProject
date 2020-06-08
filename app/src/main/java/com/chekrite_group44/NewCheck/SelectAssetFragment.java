/*
 * Date: 2020.6.8
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.NewCheck;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.chekrite_group44.AssetProperties.SelectAssetAssets;
import com.chekrite_group44.AssetProperties.SelectAssetData;
import com.chekrite_group44.HttpRequest.APIsListener;
import com.chekrite_group44.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectAssetFragment extends Fragment implements SelectAssetCategoryFragment.categoryListener, SelectAssetMakeFragment.makeListener,
        SelectAssetModelFragment.modelListener {

    private static final String TAG = "ARIEL";

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ArrayList<SelectAssetAssets>  filteredAssets = new ArrayList<>();
    public static ArrayList<SelectAssetData>  filtered_asset_classes = new ArrayList<>();




    APIsListener AssetsListener = new APIsListener() {
        @Override
        public void API_Completed(JSONObject jsonObject) {
            try {
                String status = (String) jsonObject.get("status");
                String message = (String) jsonObject.get("message");
                if (status.equals("success")) {
                    Log.d(TAG, "search asset success: " + message);
                    JSONArray data = jsonObject.getJSONArray("data");
                    if (data != null) {
                        Log.d(TAG, "data not null");

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject object = data.getJSONObject(i);
                            int id = object.getInt("id");
                            String make = object.getString("make");
                            String model = object.getString("model");
                            String category = object.getString("category");
                            int onTheFlyAssetsEnabled = object.getInt("on_the_fly_assets_enabled");
                            JSONArray assets = object.getJSONArray("asset");
                            if (assets != null) {
                                Log.d(TAG, "asset data not null");
                                for (int j = 0; j < assets.length(); j++) {
                                    JSONObject assetData = assets.getJSONObject(j);
                                    int assetId = assetData.getInt("id");
                                    String assetUnitNumber = assetData.getString("unit_number");
                                    String assetMake = assetData.getString("make");
                                    String assetModel = assetData.getString("model");
                                    String assetPhotoUrl = assetData.getString("photo");
                                    filteredAssets.add(new SelectAssetAssets(assetId, assetUnitNumber, assetMake, assetModel,
                                            assetPhotoUrl));
                                }

                            } else {
                                Log.d(TAG, "asset data null");
                            }
                            filtered_asset_classes.add(new SelectAssetData(id, make, model, category,
                                    onTheFlyAssetsEnabled, filteredAssets));
                            for(int x = 0; x < filtered_asset_classes.get(i).getAssets().size(); x++)
                            {
                                Log.d(TAG, "filtered asset class " + filtered_asset_classes.get(i).getAssets().get(x).getUnitNumber());

                            }
                            filteredAssets.clear();
                        }


                    } else {
                        Log.d(TAG, "data null");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_asset_main, container, false);
        ArrayList<SelectAssetData> selectAssetList = new ArrayList<>();

//        if(filtered_asset_classes.size() != 0) {
//            filtered_asset_classes.clear();
//        }

//        new APIsTask(AssetsListener).execute("GET", APIs.ASSETS,"","");

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.select_asset_framelayout, new SelectAssetCategoryFragment());

        transaction.commit();



        return view;
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        Fragment childFragment = new SelectAssetCategoryFragment();
//        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//        transaction.replace(R.id.select_asset_framelayout, childFragment, "taggies").commit();
//    }

    public void onAttach(Context context){
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }




    public void test(String fragType){
//        Intent intent = new Intent(this.getContext(), secondScreen.class);
//        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
        Log.d(TAG, "in test function ");
        addFragment(fragType);

    }

    public void addFragment(String fragType) {
        Fragment fragment;
        if (fragType.equals("make")) {
            fragment = new SelectAssetMakeFragment();
            fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.select_asset_framelayout,fragment,"demofragment").addToBackStack(null).commit();

        } else if (fragType.equals("model")) {
            fragment = new SelectAssetModelFragment();
            fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.select_asset_framelayout, fragment, "demofragment").addToBackStack(null).commit();

        } else if (fragType.equals("unit")) {
            fragment = new SelectAssetUnitFragment();
            fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.select_asset_framelayout, fragment, "demofragment").addToBackStack(null).commit();

        }

    }


    @Override
    public void goToMakeFrag() { }

    @Override
    public void goToModelFrag() { }


    @Override
    public void goToUnitFrag() { }
}

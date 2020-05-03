/*
 * Date: 2020.5.3
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Asset_Properties;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Data {
    private ArrayList<Asset_Class> asset_classes = new ArrayList<>();

    public Data(JSONObject input) throws JSONException {
        JSONArray data = input.getJSONArray("data");
        for(int i = 0; i<data.length();i++){
            Asset_Class asset_class = new Asset_Class((JSONObject) data.get(i));
            asset_classes.add(i, asset_class);
        }
    }

    public ArrayList<Asset_Class> getAsset_classes() {
        return asset_classes;
    }

    public void setAsset_classes(ArrayList<Asset_Class> asset_classes) {
        this.asset_classes = asset_classes;
    }
}

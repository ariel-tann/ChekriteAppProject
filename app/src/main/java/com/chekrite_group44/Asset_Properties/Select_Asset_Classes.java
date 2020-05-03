/*
 * Date: 2020.5.3
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Asset_Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Select_Asset_Classes {
    private ArrayList<Asset_Classes> asset_classes = new ArrayList<>();

    public Select_Asset_Classes(JSONObject input) throws JSONException {
        JSONArray data = input.getJSONArray("data");
        for(int i = 0; i<data.length();i++){
            Asset_Classes asset_classes = new Asset_Classes((JSONObject) data.get(i));
            this.asset_classes.add(i, asset_classes);
        }
    }

    public ArrayList<Asset_Classes> getAsset_classes() {
        return asset_classes;
    }

    public void setAsset_classes(ArrayList<Asset_Classes> asset_classes) {
        this.asset_classes = asset_classes;
    }
}

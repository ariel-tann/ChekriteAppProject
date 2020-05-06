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

public class Search_Asset {
    int id;
    String make;
    String model;
    String category;
    int on_the_fly_assets_enabled;
    ArrayList<Asset> assets = new ArrayList<>();

    public Search_Asset(JSONObject input) throws JSONException {
        JSONObject data = input.getJSONObject("data");
        this.id = data.getInt("id");
        this.make = data.getString("make");
        this.model = data.getString("model");
        this.category = data.getString("category");
        this.on_the_fly_assets_enabled = data.getInt("on_the_fly_assets_enabled");

        // add every asset in ArrayList
        JSONArray json_assets = data.getJSONArray("asset");
        for(int i = 0; i<json_assets.length();i++){
            Asset an_asset = new Asset((JSONObject) json_assets.get(i));
            assets.add(i, an_asset);
        }
    }
}

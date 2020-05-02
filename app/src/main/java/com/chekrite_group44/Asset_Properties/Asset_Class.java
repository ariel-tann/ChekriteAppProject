/*
 * Date: 2020.5.3
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Asset_Properties;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Asset_Class {
    private int id;
    private String make;
    private String model;
    private String category;
    private boolean on_the_fly_assets_enabled;
    private ArrayList<Asset> assets;

    public Asset_Class(JSONObject asset_class) throws JSONException {
        this.id = asset_class.getInt("id");
        this.make = asset_class.getString("make");
        this.model = asset_class.getString("model");
        this.category = asset_class.getString("category");
        this.on_the_fly_assets_enabled = asset_class.getBoolean("on_the_fly_assets_enabled");
        this.assets = assets;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isOn_the_fly_assets_enabled() {
        return on_the_fly_assets_enabled;
    }

    public void setOn_the_fly_assets_enabled(boolean on_the_fly_assets_enabled) {
        this.on_the_fly_assets_enabled = on_the_fly_assets_enabled;
    }

    public ArrayList<Asset> getAssets() {
        return assets;
    }

    public void setAssets(ArrayList<Asset> assets) {
        this.assets = assets;
    }
}

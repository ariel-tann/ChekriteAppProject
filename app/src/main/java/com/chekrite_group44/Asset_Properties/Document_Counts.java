/*
 * Date: 2020.5.3
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Asset_Properties;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class Document_Counts {
    private int asset;
    private int asset_class;
    private int site;

    public Document_Counts(JSONObject doc_counts) throws JSONException {
        this.asset = doc_counts.getInt("asset");
        this.asset_class = doc_counts.getInt("asset_class");
        this.site = doc_counts.getInt("site");
    }

    public int getAsset() {
        return asset;
    }

    public int getAsset_class() {
        return asset_class;
    }

    public int getSite() {
        return site;
    }

    public void setAsset(int asset) {
        this.asset = asset;
    }

    public void setAsset_class(int asset_class) {
        this.asset_class = asset_class;
    }

    public void setSite(int site) {
        this.site = site;
    }
}

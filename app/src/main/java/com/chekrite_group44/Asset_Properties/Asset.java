/*
 * Date: 2020.5.3
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Asset_Properties;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class Asset {

    private int id;
    private String unit_number;
    private String make;
    private String model;
    private String photo;
    private Document_Counts document_counts;

    public Asset(JSONObject asset) throws JSONException {

        this.id = asset.getInt("id");
        unit_number = asset.getString("unit_number");
        this.make = asset.getString("make");
        this.model = asset.getString("model");
        this.photo = asset.getString("photo");
        this.document_counts = new Document_Counts(asset.getJSONObject("document_counts"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnit_number() {
        return unit_number;
    }

    public void setUnit_number(String unit_number) {
        this.unit_number = unit_number;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Document_Counts getDocument_counts() {
        return document_counts;
    }

    public void setDocument_counts(Document_Counts document_counts) {
        this.document_counts = document_counts;
    }
}

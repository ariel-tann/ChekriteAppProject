/*
 * Date: 2020.5.3
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.AssetProperties;

import org.json.JSONException;
import org.json.JSONObject;

public class Asset {

    private int id;
    private String unit_number;
    private String make;
    private String model;
    private String photo;
    private DocumentCounts document_counts;

    public Asset(JSONObject asset) throws JSONException {

        this.id = asset.getInt("id");
        unit_number = asset.getString("unit_number");
        this.make = asset.getString("make");
        this.model = asset.getString("model");
        this.photo = asset.getString("photo");
        this.document_counts = new DocumentCounts(asset.getJSONObject("document_counts"));
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

    public DocumentCounts getDocument_counts() {
        return document_counts;
    }

    public void setDocument_counts(DocumentCounts document_counts) {
        this.document_counts = document_counts;
    }
}

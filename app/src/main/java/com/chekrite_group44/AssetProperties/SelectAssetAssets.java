/*
 * Date: 2020.6.8
 * This file is created by Kai.
 * Summary:
 */

/*
 * Date: 2020.5.3
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.AssetProperties;

import org.json.JSONException;
import org.json.JSONObject;

public class SelectAssetAssets {

    private int id;
    private String unit_number;
    private String make;
    private String model;
    private String photo;

    public SelectAssetAssets(int id, String unit_number, String make, String model, String photo) {

        this.id = id;
        this.unit_number = unit_number;
        this.make = make;
        this.model = model;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public String getUnitNumber() {
        return unit_number;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getPhoto() {
        return photo;
    }


}

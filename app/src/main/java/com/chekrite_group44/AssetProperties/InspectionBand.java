/*
 * Date: 2020.5.11
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.AssetProperties;

import org.json.JSONException;
import org.json.JSONObject;

public class InspectionBand {
    int id;
    int upper_step;
    int status;
    String label = "";
    String color;
    int display_extra_info;
    public InspectionBand(JSONObject jbans) throws JSONException {
        id = jbans.getInt("id");
        upper_step = jbans.getInt("upper_step");
        status = jbans.getInt("status");
        label = jbans.getString("label");
        color = jbans.getString("color");
        display_extra_info = jbans.getInt("display_extra_info");
    }

    public int getId() {
        return id;
    }

    public int getUpper_step() {
        return upper_step;
    }

    public int getStatus() {
        return status;
    }

    public String getColor() {
        return color;
    }

    public int getDisplay_extra_info() {
        return display_extra_info;
    }

    public String getLabel() {
        return label;
    }
}

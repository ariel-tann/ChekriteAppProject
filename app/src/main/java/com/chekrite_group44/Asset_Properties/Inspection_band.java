/*
 * Date: 2020.5.11
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Asset_Properties;

import org.json.JSONException;
import org.json.JSONObject;

public class Inspection_band {
    int id;
    int upper_step;
    int status;
    //label: null, this one is null, so we're not gg to create now
    String color;
    int display_extra_info;
    public Inspection_band(JSONObject jbans) throws JSONException {
        id = jbans.getInt("id");
        upper_step = jbans.getInt("upper_step");
        status = jbans.getInt("status");
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
}

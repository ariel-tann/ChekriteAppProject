/*
 * Date: 2020.5.6
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.AssetProperties;

import org.json.JSONException;
import org.json.JSONObject;

public class InspectionButton {
    int id;
    String label;
    int status;
    String color;
    int display_extra_info;
    String value;
    int order;

    public InspectionButton(JSONObject button) throws JSONException {
        id = button.getInt("id");
        label = button.getString("label");
        status = button.getInt("status");
        color = button.getString("color");
        display_extra_info = button.getInt("display_extra_info");
        value = button.getString("value");
        order = button.getInt("order");
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getColor() {
        return color;
    }

    public int getDisplay_extra_info() {
        return display_extra_info;
    }

    public String getValue() {
        return value;
    }

    public int getOrder() {
        return order;
    }

    public int getStatus() {
        return status;
    }
}

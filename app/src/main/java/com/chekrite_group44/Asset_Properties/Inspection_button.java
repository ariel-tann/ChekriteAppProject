/*
 * Date: 2020.5.6
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Asset_Properties;

import org.json.JSONException;
import org.json.JSONObject;

public class Inspection_button {
    int id;
    String label;
    String color;
    int display_extra_info;
    String value;
    int order;

    public Inspection_button(JSONObject button) throws JSONException {
        id = button.getInt("id");
        label = button.getString("label");
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
}

/*
 * Date: 2020.5.5
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Asset_Properties;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Inspection_checklist {
    private int id;
    private String name;
//    abort_tolerance: null,
//    supervisor_tolerance: null,
//    randomise_button_order: false

    public Inspection_checklist(JSONObject object) throws JSONException {
        id = object.getInt("id");
        name = object.getString("name");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

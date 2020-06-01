/*
 * Date: 2020.5.5
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.AssetProperties;

import org.json.JSONException;
import org.json.JSONObject;

public class InspectionChecklist {
    private int id;
    private String name;
//    abort_tolerance: null,
//    supervisor_tolerance: null,
//    randomise_button_order: false

    public InspectionChecklist(JSONObject object) throws JSONException {
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

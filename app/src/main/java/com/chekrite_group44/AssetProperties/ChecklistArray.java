/*
 * Date: 2020.5.3
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.AssetProperties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChecklistArray {
    private ArrayList<Checklist> checklists = new ArrayList<>();

    public ChecklistArray(JSONObject input) throws JSONException {
        JSONArray data = input.getJSONArray("data");
        for(int i = 0; i<data.length();i++){
            Checklist checklist = new Checklist((JSONObject) data.get(i));
            checklists.add(i, checklist);
        }

    }

    public ArrayList<Checklist> getChecklists() {
        return checklists;
    }
}

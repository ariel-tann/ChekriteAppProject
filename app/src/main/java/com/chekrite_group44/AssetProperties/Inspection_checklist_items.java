/*
 * Date: 2020.5.5
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.AssetProperties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Inspection_checklist_items {
    private ArrayList<Inspection_checklist_item> checklists = new ArrayList<>();
    public Inspection_checklist_items(JSONArray checklist_items) throws JSONException {
        for(int i = 0; i<checklist_items.length();i++){
            Inspection_checklist_item checklist = new Inspection_checklist_item((JSONObject) checklist_items.get(i));
            checklists.add(i, checklist);
        }

    }

    public ArrayList<Inspection_checklist_item> getChecklists() {
        return checklists;
    }
}

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

public class InspectionChecklistItems {
    private ArrayList<InspectionChecklistItem> checklists = new ArrayList<>();
    public InspectionChecklistItems(JSONArray checklist_items) throws JSONException {
        for(int i = 0; i<checklist_items.length();i++){
            InspectionChecklistItem checklist = new InspectionChecklistItem((JSONObject) checklist_items.get(i));
            checklists.add(i, checklist);
        }

    }

    public ArrayList<InspectionChecklistItem> getChecklists() {
        return checklists;
    }
}

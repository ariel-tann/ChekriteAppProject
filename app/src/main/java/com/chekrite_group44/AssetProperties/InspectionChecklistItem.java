/*
 * Date: 2020.5.5
 * This file is created by Kai.
 * Summary:
 */
package com.chekrite_group44.AssetProperties;

import org.json.JSONException;
import org.json.JSONObject;

public class InspectionChecklistItem {
    int id;
    String type;
    String name;
    String description;
    int parent_id;
    int left;
    int right;
    int order;
    InspectionControl control;
    public InspectionChecklistItem(JSONObject checklist_item) throws JSONException {
        id = checklist_item.getInt("id");
        type = checklist_item.getString("type");
        name = checklist_item.getString("name");
        description = checklist_item.getString("description");
        parent_id = checklist_item.getInt("parent_id");
        left = checklist_item.getInt("left");
        right = checklist_item.getInt("right");
        order = checklist_item.getInt("order");
        control = new InspectionControl(checklist_item.getJSONObject("control"));
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getParent_id() {
        return parent_id;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getOrder() {
        return order;
    }

    public InspectionControl getControl() {
        return control;
    }
}


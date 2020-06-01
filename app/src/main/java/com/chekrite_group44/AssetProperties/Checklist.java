/*
 * Date: 2020.5.3
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.AssetProperties;

import org.json.JSONException;
import org.json.JSONObject;

public class Checklist {
    private int id;
    private String name;
    private int version;
    private String category;

    public Checklist(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getInt("id");
        name = jsonObject.getString("name");
        version = jsonObject.getInt("version");
        category = jsonObject.getString("category");

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

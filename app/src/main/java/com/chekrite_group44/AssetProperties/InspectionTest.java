/*
 * Date: 2020.5.7
 * This file is created by Kai.
 * Summary:
 */

/*
 * Date: 2020.5.7
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.AssetProperties;

import org.json.JSONException;
import org.json.JSONObject;

public class InspectionTest {
    int id;
    String guid;

    public InspectionTest(JSONObject test) throws JSONException {
        id = test.getInt("id");
        guid = test.getString("guid");
    }

    public int getId() {
        return id;
    }

    public String getGuid() {
        return guid;
    }
}

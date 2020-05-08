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

package com.chekrite_group44.Asset_Properties;

import org.json.JSONException;
import org.json.JSONObject;

public class Inspection_test {
    int id;
    String guid;

    public Inspection_test(JSONObject test) throws JSONException {
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

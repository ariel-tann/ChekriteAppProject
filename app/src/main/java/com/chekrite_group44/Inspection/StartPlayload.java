/*
 * Date: 2020.5.6
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Inspection;

import android.content.Context;

import com.chekrite_group44.MetaData.MetaData_Asset;

import org.json.JSONException;
import org.json.JSONObject;

public class StartPlayload {
    JSONObject payload = new JSONObject();

    public String StartAPI_payload(Context context,
                                       String checklist_id, int asset_id, String asset_selection) {

        MetaData_Asset metaData = new MetaData_Asset(context);
        double lat = metaData.getDevice_lat();
        double lng = metaData.getDevice_lng();
        JSONObject jsonObject= metaData.getjObject();

        try {
            payload.put("checklist_id", checklist_id);
            payload.put("asset_id", asset_id);
            payload.put("lat",lat);
            payload.put("lng",lng);
            payload.put("asset_selection", asset_selection);
            payload.put("meta", jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return payload.toString();
    }
}

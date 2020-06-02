/*
 * Date: 2020.5.7
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Inspection;

import com.chekrite_group44.AssetProperties.Inspection_test;
import com.chekrite_group44.MetaData.MetaData_Asset;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class SubmitPlayload {
    int id;
    int result = 0;
    long duration;
    int score = 0;
    boolean supervisor_approval = true;
    JSONObject payload;
    public SubmitPlayload(Inspection_test test, long start, long end, MetaData_Asset metaData) throws JSONException {
        id = test.getId();
        duration = TimeUnit.MILLISECONDS.toSeconds(end - start);
        payload = new JSONObject();
        payload.put("id", id);
        payload.put("result", result);
        payload.put("duration", duration);
        payload.put("score", score);
        payload.put("supervisor_approval", supervisor_approval);
        payload.put("meta",metaData.getjObject());
    }

    public JSONObject getPayload() {
        return payload;
    }
}

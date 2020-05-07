/*
 * Date: 2020.5.7
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Tests;
import android.util.Log;

import com.chekrite_group44.Asset_Properties.Inspection_checklist_item;
import com.chekrite_group44.Asset_Properties.Inspection_test;
import com.chekrite_group44.MetaData.MetaData;
import com.chekrite_group44.MetaData.MetaData_Asset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Response_payload {
    private static final String TAG = "Response_payload";
    double button_value;    // indicate which button being selected
    Inspection_checklist_item mItem;
    // ChekRite response require variables
    int checklist_item_id;
    int control_button_id = 0;
    int control_gauge_band_id = 0;
    String date_value;
    String duration;
    int evaluation_result = 0;
    JSONArray extra_infos = new JSONArray();
    double gauge_value;
    double lat;
    double lng;
    int left;
    int right;
    int numeric_value = -1;
    int order;
    int parent_id;
    int repetitions = 0;
    String response_timestamp;
    String score = "0";
    int status;
    int sub_test_id = 0;
    JSONArray subq_responses = new JSONArray();
    int test_heartbeat_id = 0;
    int test_id;
    int text_value = 0;
    String type;
    JSONObject payload;


    public Response_payload(Inspection_checklist_item item, Inspection_test test, int btn_type, int btn_order,
                            double button_value, long start, long end, MetaData_Asset metaData) throws JSONException {
        this.button_value = button_value;
        this.mItem = item;
        checklist_item_id = item.getId();
        switch (btn_type){
            case Control_TYPE.button:
                control_button_id = item.getControl().getButtons().get(btn_order).getId();
                break;
            case Control_TYPE.gauge:
                control_gauge_band_id = item.getControl().getButtons().get(btn_order).getId();
                break;
        }
        date_value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        duration = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(end - start));
        lat = metaData.getDevice_lat();
        lng = metaData.getDevice_lng();
        left = item.getLeft();
        right = item.getRight();
        order = item.getOrder();
        parent_id = item.getParent_id();

        response_timestamp = String.valueOf(System.currentTimeMillis()/1000);
        // get button status
        status = item.getControl().getButtons().get(btn_order).getStatus();
        test_id = test.getId();
        type = item.getType();

        payload = new JSONObject();
        payload.put("checklist_item_id", checklist_item_id);
        payload.put("control_button_id", control_button_id == 0?JSONObject.NULL:control_button_id);
        payload.put("control_gauge_band_id", control_gauge_band_id==0?JSONObject.NULL:control_gauge_band_id);
        payload.put("date_value", date_value);
        payload.put("duration", duration);
        payload.put("evaluation_result", evaluation_result== 0?JSONObject.NULL:evaluation_result);
        payload.put("extra_infos", extra_infos);
        payload.put("gauge_value", control_gauge_band_id==0?JSONObject.NULL:gauge_value);
        payload.put("lat", lat);
        payload.put("lng", lng);
        payload.put("left", left);
        payload.put("right", right);
        payload.put("numeric_value", numeric_value == -1?JSONObject.NULL:numeric_value);
        payload.put("order", order);
        payload.put("parent_id", parent_id);
        payload.put("repetitions", repetitions == 0?JSONObject.NULL:repetitions);
        payload.put("response_timestamp", response_timestamp);
        payload.put("score", score);
        payload.put("status", status);
        payload.put("sub_test_id", sub_test_id == 0?JSONObject.NULL:sub_test_id);
        payload.put("subq_responses", subq_responses);
        payload.put("test_heartbeat_id", test_heartbeat_id == 0?JSONObject.NULL:test_heartbeat_id);
        payload.put("test_id", test_id);
        payload.put("text_value", text_value == 0?JSONObject.NULL:text_value);
        payload.put("type", type);
        payload.put("meta",metaData.getjObject());
        Log.d(TAG, payload.toString());
    }
    public JSONObject getPayload() {
        return payload;
    }

}

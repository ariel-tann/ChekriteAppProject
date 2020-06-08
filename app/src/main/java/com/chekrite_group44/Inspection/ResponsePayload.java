/*
 * Date: 2020.5.7
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Inspection;
import android.util.Log;

import com.chekrite_group44.AssetProperties.ControlType;
import com.chekrite_group44.AssetProperties.InspectionChecklistItem;
import com.chekrite_group44.AssetProperties.InspectionGauge;
import com.chekrite_group44.AssetProperties.InspectionTest;
import com.chekrite_group44.MetaData.MetaDataAsset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ResponsePayload {
    private static final String TAG = "Payload_Response";
    InspectionChecklistItem mItem;
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
    String text_value = "";
    String type;
    JSONObject payload;


    public ResponsePayload(InspectionChecklistItem item, InspectionTest test, String type, int btn_order, String txt_value,
                           double gauge_value, long start, long end, MetaDataAsset metaData) throws JSONException {
        this.gauge_value = gauge_value;
        this.mItem = item;
        this.text_value = txt_value;
        checklist_item_id = item.getId();
        switch (type){
            case ControlType.BUTTONS:
                control_button_id = item.getControl().getButtons().get(btn_order).getId();
                status = item.getControl().getButtons().get(btn_order).getStatus();
                break;
            case ControlType.GAUGE:
                // get status using condition of upper bond and lower bond
                control_gauge_band_id = item.getControl().getGauges().get(btn_order).getId();
                InspectionGauge gauge = mItem.getControl().getGauges().get(0);
                // search status in band using current value
                long ratio = Math.round(gauge.getUpper()/gauge.getMarks_count());
                int val = (int)gauge_value / (int)ratio;
                for(int i = 0; i < gauge.getBands().size(); i++){
                    int prev = 0;
                    if(i != 0)
                        prev = gauge.getBands().get(i-1).getUpper_step();
                    if (val >= prev
                            && val <= gauge.getBands().get(i).getUpper_step()){
                        // get status
                        status = gauge.getBands().get(i).getStatus();
                        Log.d(TAG, "status: "+status);
                    }
                }
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

        response_timestamp = String.format("%.3f",System.currentTimeMillis()/1000.0);
        // get button status

        test_id = test.getId();
        type = item.getType();

        payload = new JSONObject();
        payload.put("checklist_item_id", checklist_item_id);
        payload.put("control_button_id", control_button_id == 0?JSONObject.NULL:control_button_id);
        payload.put("control_gauge_band_id", control_gauge_band_id==0?JSONObject.NULL:control_gauge_band_id);
        payload.put("date_value", JSONObject.NULL);
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
        payload.put("text_value", text_value.length() == 0?JSONObject.NULL:text_value);
        payload.put("type", type);
        payload.put("meta",metaData.getjObject());
    }
    public JSONObject getPayload() {
        return payload;
    }

}

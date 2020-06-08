/*
 * Date: 2020.5.6
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.AssetProperties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InspectionControl {
    int id;
    String type;
    String name;
    String buttons_shape;
    ArrayList<InspectionButton> buttons = new ArrayList<>();
    ArrayList<InspectionGauge> gauges = new ArrayList<>();
    public InspectionControl(JSONObject control) throws JSONException {

        type = control.getString("type");
        name = control.getString("name");
        //
        switch (type){
            case ControlType.BUTTONS:
                id = control.getInt("id");
                // button question
                buttons_shape = control.getString("buttons_shape");
                // get a button in buttons
                JSONArray jbuttons = control.getJSONArray(ControlType.BUTTONS);
                for(int i = 0; i<jbuttons.length();i++) {
                    InspectionButton button = new InspectionButton(jbuttons.getJSONObject(i));
                    buttons.add(i, button);
                }
                break;
            case ControlType.GAUGE:
                // gauge question
                JSONArray jgauge = control.getJSONArray(ControlType.GAUGE);
                for(int i = 0; i<jgauge.length();i++) {
                    InspectionGauge gauge = new InspectionGauge(jgauge.getJSONObject(i));
                    gauges.add(i, gauge);
                }
                break;
        }
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

    public String getButtons_shape() {
        return buttons_shape;
    }

    public ArrayList<InspectionButton> getButtons() {
        return buttons;
    }

    public ArrayList<InspectionGauge> getGauges() {
        return gauges;
    }
}

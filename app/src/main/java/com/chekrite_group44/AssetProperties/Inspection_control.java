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

public class Inspection_control {
    int id;
    String type;
    String name;
    String buttons_shape;
    ArrayList<Inspection_button> buttons = new ArrayList<>();
    ArrayList<Inspection_gauge> gauges = new ArrayList<>();
    public Inspection_control(JSONObject control) throws JSONException {

        type = control.getString("type");
        name = control.getString("name");
        //
        switch (type){
            case Control_Type.BUTTONS:
                id = control.getInt("id");
                // button question
                buttons_shape = control.getString("buttons_shape");
                // get a button in buttons
                JSONArray jbuttons = control.getJSONArray(Control_Type.BUTTONS);
                for(int i = 0; i<jbuttons.length();i++) {
                    Inspection_button button = new Inspection_button(jbuttons.getJSONObject(i));
                    buttons.add(i, button);
                }
                break;
            case Control_Type.GAUGE:
                // gauge question
                JSONArray jgauge = control.getJSONArray(Control_Type.GAUGE);
                for(int i = 0; i<jgauge.length();i++) {
                    Inspection_gauge gauge = new Inspection_gauge(jgauge.getJSONObject(i));
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

    public ArrayList<Inspection_button> getButtons() {
        return buttons;
    }

    public ArrayList<Inspection_gauge> getGauges() {
        return gauges;
    }
}

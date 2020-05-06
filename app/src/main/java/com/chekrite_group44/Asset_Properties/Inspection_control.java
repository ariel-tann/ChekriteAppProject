/*
 * Date: 2020.5.6
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Asset_Properties;

import android.util.Log;

import com.chekrite_group44.Tests.Inspection;

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
    public Inspection_control(JSONObject control) throws JSONException {
        id = control.getInt("id");
        type = control.getString("type");
        name = control.getString("name");
        buttons_shape = control.getString("buttons_shape");
        // get a button in buttons
        JSONArray jbuttons = control.getJSONArray("buttons");
        for(int i = 0; i<jbuttons.length();i++){
            Inspection_button button = new Inspection_button(jbuttons.getJSONObject(i));
            buttons.add(i,button);
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
}

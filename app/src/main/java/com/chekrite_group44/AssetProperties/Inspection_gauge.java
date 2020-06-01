/*
 * Date: 2020.5.11
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.AssetProperties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Inspection_gauge {
    int id;
    int display_marks;
    int marks_count;
    String lower;
    String upper;
    String needle_labels;
    ArrayList<InspectionBand> bands = new ArrayList<>();

    public Inspection_gauge(JSONObject jgauage) throws JSONException {
        id = jgauage.getInt("id");
        display_marks = jgauage.getInt("display_marks");
        marks_count = jgauage.getInt("marks_count");
        lower = jgauage.getString("lower");
        upper = jgauage.getString("upper");
        needle_labels = jgauage.getString("needle_labels");
        JSONArray jbands = jgauage.getJSONArray("bands");
        for(int i = 0; i<jbands.length();i++) {
            InspectionBand band = new InspectionBand(jbands.getJSONObject(i));
            bands.add(i, band);
        }


    }

    public int getId() {
        return id;
    }

    public int getDisplay_marks() {
        return display_marks;
    }

    public int getMarks_count() {
        return marks_count;
    }

    public Double getLower() {
        return Double.valueOf(lower);
    }

    public Double getUpper() {
        return Double.valueOf(upper);
    }

    public String getNeedle_labels() {
        return needle_labels;
    }

    public ArrayList<InspectionBand> getBands() {
        return bands;
    }
}

/*
 * Date: 2020.4.4
 * This file is created by Kai.
 * Summary:
 */

/*
 * Date: 2020.4.3
 * This file is created by Kai.
 * Summary:
 */

/*
 * Date: 2020.4.3
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

public class Chekrite extends Application {
    public static final String SHARED_PREFS = "sharedPrefs";
    public static SharedPreferences pref;
    public static SharedPreferences.Editor editor;
    // default color
    public static String Color  = "#65cb81";
    public Chekrite() {
        // initial vars
    }

    @Override
    public void onCreate() {
        super.onCreate();
        pref = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        editor = pref.edit();
    }
    public static void putString(String name, String data){
        editor.putString(name, data);
        editor.apply();
    }
    public static String getString(String name){
        return pref.getString(name, "");
    }
    public static String getColor(){
        return pref.getString("highlight_colour", Color);
    }
    public static int getParseColor(){
        return android.graphics.Color.parseColor(pref.getString("highlight_colour", Color));
    }
    public static void rmPref(){
        editor.clear();
        editor.commit(); // commit changes
    }
}

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

public class Chekrite extends Application {
    public static final String SHARED_PREFS = "sharedPrefs";
    public static SharedPreferences pref;
    public static SharedPreferences.Editor editor;
    public static String color = "#65cb81"; // default color
    
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
        // put string in share preference
        editor.putString(name, data);
        editor.apply();
    }
    public static String getString(String name){
        // get string in share preference
        return pref.getString(name, "");
    }
    public static String getColor(){
        // get default color code
        return pref.getString("highlight_colour", color);
    }
    public static int getParseColor(){
        // convert string color to int
        return android.graphics.Color.parseColor(pref.getString("highlight_colour", color));
    }
    public static void rmPref(){
        // clear and remove share preference
        editor.clear();
        editor.commit(); // commit changes
    }
}

/*
 * Date: 2020.5.5
 * This file is created by Kai.
 * Summary:
 */

/*
 * Date: 2020.4.10
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.MetaData;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.chekrite_group44.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;

import static android.content.Context.BATTERY_SERVICE;

//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;

public class MetaData_Asset {
    // date of app build
    private static final String TAG = "MetaData_Asset";
    String app_build;
    String app_version;
    String internet_capabilities;
    double device_battery_level;
    double device_lat = 0.0d;
    double device_lng = 0.0d;
    int device_memory;
    String device_model;
    String os_version;
    JSONObject jObject = new JSONObject();

    public double getDevice_lat() {
        return device_lat;
    }

    public double getDevice_lng() {
        return device_lng;
    }

    public JSONObject getjObject() {
        return jObject;
    }

    public MetaData_Asset(Context context) {

        String pattern = "yyyyMMdd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        this.app_build = simpleDateFormat.format(BuildConfig.BUILD_TIME);
        this.app_version = BuildConfig.VERSION_NAME;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info.getType() == ConnectivityManager.TYPE_WIFI)
            internet_capabilities = "wifi";
        else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            internet_capabilities = "cellular";
        } else {
            internet_capabilities = "unknown";
        }

        BatteryManager bm = (BatteryManager) context.getSystemService(BATTERY_SERVICE);
        device_battery_level = (double) bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

        LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // permission denied
            Log.d(TAG, "Location denied");
            device_lat = 0.0d;
            device_lng = 0.0d;
        }else{
            Location location = locationManager.getLastKnownLocation(bestProvider);
            if(location != null) {
                device_lat = location.getLatitude();
                device_lng = location.getLongitude();
            }
        }
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        device_memory = (int)(blockSize*totalBlocks/ (1024 * 1024 * 1024.0));

        device_model = Build.MODEL;
        os_version = Build.VERSION.RELEASE;

        // create json file
        try {
            jObject.put("device_battery_level",  device_battery_level/100);
            jObject.put("app_version", app_version);
            jObject.put("app_build", app_build);
            jObject.put("os","Android");
            jObject.put("os_version", os_version);
            jObject.put("device_model", device_model);
            jObject.put("device_memory", device_memory);
            jObject.put("internet_capabilities",internet_capabilities);
            jObject.put("device_lat",device_lat);
            jObject.put("device_lng",device_lng);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}

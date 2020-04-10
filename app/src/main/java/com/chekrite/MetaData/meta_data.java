/*
 * Date: 2020.4.10
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite.MetaData;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.chekrite.BuildConfig;

import java.io.File;
import java.text.SimpleDateFormat;

import static android.content.Context.BATTERY_SERVICE;

public class meta_data {
    String app_build;
    String app_version;
    String connection;
    int device_battery_level;
    double device_lat;
    double device_lng;
    int device_memory;
    String device_model;
    String internet_capabilities;


    public meta_data(Context context) {

        String pattern = "yyyyMMdd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        this.app_build = simpleDateFormat.format(BuildConfig.BUILD_TIME);
        this.app_version = BuildConfig.VERSION_NAME;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info.getType() == ConnectivityManager.TYPE_WIFI)
            connection = "Wi-Fi";
        else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            connection = "Cellular";
        } else {
            connection = "unknown";
        }
        BatteryManager bm = (BatteryManager) context.getSystemService(BATTERY_SERVICE);
        device_battery_level = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // permission denied
        }else{
            Location location = locationManager.getLastKnownLocation("gps");
            device_lat = location.getLatitude();
            device_lng = location.getLongitude();

        }
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        device_memory = (int)(blockSize*totalBlocks/ 1073741824.0);
        device_model = Build.MODEL;
        //TODO internet_capabilities
    }
}

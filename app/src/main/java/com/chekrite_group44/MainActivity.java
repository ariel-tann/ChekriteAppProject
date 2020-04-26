package com.chekrite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.chekrite.MetaData.MetaData;
import com.chekrite.PinView.Chekrite_PinView;
import com.chekrite.PinView.PinListener;
import com.chekrite.dashBoard.Dashboard;
import com.chekrite.dashBoard.WelcomeSplash;
import com.chekrite.http_request.APIsListener;
import com.chekrite.http_request.APIsTask;
import com.chekrite.permission.Permission;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks{
    private Permission mPermission;
    private Button mBtnSubmit;
    private static final String FILE_NAME = "pair.txt";

    private APIsListener apIsListener = new APIsListener() {
        @Override
        public void API_Completed(JSONObject jsonObject) {
            // execute this when transaction finished
            try {
                String status = (String) jsonObject.get("status");
                if(status.equals("success")){
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONObject device = data.getJSONObject("device");
                    String udif = device.getString("udid");
                    String auth_code = device.getString("auth_code");
                    JSONObject company = data.getJSONObject("company");
                    String company_name = company.getString("name");
                    JSONObject site = data.getJSONObject("site");
                    String site_name = site.getString("name");

                    JSONObject pair_credent = new JSONObject();
                    pair_credent.put("udif", udif);
                    pair_credent.put("auth_code", auth_code);
                    pair_credent.put("company", company_name);
                    pair_credent.put("site", site_name);
                    FileOutputStream outputStream;
                    try {
                        outputStream = openFileOutput(FILE_NAME , Context.MODE_PRIVATE);
                        outputStream.write(pair_credent.toString().getBytes());
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    openLoginScreen();
                }else{
                    // TODO pair fail

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };
    // set a listener for pin submit
    private PinListener mPinListen = new PinListener() {
        @Override
        public void onSubmit(String pin) {
            JSONObject jsonObject;
            getLocation();
            try{
             MetaData metaData = new MetaData(getApplicationContext());
            // Pair Device
            jsonObject= metaData.get();
            jsonObject.put("pairing_code", pin);

//          Log.d("KAI", jsonObject.toString());
            new APIsTask(apIsListener).execute("POST","pair",jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private View.OnClickListener submitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//             create a fragment to show PinView
            Chekrite_PinView pinView = new Chekrite_PinView(Chekrite_PinView.SETUP, mPinListen);
            pinView.show(getSupportFragmentManager(),"pin");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO check if auth file exists
        setContentView(R.layout.activity_main);
        File file = getBaseContext().getFileStreamPath(FILE_NAME);
        if(file.exists()){
            openLoginScreen();
        }
        mPermission = new Permission(this, this);
        mPermission.RequestPermissions();
        mBtnSubmit = findViewById(R.id.setupApp_btn);
        mBtnSubmit.setOnClickListener(submitListener);

    }

    public void openLoginScreen() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
//
//    public void openDashboardScreen() {
//        Intent intent = new Intent(this, Dashboard.class);
//        startActivity(intent);
//    }
//    public void openWelcomeSplash() {
//        Intent intent = new Intent(this, WelcomeSplash.class);
//        startActivity(intent);
//    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.d("KAI", this.toString()+"Permission Deny: "+perms.toString());

    }

    @Override
    public void onRationaleAccepted(int requestCode) {

    }

    @Override
    public void onRationaleDenied(int requestCode) {

    }

    public void getLocation()
    {
        long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
        long FASTEST_INTERVAL = 2000; /* 2 sec */
        LocationRequest mLocationRequest =new LocationRequest();
        FusedLocationProviderClient fusedLocationProviderClient= getFusedLocationProviderClient(this);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
       fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        // do work here
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());


    }
    public void onLocationChanged(Location location) {
        double latitude=location.getLatitude();
        double longitude=location.getLongitude();
        MetaData metaData=new MetaData(latitude,longitude);

        getLastLocation();

    }
    public void getLastLocation() {
        // Get last known recent location using new Google Play Services SDK (v11+)
        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(this);

        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // GPS location can be null if GPS is switched off
                        if (location != null) {
                            onLocationChanged(location);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MapDemoActivity", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }
}

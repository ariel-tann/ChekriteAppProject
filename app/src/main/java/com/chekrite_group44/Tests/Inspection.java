/*
 * Date: 2020.5.5
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Tests;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chekrite_group44.MetaData.MetaData_Asset;
import com.chekrite_group44.R;
import com.chekrite_group44.http_request.APIs;
import com.chekrite_group44.http_request.APIsListener;
import com.chekrite_group44.http_request.APIsTask;
import com.chekrite_group44.permission.Permission;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class Inspection extends AppCompatActivity
        implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks{

    public static final String SHARED_PREFS = "sharedPrefs";
    private Permission mPermission;
    APIsListener StartListener = new APIsListener() {
        @Override
        public void API_Completed(JSONObject jsonObject) {
            Log.d("KAI","START: \n"+jsonObject.toString());
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection);
        mPermission = new Permission(this, this);
        mPermission.RequestPermissions();

        // Display company name received from API
        SharedPreferences pref = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String profile_link = pref.getString("profile_photo", "");

        // display profile photo
        ImageView profile = findViewById(R.id.inspection_profile);
        if(profile_link.length() > 0)
            Glide.with(getApplicationContext()).load(profile_link).apply(RequestOptions.circleCropTransform()).into(profile);

        // get color and set to btn background
        String highlight_colour = pref.getString("highlight_colour", "#65cb81");
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.inspection_toolbar);
        toolbar.setBackgroundColor(Color.parseColor(highlight_colour));
        // get info from previous class
        String checklist_id=getIntent().getStringExtra("checklist_id");
        int asset_id = getIntent().getIntExtra("asset_id", 0);
        String asset_selection = getIntent().getStringExtra("asset_selection");
        String payload = prepare_payload(checklist_id,asset_id,asset_selection);
        new APIsTask(StartListener, getApplicationContext()).execute("POST", APIs.START,"",payload);

    }
    private String prepare_payload(String checklist_id, int asset_id, String asset_selection){

        MetaData_Asset metaData = new MetaData_Asset(getApplicationContext());

        double lat = metaData.getDevice_lat();
        double lng = metaData.getDevice_lng();

        JSONObject jsonObject= metaData.getjObject();
        JSONObject payload = new JSONObject();
        try {
            payload.put("checklist_id", checklist_id);
            payload.put("asset_id", asset_id);
            payload.put("lat",lat);
            payload.put("lng",lng);
            payload.put("asset_selection", asset_selection);
            payload.put("meta", jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return payload.toString();
    }
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
}

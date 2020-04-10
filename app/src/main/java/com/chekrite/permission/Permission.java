/*
 * Date: 2020.4.1
 * This file is created by Kai.
 * Summary:
 * This class to ask the user for permission to camera and recording.
 */

package com.chekrite.permission;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.chekrite.R;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;


public class Permission {
    Activity mActivity;
    private int mRequestCode = 256;
    private Context mContext;
    public Permission(Activity activity, Context context) {
        mActivity = activity;
        mContext = context;
        // Will request all permissions from the Manifest automatically.
        RequestPermissions();
    }
    public void RequestPermissions() {
        String[] perms = {  Manifest.permission.CAMERA,
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION};
        if (!EasyPermissions.hasPermissions(mActivity, perms)) {
            //Request Permissions

            EasyPermissions.requestPermissions(
                    new PermissionRequest.Builder(mActivity, mRequestCode, perms)
                            .setRationale(R.string.rationale_camera_audio)
                            .setPositiveButtonText(R.string.rationale_ask_ok)
                            .setNegativeButtonText(R.string.rationale_ask_cancel)
                            .build());

        } else {
            // Already have permission, do the thing
            // ...
        }

    }

}

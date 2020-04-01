/*
 * Date: 2020.4.1
 * This file is created by Kai.
 * Summary:
 * This class to ask the user for permission to camera and recording.
 */

package com.chekrite.permission;
import android.Manifest;
import android.app.Activity;

import com.chekrite.R;

import pub.devrel.easypermissions.EasyPermissions;



public class Permission{
    Activity mActivity;
    private int mRequestCode = 256;
    public Permission(Activity activity) {
        mActivity = activity;
        // Will request all permissions from the Manifest automatically.
        RequestPermissions();
    }
    public void RequestPermissions() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};
        if (!EasyPermissions.hasPermissions(mActivity, perms)) {
            //Request Permissions
            EasyPermissions.requestPermissions(mActivity,
                    mActivity.getString(R.string.rationale_camera_audio),
                    mRequestCode, perms);
        } else {
            // Already have permission, do the thing
            // ...
        }

    }
}

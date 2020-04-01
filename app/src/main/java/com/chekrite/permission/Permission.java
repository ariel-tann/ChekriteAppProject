package com.chekrite.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import com.chekrite.R;

import pub.devrel.easypermissions.EasyPermissions;


// use this class to ask the user for permission to camera recording etc..
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

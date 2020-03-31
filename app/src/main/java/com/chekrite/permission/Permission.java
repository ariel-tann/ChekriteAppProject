package com.chekrite.permission;

import android.Manifest;
import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chekrite.R;

import pub.devrel.easypermissions.EasyPermissions;

// use this class to ask the user for permission to camera recording etc..
public class Permission {
    Context mContext;
    public Permission(Context context) {
        mContext = context;
    }
//    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
//    private void methodRequiresTwoPermission() {
//        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION};
//        if (EasyPermissions.hasPermissions(this, perms)) {
//            // Already have permission, do the thing
//            // ...
//        } else {
//            // Do not have permissions, request them now
//            EasyPermissions.requestPermissions(this, R.string.rationale_camera,
//                    RC_CAMERA_AND_LOCATION, perms);
//        }
//    }
}

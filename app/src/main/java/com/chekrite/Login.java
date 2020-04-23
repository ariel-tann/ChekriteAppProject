/*
 * Date: 2020.4.8
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.chekrite.PinView.Chekrite_PinView;
import com.chekrite.PinView.PinListener;
import com.chekrite.permission.Permission;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class Login extends AppCompatActivity
        implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks{

    Button signIn_Btn;
    Button login_syncNow_btn;
    private Permission mPermission;
    private String EMPLOY_ID;
    private String EMPLOY_PIN;
    private PinListener mEPListener = new PinListener() {
        @Override
        public void onSubmit(String pin) {
            EMPLOY_PIN = pin;
            // TODO login, if success, inflate openWelcomeSplash

        }
    };

    private PinListener mEIDPinListen = new PinListener() {
        @Override
        public void onSubmit(String pin) {
            //create a fragment to show Employ PIN enter
            Chekrite_PinView pinView = new Chekrite_PinView(Chekrite_PinView.EMPLOY_PIN, mEPListener);
            pinView.show(getSupportFragmentManager(),"employPin");
            EMPLOY_ID = pin;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPermission = new Permission(this, this);
        mPermission.RequestPermissions();

        signIn_Btn = findViewById(R.id.signIn_btn);
        signIn_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create a fragment to show PinView
                Chekrite_PinView pinView = new Chekrite_PinView(Chekrite_PinView.EMPLOY_ID, mEIDPinListen);
                pinView.show(getSupportFragmentManager(),"employID");
            }
        });

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

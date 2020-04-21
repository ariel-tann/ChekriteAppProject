package com.chekrite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.chekrite.MetaData.meta_data;
import com.chekrite.PinView.Chekrite_PinView;
import com.chekrite.dashBoard.Dashboard;
import com.chekrite.dashBoard.WelcomeSplash;
import com.chekrite.http_request.APIsTask;
import com.chekrite.permission.Permission;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity
        implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks{

    Button test_btn;

    private Permission mPermission;
    private Button mBtnSubmit;
    private ImageView mImageView;
    private Context mContent;
    private View.OnClickListener submitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            new APIsTask().execute();
//             create a fragment to show PinView
            Chekrite_PinView pinView = new Chekrite_PinView(Chekrite_PinView.SETUP);
            pinView.show(getSupportFragmentManager(),"pin");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContent = this.getApplicationContext();

        mPermission = new Permission(this, this);
        mPermission.RequestPermissions();
        mBtnSubmit = findViewById(R.id.setupApp_btn);
        mBtnSubmit.setOnClickListener(submitListener);

        //test
        test_btn = findViewById(R.id.test_btn);
        test_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginScreen();
                //openDashboardScreen();
                //openWelcomeSplash();
            }
        });
        //test_btn.setVisibility((View.GONE));
    }

    public void openLoginScreen() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void openDashboardScreen() {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

    public void openWelcomeSplash() {
        Intent intent = new Intent(this, WelcomeSplash.class);
        startActivity(intent);
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

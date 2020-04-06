package com.chekrite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chekrite.PinView.Chekrite_PinView;
import com.chekrite.http_request.ImageDownloadTask;
import com.chekrite.permission.Permission;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity
        implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks{

    private Permission mPermission;
    private Button mBtnSubmit;
    private ImageView mImageView;
    private Context mContent;
    private View.OnClickListener submitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            URL url = null;
//            try {
//                url = new URL("https://i.imgur.com/tGbaZCY.jpg");
//                new ImageDownloadTask(mImageView).execute(url);
//            } catch (IOException e) {
//                Log.e("Error", "URL error");
//            }
            // create a fragment to show PinView

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
        mBtnSubmit = findViewById(R.id.btn_submit);
        mBtnSubmit.setOnClickListener(submitListener);
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

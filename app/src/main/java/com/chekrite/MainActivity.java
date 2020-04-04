package com.chekrite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


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
    private View.OnClickListener submitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            URL url = null;
            try {
                url = new URL("https://i.imgur.com/tGbaZCY.jpg");
                new ImageDownloadTask(mImageView).execute(url);
            } catch (IOException e) {
                Log.e("Error", "URL error");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mPermission = new Permission(this, this);
        mPermission.RequestPermissions();

        Chekrite_PinView pinView = new Chekrite_PinView(this,
                6,42,
                getResources().getString(R.string.pair_txt),
                getResources().getString(R.string.btn_pin_pair),
                getResources().getString(R.string.pin_title_singin));
        pinView.show(getSupportFragmentManager(),"pin");


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

package com.chekrite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.chekrite.permission.Permission;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {
    private Permission mPermission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("KAI", this.getComponentName()+"");
        mPermission = new Permission(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //check permissions are all allowed
        mPermission.RequestPermissions();
        
    }
}

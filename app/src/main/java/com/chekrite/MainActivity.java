package com.chekrite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.chekrite.MetaData.MetaData;
import com.chekrite.PinView.Chekrite_PinView;
import com.chekrite.PinView.PinListener;
import com.chekrite.http_request.APIsListener;
import com.chekrite.http_request.APIsTask;
import com.chekrite.permission.Permission;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity
        implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks{


    private Permission mPermission;
    private Button mBtnSubmit;
    private static final String FILE_NAME = "pair.txt";

    private APIsListener apIsListener = new APIsListener() {
        @Override
        public void API_Completed(JSONObject jsonObject) {
            // execute this when transaction finished
            try {
                String status = (String) jsonObject.get("status");
                if(status.equals("success")){
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONObject device = data.getJSONObject("device");
                    String udif = device.getString("udid");
                    String auth_code = device.getString("auth_code");
                    JSONObject company = data.getJSONObject("company");
                    String company_name = company.getString("name");
                    String splash = company.getString("splash");
                    String splash_portrait = company.getString("splash_portrait");
                    String highlight_colour = company.getString("highlight_colour");
                    JSONObject site = data.getJSONObject("site");
                    String site_name = site.getString("name");
                    JSONObject pair_credent = new JSONObject();
                    pair_credent.put("device_udid", udif);
                    pair_credent.put("auth_code", auth_code);
                    pair_credent.put("company", company_name);
                    pair_credent.put("site", site_name);
                    FileOutputStream outputStream;
                    try {
                        outputStream = openFileOutput(FILE_NAME , Context.MODE_PRIVATE);
                        outputStream.write(pair_credent.toString().getBytes());
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //TODO go to setup_progress xml and download image


                    openLoginScreen();
                }else{
                    // TODO pair fail

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };
    // set a listener for pin submit
    private PinListener mPinListen = new PinListener() {
        @Override
        public void onSubmit(String pin) {

            MetaData metaData = new MetaData(getApplicationContext());
            // Pair Device
            JSONObject jsonObject= metaData.get();
            try {
                jsonObject.put("pairing_code", pin);
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            Log.d("KAI", jsonObject.toString());
            new APIsTask(apIsListener).execute("POST","pair",jsonObject.toString());
        }
    };

    private View.OnClickListener submitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//             create a fragment to show PinView
            Chekrite_PinView pinView = new Chekrite_PinView(Chekrite_PinView.SETUP, mPinListen);
            pinView.show(getSupportFragmentManager(),"pin");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO check if auth file exists
        setContentView(R.layout.activity_main);
        File file = getBaseContext().getFileStreamPath(FILE_NAME);
        if(file.exists()){
            openLoginScreen();
        }
        mPermission = new Permission(this, this);
        mPermission.RequestPermissions();
        mBtnSubmit = findViewById(R.id.setupApp_btn);
        mBtnSubmit.setOnClickListener(submitListener);

    }

    public void openLoginScreen() {
        Intent intent = new Intent(this, Login.class);
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

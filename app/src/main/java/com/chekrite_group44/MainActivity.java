package com.chekrite_group44;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chekrite_group44.MetaData.MetaData;
import com.chekrite_group44.PinView.PinViewDialog;
import com.chekrite_group44.PinView.PinListener;
import com.chekrite_group44.HttpRequest.APIs;
import com.chekrite_group44.HttpRequest.APIsListener;
import com.chekrite_group44.HttpRequest.APIsTask;
import com.chekrite_group44.Permission.Permission;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity
        implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks{
    private static final String TAG = "MainActivity";
    private Permission permission;
    private Button pair_btn;
    ProgressDialog dialog;

    private APIsListener pairAPIListener = new APIsListener() {
        @Override
        public void API_Completed(JSONObject jsonObject) {
            // execute this when AsyncTask doing onPostExecute.
            try {
                String status = (String) jsonObject.get("status");
                if(status.equals("success")){
                    // extract auth_code, company, site, splash_portrait and highlight_colour
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
                    // put these info in share preference
                    Chekrite.putString("device_udid",udif);
                    Chekrite.putString("auth_code", auth_code);
                    Chekrite.putString("company", company_name);
                    Chekrite.putString("site", site_name);
                    Chekrite.putString("highlight_colour", highlight_colour);
                    Chekrite.putString("splash_portrait", splash_portrait);
                    // close login dialog
                    dialog.dismiss();
                    openLoginScreen();
                }else{
                    // Fail
                    dialog.dismiss();
                    Log.d(TAG,"Pair fail");
                    String message = jsonObject.getString("message");
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Error: "+message, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
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
            // get meta data
            MetaData metaData = new MetaData(getApplicationContext());
            // Pair Device
            JSONObject jsonObject= metaData.getjObject();
            try {
                jsonObject.put("pairing_code", pin);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // login dialog
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("Pairing");
            dialog.setMessage("Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            new APIsTask(pairAPIListener).execute("POST", APIs.PAIR, "", jsonObject.toString());
        }
    };

    private View.OnClickListener pairBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//             create a dialog fragment to show PinView
            PinViewDialog pinViewDialog = new PinViewDialog(PinViewDialog.PAIR, mPinListen);
            pinViewDialog.show(getSupportFragmentManager(),"pin");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // if the device has paired, then go to login activity
        if(Chekrite.pref.contains("device_udid")){
            openLoginScreen();
        }
        permission = new Permission(this, this);
        permission.RequestPermissions();
        pair_btn = findViewById(R.id.setupApp_btn);
        // create button radius
        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius(10);
        shape.setColor(Chekrite.getParseColor());
        pair_btn.setBackground(shape);
        // set up listener
        pair_btn.setOnClickListener(pairBtnListener);
        dialog = new ProgressDialog(this); // Login log

    }

    public void openLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
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
        Log.d(TAG, this.toString()+"Permission Deny: "+perms.toString());

    }

    @Override
    public void onRationaleAccepted(int requestCode) {

    }

    @Override
    public void onRationaleDenied(int requestCode) {

    }
}

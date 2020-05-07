/*
 * Date: 2020.4.8
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


import com.chekrite_group44.PinView.Chekrite_PinView;
import com.chekrite_group44.PinView.PinListener;
import com.chekrite_group44.DashBoard.WelcomeSplash;
import com.chekrite_group44.Http_Request.APIs;
import com.chekrite_group44.Http_Request.APIsListener;
import com.chekrite_group44.Http_Request.APIsTask;
import com.chekrite_group44.Permission.Permission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class Login extends AppCompatActivity
        implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks{
    private static final String TAG = "Login";
    Button signIn_Btn;
    TextView company_Name;
    ImageView company_splash_portait;
    private Permission mPermission;
    private String EMPLOY_ID;
    private String EMPLOY_PIN;
    Chekrite_PinView mEIDPinView;
    ProgressDialog dialog;
    SharedPreferences pref;
    private APIsListener APIApp_version = new APIsListener() {
        @Override
        public void API_Completed(JSONObject jsonObject) {
            String status = null;
            try {
                if(jsonObject!=null) {
                    status = (String) jsonObject.get("status");
                    if (status.equals("success")) {
                        Log.d("KAI", "status " + status);
                        JSONObject data = jsonObject.getJSONObject("data");
                        String app_version = data.getString("app_version");
                        Log.d("KAI", "app_version: " + app_version);

                    } else {
                        String message = jsonObject.getString("message");
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Error: "+message, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
                else{
                    Log.d("KAI", "Error: JSONObject null");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    APIsListener LogoutListener = new APIsListener() {
        @Override
        public void API_Completed(JSONObject jsonObject) {
            try {
                String status = (String) jsonObject.get("status");
                // Fail logout
                if(!status.equals("success")){
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


    private APIsListener apIsListener = new APIsListener() {
        @Override
        public void API_Completed(JSONObject jsonObject) {
            String status = null;
            try {
                status = (String) jsonObject.get("status");
                if(status.equals("success")){
                    Log.d("KAI", "status "+ status);
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONObject device = data.getJSONObject("device");
                    String access_token = device.getString("access_token");
                    String token_expiry = device.getString("token_expiry");
                    JSONObject user = data.getJSONObject("user");
                    String first_name = user.getString("first_name");
                    String last_name = user.getString("last_name");
                    String profile_photo = user.getString("profile_photo");
                    Log.d("KAI", "token "+ access_token);
                    //SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    pref = getSharedPreferences(Chekrite.SHARED_PREFS, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("first_name", first_name);
                    editor.putString("last_name", last_name);
                    editor.putString("profile_photo", profile_photo);
                    editor.putString("access_token", access_token);
                    editor.putString("token_expiry", token_expiry);
                    editor.apply();
//                    get app_version
                    new APIsTask(APIApp_version, getApplicationContext()).execute("GET", APIs.APP_VERSION, "", "");
                    // close login dialog
                    dialog.dismiss();
                    openWelcomeSplash(profile_photo,first_name,last_name);

                }else{
                    dialog.dismiss();
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
    private PinListener mEPListener = new PinListener() {
        @Override
        public void onSubmit(String pin) {
            mEIDPinView.dismiss();
            EMPLOY_PIN = pin;
            try {

                SharedPreferences pref = getSharedPreferences(Chekrite.SHARED_PREFS, Context.MODE_PRIVATE);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("company",pref.getString("company", ""));
                jsonObject.put("site",pref.getString("site", ""));
                jsonObject.put("device_udid",pref.getString("device_udid", ""));
                jsonObject.put("auth_code",pref.getString("auth_code", ""));
                jsonObject.put("badge_no", EMPLOY_ID);
                jsonObject.put("pin", EMPLOY_PIN);
                // login dialog
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setTitle("Login");
                dialog.setMessage("Please wait...");
                dialog.setIndeterminate(true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                // Check token_expiry
                String token_expiry = pref.getString("token_expiry", "");
                if (token_expiry.length() > 0){
                    Date expiredDate = stringToDate(token_expiry, "yyyy-MM-dd HH:mm:ss");
                    // if token is not expired and can used, we need to logout first
                    if(new Date().before(expiredDate)&&!new Date().after(expiredDate)){
                        new APIsTask(LogoutListener, getApplicationContext()).execute("POST", APIs.LOGOUT,"","");
                    }
                }
                new APIsTask(apIsListener, getApplicationContext()).execute("POST", APIs.LOGIN, "",jsonObject.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
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
    private Date stringToDate(String aDate,String aFormat) {

        if(aDate==null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;

    }
    public void openWelcomeSplash(String profile_photo,String first_name,String last_name) {
        Intent intent = new Intent(this, WelcomeSplash.class);
        intent.putExtra("profile_photo",profile_photo);
        intent.putExtra("first_name",first_name);
        intent.putExtra("last_name",last_name);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPermission = new Permission(this, this);
        mPermission.RequestPermissions();
        dialog = new ProgressDialog(this); // Login log

        // Display company name received from server
        SharedPreferences pref = getSharedPreferences(Chekrite.SHARED_PREFS, Context.MODE_PRIVATE);
        String companyName = pref.getString("company", "NIL");
        company_Name = findViewById(R.id.Company_name);
        company_Name.setText(companyName);

        //Display company portait with URL saved from server
        String splash_portrait_URL = pref.getString("splash_portrait", "NUL");
        company_splash_portait = findViewById(R.id.imageView4);
        Glide.with(getApplicationContext()).load(splash_portrait_URL).into(company_splash_portait);


        signIn_Btn = findViewById(R.id.signIn_btn);
        // get color and set to btn background
        String highlight_colour = pref.getString("highlight_colour", "#65cb81");
        signIn_Btn.setBackgroundColor(Color.parseColor(highlight_colour));
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar4);
        toolbar.setBackgroundColor(Color.parseColor(highlight_colour));

        signIn_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create a fragment to show PinView
                mEIDPinView = new Chekrite_PinView(Chekrite_PinView.EMPLOY_ID, mEIDPinListen);
                mEIDPinView.show(getSupportFragmentManager(),"employID");
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

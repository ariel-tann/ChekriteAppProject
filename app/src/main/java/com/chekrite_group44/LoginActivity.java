/*
 * Date: 2020.4.8
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chekrite_group44.PinView.PinViewDialog;
import com.chekrite_group44.PinView.PinListener;
import com.chekrite_group44.DashBoard.WelcomeActivity;
import com.chekrite_group44.HttpRequest.APIs;
import com.chekrite_group44.HttpRequest.APIsListener;
import com.chekrite_group44.HttpRequest.APIsTask;
import com.chekrite_group44.Permission.Permission;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class LoginActivity extends AppCompatActivity
        implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks{
    private static final String TAG = "Login";
    Button sign_in_btn;
    TextView company_name;
    ImageView company_splash_portrait;
    private Permission permission;
    private String employeeId;
    private String employeePin;
    PinViewDialog mEmployIDPinViewDialog;
    ProgressDialog dialog;
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


    private APIsListener PairAPIsListener = new APIsListener() {
        @Override
        public void API_Completed(JSONObject jsonObject) {
            String status = null;
            try {
                status = (String) jsonObject.get("status");
                if(status.equals("success")){
                    Log.d(TAG, "status "+ status);
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONObject device = data.getJSONObject("device");
                    String access_token = device.getString("access_token");
                    String token_expiry = device.getString("token_expiry");
                    JSONObject user = data.getJSONObject("user");
                    String first_name = user.getString("first_name");
                    String last_name = user.getString("last_name");
                    String profile_photo = user.getString("profile_photo");
                    //
                    Chekrite.putString("first_name", first_name);
                    Chekrite.putString("last_name", last_name);
                    Chekrite.putString("profile_photo", profile_photo);
                    Chekrite.putString("access_token", access_token);
                    Chekrite.putString("token_expiry", token_expiry);
//                    get app_version
                    new APIsTask(APIApp_version).execute("GET", APIs.APP_VERSION, "", "");
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
                    if(!message.equals("User not found.") &&
                            !message.equals("Validation failed.")){
                        // remove share data and go to pair screen
                        Chekrite.rmPref();
                        openMainActivity();
                    }
                    Log.d(TAG, jsonObject.toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    private PinListener employIDListener = new PinListener() {
        @Override
        public void onSubmit(String pin) {
            mEmployIDPinViewDialog.dismiss();
            employeePin = pin;
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("company",Chekrite.getString("company"));
                jsonObject.put("site",Chekrite.getString("site"));
                jsonObject.put("device_udid",Chekrite.getString("device_udid"));
                jsonObject.put("auth_code",Chekrite.getString("auth_code"));
                jsonObject.put("badge_no", employeeId);
                jsonObject.put("pin", employeePin);
                // login dialog
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setTitle("Login");
                dialog.setMessage("Please wait...");
                dialog.setIndeterminate(true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                // Check token_expiry
                String token_expiry = Chekrite.getString("token_expiry");
                if (token_expiry.length() > 0){
                    Date expiredDate = stringToDate(token_expiry, "yyyy-MM-dd HH:mm:ss");
                    // if token is not expired and can used, we need to logout first
                    if(new Date().before(expiredDate)&&!new Date().after(expiredDate)){
                        new APIsTask(LogoutListener).execute("POST", APIs.LOGOUT,"","");
                    }
                }
                new APIsTask(PairAPIsListener).execute("POST", APIs.LOGIN, "",jsonObject.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private PinListener employPinListen = new PinListener() {
        @Override
        public void onSubmit(String pin) {
            //create a fragment to show Employ PIN enter
            PinViewDialog pinViewDialog = new PinViewDialog(PinViewDialog.EMPLOY_PIN, employIDListener);
            pinViewDialog.show(getSupportFragmentManager(),"employPin");
            employeeId = pin;
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
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.putExtra("profile_photo",profile_photo);
        intent.putExtra("first_name",first_name);
        intent.putExtra("last_name",last_name);
        startActivity(intent);
    }
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        permission = new Permission(this, this);
        permission.RequestPermissions();
        dialog = new ProgressDialog(this); // Login log

        // Display company name received from server
        String companyName = Chekrite.getString("company");
        if(companyName.length() == 0){
            companyName = "NIL";
        }
        company_name = findViewById(R.id.Company_name);
        company_name.setText(companyName);

        //Display company portrait with URL saved from server
        String splash_portrait_URL = Chekrite.getString("splash_portrait");
        final ProgressBar loading = findViewById(R.id.progress_loading);
        loading.getIndeterminateDrawable()
                .setColorFilter(Chekrite.getParseColor(), PorterDuff.Mode.SRC_IN );
        company_splash_portrait = findViewById(R.id.imageView4);
        Glide.with(getApplicationContext())
                .load(splash_portrait_URL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        loading.setVisibility(View.GONE);
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        loading.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(company_splash_portrait);

        sign_in_btn = findViewById(R.id.signIn_btn);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar4);
        // get color and set to btn background
        sign_in_btn.setBackgroundColor(Chekrite.getParseColor());
        // create button radius
        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius(10);
        shape.setColor(Chekrite.getParseColor());
        sign_in_btn.setBackground(shape);
        //
        toolbar.setBackgroundColor(Chekrite.getParseColor());

        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create a fragment to show PinView
                mEmployIDPinViewDialog = new PinViewDialog(PinViewDialog.EMPLOY_ID, employPinListen);
                mEmployIDPinViewDialog.show(getSupportFragmentManager(),"employID");
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
        Log.d(TAG, this.toString()+"Permission Deny: "+perms.toString());

    }

    @Override
    public void onRationaleAccepted(int requestCode) {

    }

    @Override
    public void onRationaleDenied(int requestCode) {

    }
}

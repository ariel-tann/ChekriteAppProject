/*
 * Date: 2020.4.8
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.chekrite_group44.PinView.Chekrite_PinView;
import com.chekrite_group44.PinView.PinListener;
import com.chekrite_group44.dashBoard.WelcomeSplash;
import com.chekrite_group44.http_request.APIs;
import com.chekrite_group44.http_request.APIsListener;
import com.chekrite_group44.http_request.APIsTask;
import com.chekrite_group44.permission.Permission;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xml.sax.Parser;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class Login extends AppCompatActivity
        implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks{

    Button signIn_Btn;
    Button login_syncNow_btn;
    TextView company_Name;
    public static final String SHARED_PREFS = "sharedPrefs";
    private Permission mPermission;
    private String EMPLOY_ID;
    private String EMPLOY_PIN;
    Chekrite_PinView mEIDPinView;

    private static final String FILE_NAME = "pair.txt";
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
                    JSONObject user = data.getJSONObject("user");
                    String first_name = user.getString("first_name");
                    String last_name = user.getString("last_name");
                    String profile_photo = user.getString("profile_photo");
                    Log.d("KAI", "token "+ access_token);
                    //SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences pref = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("first_name", first_name);
                    editor.putString("last_name", last_name);
                    editor.putString("access_token", access_token);
                    editor.apply();
//                    get app_version
                    new APIsTask(APIApp_version, getApplicationContext()).execute("GET", APIs.APP_VERSION, "", "");
                    openWelcomeSplash(profile_photo,first_name,last_name);

                }else{
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
            // TODO login, if success, inflate openWelcomeSplash
            try {
                // read pair.txt file
                FileInputStream fis;
                final StringBuffer storedString = new StringBuffer();
                fis = openFileInput(FILE_NAME);
                DataInputStream dataIO = new DataInputStream(fis);
                String strLine = null;
                if ((strLine = dataIO.readLine()) != null) {
                    storedString.append(strLine);
                }
                dataIO.close();
                fis.close();
                JSONObject jsonObject = new JSONObject(strLine);
                jsonObject.put("badge_no", EMPLOY_ID);
                jsonObject.put("pin", EMPLOY_PIN);

                new APIsTask(apIsListener, getApplicationContext()).execute("POST", APIs.LOGIN, "",jsonObject.toString());

            } catch (IOException e) {
                e.printStackTrace();
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

        // Display company name received from API
        SharedPreferences pref = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String settings = pref.getString("company", "nope");
        company_Name = findViewById(R.id.Company_name);
        company_Name.setText(settings);

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

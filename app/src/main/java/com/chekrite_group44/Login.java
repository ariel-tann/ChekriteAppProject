/*
 * Date: 2020.4.8
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.chekrite_group44.PinView.Chekrite_PinView;
import com.chekrite_group44.PinView.PinListener;
import com.chekrite_group44.dashBoard.WelcomeSplash;
import com.chekrite_group44.http_request.APIsListener;
import com.chekrite_group44.http_request.APIsTask;
import com.chekrite_group44.permission.Permission;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class Login extends AppCompatActivity
        implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks{

    Button signIn_Btn;
    Button login_syncNow_btn;
    private Permission mPermission;
    private String EMPLOY_ID;
    private String EMPLOY_PIN;
    Chekrite_PinView mEIDPinView;

    private static final String FILE_NAME = "pair.txt";
    private APIsListener apIsListener = new APIsListener() {
        @Override
        public void API_Completed(JSONObject jsonObject) {
            Log.d("Json object value ","Value : "+jsonObject);
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
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("first_name", first_name);
                    editor.putString("last_name", last_name);
                    editor.putString("access_token", access_token);
                    editor.apply();
                    openWelcomeSplash(profile_photo,first_name,last_name);

                }else{
                    // TODO login fail
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

                new APIsTask(apIsListener, getApplicationContext()).execute("POST", "login", jsonObject.toString());

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

        signIn_Btn = findViewById(R.id.signIn_btn);
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

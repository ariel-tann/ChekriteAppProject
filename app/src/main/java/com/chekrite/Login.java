/*
 * Date: 2020.4.8
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.chekrite.PinView.Chekrite_PinView;
import com.chekrite.PinView.PinListener;
import com.chekrite.http_request.APIsListener;
import com.chekrite.http_request.APIsTask;
import com.chekrite.permission.Permission;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
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
            String status = null;
            try {
                status = (String) jsonObject.get("status");
                if(status.equals("success")){
                    JSONObject data = jsonObject.getJSONObject("data");
                    String user_name = data.getString("first_name");
                    user_name += data.getString("last_name");
                    JSONObject device = jsonObject.getJSONObject("device");
                    String access_token = data.getString("access_token");
                    Log.d("KAI", "user name: "+user_name+ "token"+access_token);
                    

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

                new APIsTask(apIsListener).execute("POST", "login", jsonObject.toString());

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

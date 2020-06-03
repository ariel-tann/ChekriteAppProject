/*
 * Date: 2020.5.12
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.ChecklistSelection;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.chekrite_group44.Chekrite;
import com.chekrite_group44.HttpRequest.APIs;
import com.chekrite_group44.HttpRequest.APIsListener;
import com.chekrite_group44.HttpRequest.APIsTask;
import com.chekrite_group44.LoginActivity;
import com.chekrite_group44.R;

import org.json.JSONException;
import org.json.JSONObject;


public class SignoutActivity extends AppCompatActivity {
    //UI
    Button logoutBtn;
    Button cancelBtn;
    TextView headerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_out);
        // set view
        headerText = (TextView) findViewById(R.id.sign_out_header);
        headerText.setBackgroundColor(Chekrite.getParseColor());

        logoutBtn = (Button) findViewById(R.id.sign_out_btn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        logoutBtn.setBackgroundColor(Chekrite.getParseColor());


        cancelBtn = (Button) findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignoutActivity.super.onBackPressed();
            }
        });
    }

    public void logout(){

        new APIsTask(LogoutListener).execute("POST", APIs.LOGOUT,"","");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    };
    //call logout api
    APIsListener LogoutListener = new APIsListener() {
        @Override
        public void API_Completed(JSONObject jsonObject) {
            try {
                String status = (String) jsonObject.get("status");
                if(status.equals("success")){
                    Log.d("KAI", "Logout success");
                    String message = jsonObject.getString("message");
                    Toast toast = Toast.makeText(getApplicationContext(),
                            message, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
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
}

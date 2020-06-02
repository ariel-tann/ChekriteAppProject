/*
 * Date: 2020.4.5
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.DashBoard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chekrite_group44.Chekrite;
import com.chekrite_group44.LoginActivity;
import com.chekrite_group44.NewCheck.NewCheckActivity;
import com.chekrite_group44.R;
import com.chekrite_group44.SelectAssetScreen.SelectAssetScreen;
import com.chekrite_group44.HttpRequest.APIs;
import com.chekrite_group44.HttpRequest.APIsListener;
import com.chekrite_group44.HttpRequest.APIsTask;

import org.json.JSONException;
import org.json.JSONObject;


public class DashboardActivity extends AppCompatActivity {
    private Button logout_button;
    ImageView check_button;
    ImageView profile_button_photo;
    String photo_url;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //Set background color
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Chekrite.getParseColor());
        photo_url = Chekrite.getString("profile_photo");
        Log.d("ariel", "onCreate: " + photo_url);
        profile_button_photo = findViewById(R.id.btn_profile);

        //get_btn_profile
        if(!photo_url.equals("null")) {

            Log.d("ariel", "onCreate: in ==null");
            Glide.with(getApplicationContext()).load(photo_url).apply(RequestOptions.circleCropTransform()).into(profile_button_photo);
        }


        logout_button=findViewById(R.id.logout_button);
        check_button=findViewById(R.id.newCheck);

        check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_newCheck();
            }
        });
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    public void startNewcheck(View view){
        Log.d("startcheck","Inside start check");
        Intent intent = new Intent(this, SelectAssetScreen.class);
        startActivity(intent);

    }
    public void logout(){
        new APIsTask(LogoutListener).execute("POST", APIs.LOGOUT,"","");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void open_newCheck() {
        Intent intent = new Intent(this, NewCheckActivity.class);
        startActivity(intent);
    }
}

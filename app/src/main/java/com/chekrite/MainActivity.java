package com.chekrite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.chekrite.http_request.ImageDownloadTask;
import com.chekrite.permission.Permission;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {
    private Permission mPermission;
    private Button mBtnSubmit;
    private ImageView mImageView;
    private View.OnClickListener submitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            URL url = null;
            try {
                url = new URL("https://i.imgur.com/tGbaZCY.jpg");
                new ImageDownloadTask(mImageView).execute(url);
            } catch (IOException e) {
                Log.e("Error", "URL error");
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("KAI", this.getComponentName()+"");

        mPermission = new Permission(this, this);
        mBtnSubmit = findViewById(R.id.btn_submit);
        mBtnSubmit.setOnClickListener(submitListener);
        mImageView = findViewById(R.id.ivImage);

    }


    @Override
    protected void onStart() {
        super.onStart();
        //check permissions are all allowed
        mPermission.RequestPermissions();

    }
}

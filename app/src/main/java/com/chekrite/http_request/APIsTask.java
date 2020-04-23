/*
 * Date: 2020.4.1
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite.http_request;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

// Defines the background task to download and then load the image within the ImageView
public class APIsTask extends AsyncTask<String, Void, String> {
    APIsListener mAPIsListener;
    public APIsTask(APIsListener apIsListener) {
        mAPIsListener = apIsListener;
    }

    @Override
    protected String doInBackground(String... params) {
        // params[0]: GET/POST
        // params[1]: api
        // params[2]: body
        URL url = null;
        String tmp = "https://apitest.mychekrite.com/api/";
        InputStream in = null;
        try {
            if(params[0].equals("POST")){
                tmp += params[1] +"?";
                JSONObject jsonObject= new JSONObject(params[2]);
                JSONArray jkey = jsonObject.names ();
                for (int i = 0; i < jkey.length (); ++i) {
                    try {
                        String key = jkey.getString (i); // key of json
                        String value = jsonObject.getString (key); // value of json
                        if(i < jkey.length ()-1){
                            tmp += key+"="+value+"&";
                        }else{
                            // the last value
                            tmp += key+"="+value;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }else {
                // TODO "GET" HttpURL
            }
            Log.d("KAI", "URL: "+tmp);
            url = new URL(tmp);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(params[0]);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Accept", "*/*");
            connection.setDoOutput(true);
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
//            writer.write("Write something");
//            writer.close();
            connection.connect();
            // Response: 400
            Log.d("KAI", "Response: "+connection.getResponseMessage() + "");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();
            return stringBuilder.toString();
        } catch (Exception e) {
            Log.e(e.toString(), "Something with request");
            return null;
        }
    }

    @Override
    protected void onPostExecute(String response) {
        //Action after Execute
        Log.d("KAI", "response: "+response);
        try {
            mAPIsListener.API_Completed(new JSONObject(response));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private Boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }


}
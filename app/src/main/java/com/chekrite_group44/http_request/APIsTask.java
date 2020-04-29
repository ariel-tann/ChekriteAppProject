/*
 * Date: 2020.4.1
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.http_request;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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
        String chekriteLink = "https://apitest.mychekrite.com/api/" + params[1];
        InputStream in = null;
        try {
            Log.d("KAI", "URL: "+ chekriteLink);
            url = new URL(chekriteLink);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(params[0]);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Accept", "*/*");
            connection.setDoOutput(true);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            Log.d("KAI","Body: \n"+ params[2]);
            writer.write(JsonToString(params[2]));
            connection.connect();
            writer.close();
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
        Log.d("KAI", "response: \n"+response);
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

    private String JsonToString(String json) throws JSONException {
        String tmp = "";
        JSONObject jsonObject= new JSONObject(json);
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
        return tmp;
    }
}
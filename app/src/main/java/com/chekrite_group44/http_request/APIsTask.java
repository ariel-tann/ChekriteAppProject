/*
 * Date: 2020.4.1
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.http_request;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.chekrite_group44.Login;

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
    Context mContext;

    public APIsTask(APIsListener apIsListener, Context context) {
        // APIsListener: When API gets response from DB, it will notify user
        // context: used for getting share preference
        mAPIsListener = apIsListener;
        mContext = context;
    }

    @Override
    protected String doInBackground(String... params) {
        // params[0]: GET/POST
        // params[1]: api
        // params[2]: search query or asset_id
        // params[3]: http body
        URL url = null;
        String chekriteLink = "https://apitest.mychekrite.com/api/";
        // Define API link
        switch (params[1]){
            case APIs.PAIR:
                chekriteLink = chekriteLink + "pair";
                break;
            case APIs.LOGIN:
                chekriteLink = chekriteLink + "login";
                break;
            case APIs.LOGOUT:
                chekriteLink = chekriteLink + "logout";
                break;
            case APIs.APP_VERSION:
                chekriteLink = chekriteLink + "app_version";
                break;
            case APIs.ASSETS:
                chekriteLink = chekriteLink + "asset_classes";
                break;
            case APIs.SEARCH:
                chekriteLink = chekriteLink + "search?q="+params[2];
                break;
            case APIs.CHECKLIST:
                chekriteLink = chekriteLink +"assets/"+params[2]+"/checklists";
                break;
        }

        try {
            Log.d("KAI", params[0]+" URL: "+ chekriteLink);
            url = new URL(chekriteLink);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

//          Get token, if it exists in share pref
            SharedPreferences pref = mContext.getSharedPreferences(Login.SHARED_PREFS, mContext.MODE_PRIVATE);
            String token = pref.getString("access_token", "");
//            Add token in Header
//            LOGIN and PAIR don't require token
            if(token.length()>0 && params[1]!=APIs.LOGIN && params[1]!=APIs.PAIR){
                connection.setRequestProperty("Authorization", "Bearer "+token);
                Log.d("KAI","Token: "+token);
            }
            // define http property
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Host", "apitest.mychekrite.com");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Accept-Encoding", "gzip");
            connection.setRequestMethod(params[0]);

            //only POST needs
            if (params[0] == "POST"){
                connection.setDoOutput(true);
            }
            // Write body
            if (params[3].length() > 0) {
                // only write body, when params[2] has value
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                Log.d("KAI", "Body: \n" + params[3]);
                writer.write(JsonToString(params[3]));
                connection.connect();
                writer.close();
            }else{
                connection.connect();
            }
            // Response from Server
            Log.d("KAI", "Response: "+connection.getResponseMessage() + "");
            // use BufferedReader to parse response to String variable
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
        if (response!=null) {
            Log.d("KAI", "response: \n" + response);
            try {
                mAPIsListener.API_Completed(new JSONObject(response));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            Log.d("KAI", "Error: JSONObject null");
        }
    }

    private String JsonToString(String json) throws JSONException {
        // Convert json string to chekrite require http format
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
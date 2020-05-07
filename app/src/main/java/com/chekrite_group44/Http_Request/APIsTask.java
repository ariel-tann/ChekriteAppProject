/*
 * Date: 2020.4.1
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Http_Request;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.chekrite_group44.Chekrite;
import com.chekrite_group44.Login;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


// Defines the background task to download and then load the image within the ImageView
public class APIsTask extends AsyncTask<String, Void, String> {
    APIsListener mAPIsListener;
    Context mContext;
    private static final String TAG = "APIsTask";
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
            case APIs.START:
                chekriteLink = chekriteLink + "tests/start";
                break;
            case APIs.RESPONSES:
                chekriteLink = chekriteLink + "responses";
                break;
            case APIs.SUBMIT:
                chekriteLink = chekriteLink + "tests/submit";
                break;
        }

        try {
            Log.d(TAG, params[0]+" URL: "+ chekriteLink);
            url = new URL(chekriteLink);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

//          Get token, if it exists in share pref
            SharedPreferences pref = mContext.getSharedPreferences(Chekrite.SHARED_PREFS, mContext.MODE_PRIVATE);
            String token = pref.getString("access_token", "");
//            Add token in Header
//            LOGIN and PAIR don't require token
            if(token.length()>0 && params[1]!=APIs.LOGIN && params[1]!=APIs.PAIR){
                connection.setRequestProperty("Authorization", "Bearer "+token);
                Log.d(TAG,"Token: "+token);
            }
            // define http property
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Host", "apitest.mychekrite.com");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Accept-Encoding", "gzip");
            connection.setRequestMethod(params[0]);

            //only POST needs
            if (params[0].equals("POST")){
                connection.setDoOutput(true);
            }
            // Write body
            if (params[3].length() > 0) {
//                 only write body, when params[2] has value
                Log.d(TAG, "info sending to DB\n"+params[3]);
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes(params[3]);
                connection.connect();
                out.flush();
                out.close();
            }else{
                connection.connect();
            }
            // Response from Server
            Log.d(TAG, "Response: "+connection.getResponseMessage() + "");
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
            Log.d(TAG, "response: \n" + response);
            try {
                mAPIsListener.API_Completed(new JSONObject(response));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            Log.d(TAG, "Error: JSONObject null");
        }
    }
}
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

// Defines the background task to download and then load the image within the ImageView
public class APIsTask extends AsyncTask<Void, Void, Void> {
    URL url = null;

    public APIsTask() {

    }

    @Override
    protected Void doInBackground(Void... voids) {
        URL url = null;
        InputStream in = null;
        try {
            Log.d("KAI","start doIn...");
            url = new URL("https://test.mychekrite.com/oauth/authorize");
            URLConnection conn = null;
            conn = (HttpURLConnection) url.openConnection();
            conn.addRequestProperty("client_id","kaihua.chuang@connect.qut.edu.au");
            Log.d("KAI","Request: "+conn.getContentEncoding());
            // 2. Open InputStream to connection
            conn.connect();
            in = conn.getInputStream();
            Log.d("KAI","Response: "+in.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        //Action after Execute
        super.onPostExecute(aVoid);
    }


    private Boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }


}
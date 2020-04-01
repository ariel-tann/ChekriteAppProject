/*
 * Date: 2020.4.1
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite.http_request;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

// Defines the background task to download and then load the image within the ImageView
public class ImageDownloadTask extends AsyncTask<URL, Void, Bitmap> {
    ImageView imageView;

    public ImageDownloadTask(ImageView imageView) {
        this.imageView = imageView;
    }
    private Boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }


    @Override
    protected Bitmap doInBackground(URL... urls) {
        Bitmap bitmap = null;
        InputStream in = null;
        Log.d("KAI", "doInBackground");
        try {
            // 1. Declare a URL Connection
            HttpURLConnection conn = (HttpURLConnection) urls[0].openConnection();
            // 2. Open InputStream to connection
            conn.connect();
            in = conn.getInputStream();
            // 3. Download and decode the bitmap using BitmapFactory
            bitmap = BitmapFactory.decodeStream(in);
            Log.d("KAI", "Download an image");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    Log.d("KAI", "Exception while closing inputstream"+ e);
                }
        }
        return bitmap;
    }

    // Fires after the task is completed, displaying the bitmap into the ImageView
    @Override
    protected void onPostExecute(Bitmap result) {
        // Set bitmap image for the result
        imageView.setImageBitmap(result);
    }
}
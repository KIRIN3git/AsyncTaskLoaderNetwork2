/*!
 @file AsyncLoadingTask.java
 ITS AP Forum FUKUOKA

 @author
    Created by Nagakura Hideharu.
 @copyright
    Copyright (c) 2018 Jorudan Co.,Ltd. All rights reserved.
 */
package com.example.shinji_win.asynctaskloadernetwork2.util;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class AsyncLoadingTask extends AsyncTaskLoader<JSONObject> {


    String mUrl;

    public AsyncLoadingTask(Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    @Override
    public JSONObject loadInBackground() {
        URL url;
        try {
            url = new URL(mUrl);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + mUrl);
        }

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            if (conn != null) {
                conn.connect();
                String jsonObject = JSONHandler.parseStream(conn.getInputStream());
                return new JSONObject(jsonObject);
            }
        } catch (IOException e) {
        } catch (JSONException e) {
        } finally {
        }

        return null;
    }


}

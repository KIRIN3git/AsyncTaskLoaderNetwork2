/*!
 @file JSONHandler.java
 ITS AP Forum FUKUOKA

 @author
    Created by Nagakura Hideharu.
 @copyright
    Copyright (c) 2018 Jorudan Co.,Ltd. All rights reserved.
 */
package com.example.shinji_win.asynctaskloadernetwork2.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JSONHandler {

    /**
     * Loads a raw resource and returns its content as a String.
     */
    public static String parseResource(Context context, int resource) {
        InputStream stream = context.getResources().openRawResource(resource);
        return parseStream(stream);
    }

    public static String parseStream(final InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, IOUtils.CHARSET_UTF8));
        String writer = "";

        try {
            String line = "";
            while ((line = reader.readLine()) != null) {
                writer += line;
            }
        } catch (IOException e) {
        }

        return writer;
    }


}

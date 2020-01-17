package com.marix.myaht.api;

import android.net.Uri;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.BasicClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public abstract class ConnectionServer {
    final private String TAG = "MyAht";
    final private String SITE_URI = "https://ahtmy.ru/";
    public abstract void callBack(JSONArray result);

    private HttpsURLConnection connection;

    private InputStream inputStream = null;


    public void sendRequest(String request) {
        try {
            URL url = new URL(SITE_URI + request);
            connection = (HttpsURLConnection) url.openConnection();
            inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null){
                sb.append(line + '\n');
            }

            callBack(new JSONArray(sb.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (connection != null)
                connection.disconnect();
        }
    }
}

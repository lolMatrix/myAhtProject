package com.marix.myaht;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.json.JSONException;
import org.json.JSONObject;

public class NewsActivity extends AppCompatActivity {

    private final String TAG = "MyAht";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        WebView wb = (WebView) findViewById(R.id.webViewNews);
        WebSettings ws = wb.getSettings();
        ws.setAppCacheEnabled(true);
        ws.setJavaScriptEnabled(true);
        ws.setUseWideViewPort(true);
        ws.setLoadWithOverviewMode(true);
        ws.setTextZoom(300);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        try {
            JSONObject news = new JSONObject(i.getStringExtra("object"));
            actionBar.setTitle(news.getJSONObject("title").getString("rendered"));
            wb.loadDataWithBaseURL(null, news.getJSONObject("content").getString("rendered"), "text/html", "UTF-8", null);
            Log.d(TAG, "onCreate: " + news.getJSONObject("content").getString("rendered"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

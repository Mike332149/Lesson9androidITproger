package com.example.lesson9androiditproger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ContactsActivity extends AppCompatActivity {

    private WebView webViewVacancy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        webViewVacancy = findViewById(R.id.webViewVacancy);
        WebSettings webSettings = webViewVacancy.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webViewVacancy.loadUrl("https://wargaming.com/en/careers/");
        webViewVacancy.setWebViewClient(new WebViewClient());
    }
    public void goHome (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void goWeb (View view) {
        Intent intent = new Intent(this, WebActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        if (webViewVacancy.canGoBack())
            webViewVacancy.goBack();
        else {
            super.onBackPressed();
        }
    }



}


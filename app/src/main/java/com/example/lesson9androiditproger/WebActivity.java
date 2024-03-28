package com.example.lesson9androiditproger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://dou.ua/lenta");
        webView.setWebViewClient(new WebViewClient());
    }
    @Override
    public void onBackPressed() {
        if (webView.canGoBack())
            webView.goBack();
        else {
            super.onBackPressed();
        }
    }
    public void goHome (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void goContacts (View view) {
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
    }
}
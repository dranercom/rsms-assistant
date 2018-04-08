package com.example.draner.rsms;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class SeatingArrangement extends AppCompatActivity {
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seating_arrangement);
        WebView pdfView=findViewById(R.id.pdfView);
        startWebView("https://www.rajagiritech.ac.in/stud/parent/Seat/14012902.pdf",pdfView);

    }
    private void startWebView(String url,WebView view) {

        WebSettings settings = view.getSettings();

        settings.setJavaScriptEnabled(true);
        view.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

        view.getSettings().setBuiltInZoomControls(true);
        view.getSettings().setUseWideViewPort(true);
        view.getSettings().setLoadWithOverviewMode(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getApplicationContext(), "Error:" + description, Toast.LENGTH_SHORT).show();

            }
        });
        view.loadUrl(url);
    }
}

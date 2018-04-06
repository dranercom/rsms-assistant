package com.example.draner.rsms;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EncodingUtils;

import java.io.IOException;
import java.io.InputStream;

public class home_page extends AppCompatActivity {
    String user, pass;
    String data;

    //    byte text[] = data.getBytes();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle=getIntent().getExtras();
        user = bundle.getString("username");
        pass = bundle.getString("password");
        data = "user=" + user + "&pass=" + pass + "I1.x=0&I1.y=0";
        WebView webView = findViewById(R.id.webview);
        WebViewClient wvc = new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {

                try {
                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(url);
                    httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded");
                    HttpResponse httpReponse = client.execute(httpGet);

                    Header contentType = httpReponse.getEntity().getContentType();
                    Header encoding = httpReponse.getEntity().getContentEncoding();
                    InputStream responseInputStream = httpReponse.getEntity().getContent();

                    String contentTypeValue = null;
                    String encodingValue = null;
                    if (contentType != null) {
                        contentTypeValue = contentType.getValue();
                    }
                    if (encoding != null) {
                        encodingValue = encoding.getValue();
                    }
                    return new WebResourceResponse(contentTypeValue, encodingValue, responseInputStream);
                } catch (ClientProtocolException e) {
                    //return null to tell WebView we failed to fetch it WebView should try again.
                    return null;
                } catch (IOException e) {
                    //return null to tell WebView we failed to fetch it WebView should try again.
                    return null;
                }
            }
        };
        webView.setWebViewClient(wvc);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.postUrl("https://www.rajagiritech.ac.in/stud/parent/Parent.asp", EncodingUtils.getBytes(data,"BASE64"));
    }
}
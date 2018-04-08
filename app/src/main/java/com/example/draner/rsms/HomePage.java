package com.example.draner.rsms;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EncodingUtils;
import java.io.IOException;
import java.io.InputStream;

import static com.example.draner.rsms.MainActivity.editor;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //set username on navigation drawer
        Bundle bundle=getIntent().getExtras();
        TextView textView=navigationView.getHeaderView(0).findViewById(R.id.uid_nav);
        textView.setText(bundle.getString("username"));

        //set profile picture in navigation drawer
        View header = navigationView.getHeaderView(0);
        ImageView imageView = header.findViewById(R.id.profilePic);
        String profilepic_url="https://www.rajagiritech.ac.in/stud/Photo/"+bundle.getString("username")+".jpg";
        Glide
                .with(this)
                .load(profilepic_url)
                .placeholder(R.drawable.ic_person_outline_black_24dp)
                .error(R.drawable.ic_person_outline_black_24dp)
                .into(imageView);
        //load rsms to dashboard
        String data="user="+bundle.getString("username")+"&pass="+bundle.getString("password")+"&I1.x=0&I2.y=0";
        WebView webView = findViewById(R.id.webview);
        WebViewClient wvc = new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {

                try {
                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(url);
                    httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded");
                    HttpResponse httpResponse = client.execute(httpGet);

                    Header contentType = httpResponse.getEntity().getContentType();
                    Header encoding = httpResponse.getEntity().getContentEncoding();
                    InputStream responseInputStream = httpResponse.getEntity().getContent();

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

        //To go back to the previous page when back button is pressed
        webView.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if(event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    WebView webView = (WebView) v;

                    switch(keyCode)
                    {
                        case KeyEvent.KEYCODE_BACK:
                            if(webView.canGoBack())
                            {
                                webView.goBack();
                                return true;
                            }
                            break;
                    }
                }

                return false;
            }
        });
        webView.postUrl("https://www.rajagiritech.ac.in/stud/parent/varify.asp", EncodingUtils.getBytes(data,"BASE64"));
    }
    @Override

    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.dashboard) {
            // Handle the camera action
        } else if (id == R.id.attendance) {

        } else if (id == R.id.scores) {

        } else if (id == R.id.seating_plan) {
            //MainActivity obj=new MainActivity();
            //String pdf_uid = obj.sp.getString("username",null);
            Intent intent=new Intent(getApplicationContext(),SeatingArrangement.class);
            startActivity(intent);
            //WebView pdfView=findViewById(R.id.pdfView);
            //pdfView.getSettings().setJavaScriptEnabled(true);
            //String pdf_url="https://www.rajagiritech.ac.in/stud/parent/Seat/"+pdf_uid.substring(1)+".pdf";*/
            //pdfView.loadUrl("https://www.rajagiritech.ac.in/stud/parent/Seat/1403102.pdf");


        } else if (id == R.id.notice) {

        } else if (id == R.id.settings) {

        } else if (id == R.id.about_developer) {

        } else if (id == R.id.logout) {
            editor.clear();
            editor.commit();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

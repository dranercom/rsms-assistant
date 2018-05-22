package com.example.draner.rsms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import static com.example.draner.rsms.MainActivity.editor;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ProgressDialog dashboard_loading;
    String homepage_url="https://www.rajagiritech.ac.in/stud/parent/varify.asp";

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
        new LoadDashBoard().execute();
    }
    private class LoadDashBoard extends AsyncTask<Void,Void,Void>{
        String title;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dashboard_loading=new ProgressDialog(HomePage.this);
            dashboard_loading.setTitle("Student Dashboard");
            dashboard_loading.setMessage("Loading..!");
            dashboard_loading.setIndeterminate(false);
            dashboard_loading.show();
        }
        @Override
        protected Void doInBackground(Void...params){
            try{
                Document document=Jsoup.connect(homepage_url)
                                    .header("Content-Type", "application/x-www-form-urlencoded")
                                    .data("user","u1403102")
                                    .data("pass","5775")
                                    .data("I1.x","0")
                                    .data("I2.y","0")
                                    .post();
                title=document.title();
            }catch(IOException ae){
                ae.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            TextView dash_title = findViewById(R.id.dasboard_title);
            dash_title.setText(title);
            dashboard_loading.dismiss();
        }
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

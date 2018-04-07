package com.example.draner.rsms;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public SharedPreferences sp;
    public static SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button =findViewById(R.id.login_button);
        //To go to home page if already logged in
        sp=getSharedPreferences("login",MODE_PRIVATE);
        editor=sp.edit();
        if(sp.getString("username",null)!=null && sp.getString("password",null)!=null){
            Intent intent=new Intent(this,HomePage.class);
            intent.putExtra("username",sp.getString("username",null));
            intent.putExtra("password",sp.getString("password",null));
            startActivity(intent);
            finish();
        }
        button.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        EditText uid_form=findViewById(R.id.uid_form);
        EditText password_form=findViewById(R.id.password_form);
        String uname=uid_form.getText().toString();
        String pword=password_form.getText().toString();
        editor.putString("username",uname);
        editor.putString("password",pword);
        editor.commit();
        Intent intent=new Intent(getApplicationContext(),HomePage.class);
        intent.putExtra("username",uname);
        intent.putExtra("password",pword);
        startActivity(intent);
        finish();
    }
    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }
}



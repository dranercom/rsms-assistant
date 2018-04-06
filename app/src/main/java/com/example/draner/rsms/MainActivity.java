package com.example.draner.rsms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button =findViewById(R.id.login_button);
        button.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        EditText uid_form=findViewById(R.id.uid_form);
        EditText password_form=findViewById(R.id.password_form);
        String uname=uid_form.getText().toString();
        String pword=password_form.getText().toString();
        Intent intent=new Intent(getApplicationContext(),home_page.class);
        intent.putExtra("userame",uname);
        intent.putExtra("password",pword);
        startActivity(intent);
        setContentView(R.layout.activity_home_page);
    }
}



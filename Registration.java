package com.Smartcartt.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Registration extends AppCompatActivity {

  private Button createuser, createvendor;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        createuser = findViewById(R.id.create_user);
        createvendor= findViewById(R.id.create_vendor);

        createuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, User_Registration.class);
                startActivity(intent);
            }
        });

        createvendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, Vendor_Registration.class);
                startActivity(intent);
            }
        });


    }

    }

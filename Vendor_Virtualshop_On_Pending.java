package com.Smartcartt.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Vendor_Virtualshop_On_Pending extends AppCompatActivity {
    private Button lgout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_virtualshop_on_pending);
        lgout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Vendor_Virtualshop_On_Pending.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}
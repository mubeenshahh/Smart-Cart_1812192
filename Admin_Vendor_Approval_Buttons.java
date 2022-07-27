package com.Smartcartt.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;

public class Admin_Vendor_Approval_Buttons extends AppCompatActivity {
    private Button Newreq, ApprovedVendors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_vendor_approval_buttons);
        Newreq=findViewById(R.id.new_vendor_req);
        ApprovedVendors=findViewById(R.id.approved_vendors);
        Newreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent in=new Intent(Admin_Vendor_Approval_Buttons.this, Admin_Vendor_Approval.class);
            startActivity(in);
            }
        });
        ApprovedVendors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Admin_Vendor_Approval_Buttons.this, Admin_Approved_Vendors_list.class);
                startActivity(intent);
            }
        });



    }
}
package com.Smartcartt.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Admin extends AppCompatActivity {
    private Button logout, ShopApproval,VendorApproval, RiderApproval, Products, Orders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ShopApproval=findViewById(R.id.shop_approval);
        VendorApproval=findViewById(R.id.vendor_approval);
        RiderApproval=findViewById(R.id.rider_approval);
        Products=findViewById(R.id.admin_chck_prdts);
        logout=(Button)findViewById(R.id.admin_lg);


        Products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, Admin_View_Vendors_Before_Products.class);
                startActivity(intent);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, MainActivity.class);
                startActivity(intent);

            }
        });

        VendorApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Admin.this, Admin_Vendor_Approval_Buttons.class);
                startActivity(intent);

            }
        });
        ShopApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Admin.this, Admin_Shop_Approval_Buttons.class);
                startActivity(intent);
            }
        });

    }
}
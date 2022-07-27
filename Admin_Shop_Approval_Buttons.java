package com.Smartcartt.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Admin_Shop_Approval_Buttons extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_shop_approval);
        Button newShopRequests = findViewById(R.id.new_shop_req);
        Button approvedShops = findViewById(R.id.approved_shops);


        newShopRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Admin_Shop_Approval_Buttons.this,Admin_New_Shop_Req.class);
                startActivity(intent);

            }
        });
        approvedShops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent =new Intent(Admin_Shop_Approval_Buttons.this,Admin_Approved_Shops.class);
            startActivity(intent);
            }
        });

    }
}
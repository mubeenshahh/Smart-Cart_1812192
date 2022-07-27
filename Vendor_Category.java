package com.Smartcartt.app;


import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class Vendor_Category extends AppCompatActivity {
    private ImageView vegetables, fruits, dairyproducts, breakfast_itmems;
    private ImageView oil, spices, edibles, meat;
    private ImageView beverages, housecleaning, personalcare, householdessentials;
    private Button LogoutBtn, CheckOrdersBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_category);

        LogoutBtn = (Button) findViewById(R.id.admin_logout_btn);
        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Vendor_Category.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        CheckOrdersBtn = (Button) findViewById(R.id.check_orders_btn);


        CheckOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Vendor_Category.this, Vendor_New_Orders.class);
                startActivity(intent);
            }
        });



        vegetables = (ImageView) findViewById(R.id.vegetables);
        fruits = (ImageView) findViewById(R.id.fruits);
        dairyproducts = (ImageView) findViewById(R.id.dairy);
        breakfast_itmems = (ImageView) findViewById(R.id.breakfast);

        oil = (ImageView) findViewById(R.id.oil);
        spices = (ImageView) findViewById(R.id.spices);
        edibles = (ImageView) findViewById(R.id.edibles);
        meat = (ImageView) findViewById(R.id.meat);

        beverages = (ImageView) findViewById(R.id.beverages_image);
        housecleaning = (ImageView) findViewById(R.id.housecleaning);
        personalcare = (ImageView) findViewById(R.id.personalcare);
        householdessentials = (ImageView) findViewById(R.id.householdessentials);


        vegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Vendor_Category.this, Vendor_Add_New_Product.class);
                intent.putExtra("category", "Vegetables");
                startActivity(intent);
            }
        });
        fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Vendor_Category.this, Vendor_Add_New_Product.class);
                intent.putExtra("category", "Fruits");
                startActivity(intent);
            }
        });


        dairyproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Vendor_Category.this, Vendor_Add_New_Product.class);
                intent.putExtra("category", "Dairy Products");
                startActivity(intent);
            }
        });


        breakfast_itmems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Vendor_Category.this, Vendor_Add_New_Product.class);
                intent.putExtra("category", "Breakfast Section");
                startActivity(intent);
            }
        });


        oil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Vendor_Category.this, Vendor_Add_New_Product.class);
                intent.putExtra("category", "Edible Oil");
                startActivity(intent);
            }
        });


        spices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Vendor_Category.this, Vendor_Add_New_Product.class);
                intent.putExtra("category", "Spices");
                startActivity(intent);
            }
        });



        edibles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Vendor_Category.this, Vendor_Add_New_Product.class);
                intent.putExtra("category", "Instant Edibles");
                startActivity(intent);
            }
        });


        meat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Vendor_Category.this, Vendor_Add_New_Product.class);
                intent.putExtra("category", "Meat Items");
                startActivity(intent);
            }
        });



        beverages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Vendor_Category.this, Vendor_Add_New_Product.class);
                intent.putExtra("category", "Beverages");
                startActivity(intent);
            }
        });


        housecleaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Vendor_Category.this, Vendor_Add_New_Product.class);
                intent.putExtra("category", "House cleaning ");
                startActivity(intent);
            }
        });


        personalcare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Vendor_Category.this, Vendor_Add_New_Product.class);
                intent.putExtra("category", "Personal care Products");
                startActivity(intent);
            }
        });


        householdessentials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Vendor_Category.this, Vendor_Add_New_Product.class);
                intent.putExtra("category", "Household Essentials");
                startActivity(intent);
            }
        });
    }
}

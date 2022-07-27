package com.Smartcartt.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.Smartcartt.app.Model.Users;
import com.Smartcartt.app.Model.Virtual_shops;
import com.Smartcartt.app.ViewHolder.AdminViewShopViewHolder;
import com.Smartcartt.app.ViewHolder.ShopViewHolder;
import com.Smartcartt.app.ViewHolder.VendorViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Admin_View_Vendors_Before_Products extends AppCompatActivity {
    private DatabaseReference VendorRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_vendors_before_products);
        recyclerView = findViewById(R.id.a_view_vendors_info);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        VendorRef= FirebaseDatabase.getInstance().getReference().child("Vendors");


        FirebaseRecyclerOptions<Virtual_shops> options =
                new FirebaseRecyclerOptions.Builder<Virtual_shops>()
                        .setQuery(VendorRef, Virtual_shops.class)
                        .build();


        FirebaseRecyclerAdapter<Virtual_shops, AdminViewShopViewHolder> adapter =
                new FirebaseRecyclerAdapter<Virtual_shops, AdminViewShopViewHolder>(options) {

                    @SuppressLint("SetTextI18n")
                    @Override
                    protected void onBindViewHolder(@NonNull AdminViewShopViewHolder holder, int position, @NonNull final Virtual_shops model) {

                        holder.ShopName.setText("Shopname: "+model.getShopname());
                        holder.ShopAddress.setText("Shopaddress: "+model.getShopaddress());
                        holder.ShopApprovalStatus.setText("ShopApprovalStatus: "+model.getVirtualShopApproval());
                        holder.V_Phone.setText("Vendor phone: "+model.getPhone());

                        Picasso.get().load(model.getShopimage()).into(holder.ShopImagee);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String uID = getRef(holder.getAdapterPosition()).getKey();
                                Current.v= model.getPhone();
                                Intent intent =new Intent(Admin_View_Vendors_Before_Products.this, Admin_View_products.class);
                                intent.putExtra("uid",uID);

                                startActivity(intent);

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public AdminViewShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_shop, parent, false);
                        AdminViewShopViewHolder hlder = new AdminViewShopViewHolder(view);
                        return hlder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();



    }}



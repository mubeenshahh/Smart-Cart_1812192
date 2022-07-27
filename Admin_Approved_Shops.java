package com.Smartcartt.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.Smartcartt.app.Model.Users;
import com.Smartcartt.app.Model.Virtual_shops;
import com.Smartcartt.app.ViewHolder.AdminViewShopViewHolder;
import com.Smartcartt.app.ViewHolder.VendorViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Admin_Approved_Shops extends AppCompatActivity {
    DatabaseReference Virtualshopref, Dbref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_approved_shops);
        setContentView(R.layout.activity_approved_vendors_list);
        RecyclerView recyclerView = findViewById(R.id.approved_vendr_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Virtualshopref= FirebaseDatabase.getInstance().getReference().child("Approved Virtual Shops");
        Dbref=FirebaseDatabase.getInstance().getReference().child("Vendors");


        FirebaseRecyclerOptions<Virtual_shops> options =
                new FirebaseRecyclerOptions.Builder<Virtual_shops>()
                        .setQuery(Virtualshopref, Virtual_shops.class)
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
                                CharSequence[] options = new CharSequence[]
                                        {
                                                "Terminate Vendor Access"
                                        };
                                AlertDialog.Builder builder = new AlertDialog.Builder(Admin_Approved_Shops.this);
                                builder.setTitle("Cancel Approval?");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (i == 0) {

                                            Virtualshopref.child(model.getPhone()).child("shopname").removeValue();
                                            Virtualshopref.child(model.getPhone()).child("shopaddress").removeValue();
                                            Virtualshopref.child(model.getPhone()).child("shopimage").removeValue();
                                            Virtualshopref.child(model.getPhone()).child("virtualshopstatus").setValue("not created");
                                            Virtualshopref.child(model.getPhone()).child("VirtualShopApproval").setValue("not approved");
                                            Toast.makeText(Admin_Approved_Shops.this, "Successfully, Terminated.", Toast.LENGTH_SHORT).show();


                                        }

                                    }
                                });
                                builder.show();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public AdminViewShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_shop, parent, false);
                        AdminViewShopViewHolder holder = new AdminViewShopViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}
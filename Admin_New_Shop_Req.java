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

import com.Smartcartt.app.Model.Virtual_shops;
import com.Smartcartt.app.ViewHolder.AdminViewShopViewHolder;
import com.Smartcartt.app.ViewHolder.VendorViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class Admin_New_Shop_Req extends AppCompatActivity {
    DatabaseReference TempVendorref, VendorRef,DbRef;
    RecyclerView recycler;
    RecyclerView.LayoutManager layoutmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_shop_req);

        recycler=findViewById(R.id.recycler_new_shop_req);
        recycler.setHasFixedSize(true);
        layoutmanager =new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutmanager);
        DbRef=FirebaseDatabase.getInstance().getReference().child("Approved Virtual Shops");
        TempVendorref= FirebaseDatabase.getInstance().getReference().child("VirtualShops before Approval");
        VendorRef= FirebaseDatabase.getInstance().getReference().child("Vendors");


        FirebaseRecyclerOptions<Virtual_shops> options =
                new FirebaseRecyclerOptions.Builder<Virtual_shops>().setQuery(TempVendorref,Virtual_shops.class).build();
        @SuppressLint("SetTextI18n")
        FirebaseRecyclerAdapter<Virtual_shops, AdminViewShopViewHolder> adapter=new FirebaseRecyclerAdapter<Virtual_shops, AdminViewShopViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AdminViewShopViewHolder holder, int position, @NonNull Virtual_shops model) {

                holder.ShopName.setText("Shopname: "+model.getShopname());
                holder.ShopAddress.setText("Shopaddress: "+model.getShopaddress());
                holder.ShopApprovalStatus.setText("ShopApprovalStatus: "+model.getVirtualShopApproval());
                holder.V_Phone.setText("Vendor phone: "+model.getPhone());
                Picasso.get().load(model.getShopimage()).into(holder.ShopImagee);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence[] options = new CharSequence[]
                                {
                                        "Approve" ,
                                        "Reject"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_New_Shop_Req.this);
                        builder.setTitle("Approve or Reject");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i == 0) {
                                    VendorRef.child(model.getPhone()).child("VirtualShopApproval").setValue("approved");
                                    DbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            HashMap <String,Object> Userdata = new HashMap<>();
                                            Userdata.put("shopname",model.getShopname());
                                            Userdata.put("shopaddress",model.getShopaddress());
                                            Userdata.put("shopimage",model.getShopimage());
                                            Userdata.put("phone", model.getPhone());
                                            DbRef.child(model.getPhone()).updateChildren(Userdata);
                                            VendorRef.child(model.getPhone()).updateChildren(Userdata);

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });



                                    holder.ShopApprovalStatus.setText("Approval Status: Approved");
                                    Toast.makeText(Admin_New_Shop_Req.this, "Successfully, Approved. Check Approved Shops.", Toast.LENGTH_SHORT).show();
                                    TempVendorref.child(model.getPhone()).removeValue();

                                }
                                if (i == 1) {

                                    VendorRef.child(model.getPhone()).child("AdminApproval").setValue("rejected");
                                    holder.ShopApprovalStatus.setText("Approval Status: Rejected");

                                    TempVendorref.child(model.getPhone()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(Admin_New_Shop_Req.this, "Successfully, Rejected", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });


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

        recycler.setAdapter(adapter);
        adapter.startListening();

    }
}

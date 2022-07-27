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

import com.Smartcartt.app.ViewHolder.VendorViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Admin_Vendor_Approval extends AppCompatActivity {
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference TempVendorRef,VendorRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_vendor_approval);

        RecyclerView recyclerView = findViewById(R.id.recycler_vendor_list);
        TempVendorRef= FirebaseDatabase.getInstance().getReference().child("Vendor before approval");
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        VendorRef=FirebaseDatabase.getInstance().getReference().child("Vendors");


        FirebaseRecyclerOptions<Users> options =
                    new FirebaseRecyclerOptions.Builder<Users>()
                            .setQuery(TempVendorRef, Users.class)
                            .build();


            FirebaseRecyclerAdapter<Users, VendorViewHolder> adapter =
                    new FirebaseRecyclerAdapter<Users, VendorViewHolder>(options) {

                        @SuppressLint("SetTextI18n")
                        @Override
                        protected void onBindViewHolder(@NonNull VendorViewHolder holder, int position, @NonNull final Users model) {
                            if (model.getAdminApproval().equals("not approved")) {
                                holder.VendorName.setText("Name: " + model.getName());
                                holder.VendorPhone.setText("Phone: " + model.getPhone());
                                holder.VendorPassword.setText("Password: " + model.getPassword());
                                holder.VendorEmail.setText("Email: " + model.getEmail());
                                holder.ApprovalStatus.setText("Approval Status: "+model.getAdminApproval());
                                holder.itemView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        CharSequence[] options = new CharSequence[]
                                                {
                                                        "Approve",
                                                        "Reject"
                                                };
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_Vendor_Approval.this);
                                        builder.setTitle("Approve or Reject");
                                        builder.setItems(options, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                if (i == 0) {
                                                    VendorRef.child(model.getPhone()).child("AdminApproval").setValue("approved");
                                                    holder.ApprovalStatus.setText("Approval Status: Approved");
                                                    Toast.makeText(Admin_Vendor_Approval.this, "Successfully, Approved. Check Approved Vendors.", Toast.LENGTH_SHORT).show();
                                                    TempVendorRef.child(model.getPhone()).removeValue();

                                                }
                                                if (i == 1) {

                                                    VendorRef.child(model.getPhone()).child("AdminApproval").setValue("rejected");
                                                    holder.ApprovalStatus.setText("Approval Status: Rejected");

                                                    TempVendorRef.child(model.getPhone()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(Admin_Vendor_Approval.this, "Successfully, Rejected", Toast.LENGTH_SHORT).show();

                                                            }
                                                        }
                                                    });


                                                }
                                            }
                                        });
                                        builder.show();
                                    }
                                });
                            } else {
                                Toast.makeText(Admin_Vendor_Approval.this, "There are no new Vendor Registration ", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @NonNull
                        @Override
                        public VendorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_admin_view_vendor, parent, false);
                            VendorViewHolder hlder = new VendorViewHolder(view);
                            return hlder;
                        }
                    };
            recyclerView.setAdapter(adapter);
            adapter.startListening();



    }}


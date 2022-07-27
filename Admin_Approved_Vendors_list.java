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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_Approved_Vendors_list extends AppCompatActivity {
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference VendorRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_vendors_list);
        RecyclerView recyclerView = findViewById(R.id.approved_vendr_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        VendorRef=FirebaseDatabase.getInstance().getReference().child("Vendors");


        FirebaseRecyclerOptions<Users> options =
                new FirebaseRecyclerOptions.Builder<Users>()
                        .setQuery(VendorRef, Users.class)
                        .build();


        FirebaseRecyclerAdapter<Users, VendorViewHolder> adapter =
                new FirebaseRecyclerAdapter<Users, VendorViewHolder>(options) {

                    @SuppressLint("SetTextI18n")
                    @Override
                    protected void onBindViewHolder(@NonNull VendorViewHolder holder, int position, @NonNull final Users model) {

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
                                                    "Terminate Vendor Access"
                                            };
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Admin_Approved_Vendors_list.this);
                                    builder.setTitle("Cancel Approval?");
                                    builder.setItems(options, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (i == 0) {


                                                VendorRef.child(model.getPhone()).child("AdminApproval").setValue("rejected");
                                                Toast.makeText(Admin_Approved_Vendors_list.this, "Successfully, Terminated.", Toast.LENGTH_SHORT).show();


                                            }

                                        }
                                    });
                                    builder.show();
                                }
                            });
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



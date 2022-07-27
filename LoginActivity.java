package com.Smartcartt.app;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.Smartcartt.app.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;
import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    private EditText InputPhoneNumber, InputPassword, InputEmailAddress;
    private Button LoginButton;
    private ProgressDialog loadingBar;
    private TextView Vendorlink, NotVendorlink, Createaccount;
    private String parentDbName = "Users";
    private CheckBox chkBoxRememberMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginButton = (Button) findViewById(R.id.login_btn);
        InputPassword = (EditText) findViewById(R.id.login_password_input);
        InputPhoneNumber = (EditText) findViewById(R.id.login_phone_number_input);
        InputEmailAddress=(EditText) findViewById(R.id.login_email_address_input);
        Vendorlink = (TextView) findViewById(R.id.login_as_vendor);
        NotVendorlink= (TextView) findViewById(R.id.login_as_user);
        Createaccount=(TextView) findViewById(R.id.reg_link);
        loadingBar = new ProgressDialog(this);
        chkBoxRememberMe = (CheckBox) findViewById(R.id.remember_me_chkb);
        Paper.init(this);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });

        Createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, com.Smartcartt.app.Registration.class);

                startActivity(i);
            }
        });





        Vendorlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                LoginButton.setText("Login Vendor");
                Vendorlink.setVisibility(View.INVISIBLE);
                NotVendorlink.setVisibility(View.VISIBLE);
                parentDbName = "Vendors";
            }
        });

        NotVendorlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                LoginButton.setText("Login User");
                Vendorlink.setVisibility(View.VISIBLE);
                NotVendorlink.setVisibility(View.INVISIBLE);
                parentDbName = "Users";
            }
        });

    }
    private void LoginUser()
    {
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();
        String email= InputEmailAddress.getText().toString().trim();
        if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();


            AllowAccessToAccount(phone, password,email);
        }
    }
    private void AllowAccessToAccount(final String phone, final String password, final String email)
    {

        if(chkBoxRememberMe.isChecked())
        {
            Paper.book().write(Current.UserPhoneKey, phone);
            Paper.book().write(Current.UserPasswordKey, password);
            Paper.book().write(Current.UserEmailKey,email);
        }
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(parentDbName).child(phone).exists()) {

                    Users usersData = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);


                    assert usersData != null;
                    if (usersData.getPhone().equals(phone)) {
                        if (usersData.getPassword().equals(password)) {
                            if (usersData.getEmail().equals(email)) {
                                if(usersData.getIsAdmin().equals("0")){


                                if (parentDbName.equals("Vendors")) {
                                    switch (usersData.getAdminApproval()) {
                                        case "approved":


                                            if (usersData.getVirtualshopstatus().equals("not created")) {
                                                Toast.makeText(LoginActivity.this, "Welcome Vendor, Please create a Virtual shop to continue", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(LoginActivity.this, Vendor_Virtualshop.class);
                                                Current.currentOnlineUser = usersData;
                                                startActivity(intent);
                                                finish();


                                            } else {
                                                if (usersData.getVirtualShopApproval().equals("approved")) {
                                                    Intent intent = new Intent(LoginActivity.this, Vendor_Category.class);
                                                    Current.currentOnlineUser = usersData;
                                                    startActivity(intent);
                                                } else {
                                                    Intent intent = new Intent(LoginActivity.this, Vendor_Virtualshop_On_Pending.class);
                                                    Current.currentOnlineUser = usersData;
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }
                                            loadingBar.dismiss();
                                            break;
                                        case "not approved":
                                            Toast.makeText(LoginActivity.this, "Wait, while your registration request is being checked. Check back later", Toast.LENGTH_SHORT).show();
                                            loadingBar.dismiss();
                                            break;
                                        case "rejected":

                                            Toast.makeText(LoginActivity.this, "There was something wrong with your credentials, your request was rejected by the Admin. Please, try registering again..", Toast.LENGTH_SHORT).show();
                                            RootRef.child(parentDbName).child(usersData.getPhone())
                                                    .removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(LoginActivity.this, "Your credentials were removed.  ", Toast.LENGTH_LONG).show();
                                                                loadingBar.dismiss();
                                                            }
                                                        }
                                                    });


                                            break;
                                    }
                                } else if (parentDbName.equals("Users")) {
                                    Toast.makeText(LoginActivity.this, "logged in Successfully...", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();

                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    Current.currentOnlineUser = usersData;
                                    startActivity(intent);
                                }

                            }else  {
                                    Toast.makeText(LoginActivity.this, "Admin logged in Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this,Admin.class );
                                    startActivity(intent);
                                    finish();
                                }
                            }
                         else {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Email is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        loadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                }} else {
                    Toast.makeText(LoginActivity.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }}


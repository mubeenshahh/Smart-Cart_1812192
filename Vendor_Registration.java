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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Vendor_Registration extends AppCompatActivity {
    private Button CreateAccountButton;
    private EditText InputName, InputPhoneNumber, InputPassword, InputEmailAddress;
    private ProgressDialog loadingBar;
    private TextView login_link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_registration);
        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        InputName = (EditText) findViewById(R.id.register_username_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        InputPhoneNumber = (EditText) findViewById(R.id.register_phone_number_input);
        InputEmailAddress = (EditText) findViewById(R.id.register_email_address_input);
        login_link= (TextView) findViewById(R.id.login_instead);
        loadingBar = new ProgressDialog(this);


        login_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Vendor_Registration.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateVendorAccount();
            }
        });
    }
    private void CreateVendorAccount(){
        String name = InputName.getText().toString();
        String phone = InputPhoneNumber.getText().toString();
        String email = InputEmailAddress.getText().toString();
        String password = InputPassword.getText().toString();
        String virtualshopstatus ="not created";
        String AdminApproval="not approved";

        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please write your name...", Toast.LENGTH_SHORT).show();
        } else if (name.length()<3){
            Toast.makeText(this, "Name cannot be less than 3 characters", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please write your Email Address...", Toast.LENGTH_SHORT).show();
        } else if(email.length()<6){
            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        } else if (phone.length()<11){
            Toast.makeText(this, "Invalid Phone number", Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        } else if (password.length()<8){
            Toast.makeText(this, "Password can not be less than 8 characters", Toast.LENGTH_SHORT).show();
        }

        else
        {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            Validatecradentials(name, email, phone, password, virtualshopstatus, AdminApproval);

        }

    }



    private void Validatecradentials(final String name, final String email, final String phone,final String password, final String virtualshopstatus, final String AdminApproval){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://smartcart-application-default-rtdb.firebaseio.com/");
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!(dataSnapshot.child("Vendors").child(phone).exists())){

                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone", phone);
                    userdataMap.put("email", email);
                    userdataMap.put("password", password);
                    userdataMap.put("name", name);
                    userdataMap.put("isAdmin", "0");
                    userdataMap.put("virtualshopstatus",virtualshopstatus);
                    userdataMap.put("AdminApproval",AdminApproval);
                    RootRef.child("Vendor before approval").child(phone).updateChildren(userdataMap);
                    RootRef.child("Vendors").child(phone).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful())
                            {
                                Toast.makeText(Vendor_Registration.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(Vendor_Registration.this, com.Smartcartt.app.LoginActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                loadingBar.dismiss();
                                Toast.makeText(Vendor_Registration.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



                }
                else {
                    Toast.makeText(Vendor_Registration.this, "This " + phone + " already exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(Vendor_Registration.this, "Please try again using another phone number.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Vendor_Registration.this, com.Smartcartt.app.MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

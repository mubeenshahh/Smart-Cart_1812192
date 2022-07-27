package com.Smartcartt.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;



import java.util.HashMap;

public class Vendor_Virtualshop extends AppCompatActivity {
    private EditText Inputshopname, Inputshopaddress, InputvendorPhone;
    private Button Createshopbtn;
    private ImageView shopimmage;
    private StorageReference shopimageRef;
    private static final int GalleryPick = 1;
    private Uri imageUri;
    private ProgressDialog loadingBar;
    private String  downloadImageUrl, shopname, shopaddress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtualshop);

        Inputshopname =findViewById(R.id.shop_name);
        Inputshopaddress =  findViewById(R.id.shop_address);
        shopimageRef = FirebaseStorage.getInstance().getReference().child("Shop Images");
        InputvendorPhone =  findViewById(R.id.vendor_phone);
        shopimmage =  findViewById(R.id.shop_img);
        Createshopbtn =findViewById(R.id.create_shop);
        loadingBar = new ProgressDialog(this);

        shopimmage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });

        Createshopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateShopData();
            }
        });
    }

    private void OpenGallery(){
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            imageUri = data.getData();
            shopimmage.setImageURI(imageUri);
        }
    }
    private void ValidateShopData() {

        shopname = Inputshopname.getText().toString();
        shopaddress = Inputshopaddress.getText().toString();
        if (imageUri == null)
        {
            Toast.makeText(this, "Shop image is mandatory...", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(shopname))
        {
            Toast.makeText(this, "Please provide a shop name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(shopaddress))
        {
            Toast.makeText(this, "Please write shop address...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreProductInformation();
        }

    }
    private void StoreProductInformation()
    {
        loadingBar.setTitle("Adding New Virtual shop");
        loadingBar.setMessage("Dear Vendor, please wait while we are creating your new virtual shop.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();




        final StorageReference filePath = shopimageRef.child(imageUri.getLastPathSegment() + Current.currentOnlineUser.getPhone()+ ".jpg");

        final UploadTask uploadTask = filePath.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(Vendor_Virtualshop.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Vendor_Virtualshop.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();



                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();

                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(Vendor_Virtualshop.this, "got the shop image Url Successfully...", Toast.LENGTH_SHORT).show();

                            RegisterVirtualShop();
                        }
                    }
                });
            }
        });

    }




    public void RegisterVirtualShop() {

        String shopname = Inputshopname.getText().toString();
        String shopaddress = Inputshopaddress.getText().toString();
        String conf_phone = InputvendorPhone.getText().toString().trim();
        String virtualshopstatus="created";

        if (TextUtils.isEmpty(shopname) || TextUtils.isEmpty(shopaddress) || TextUtils.isEmpty(conf_phone)) {
            Toast.makeText(this, "Please fill all the fields ", Toast.LENGTH_SHORT).show();
        } else if (shopname.length() < 3) {
            Toast.makeText(this, "Name cannot be less than 6 characters", Toast.LENGTH_SHORT).show();
        } else if (conf_phone.length() <11) {
            Toast.makeText(this, "Invalid Phone number", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Create Shop");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateInputcradentials(shopname, shopaddress, conf_phone, virtualshopstatus);



        }

    }

    public void ValidateInputcradentials(final String shopname, final String shopaddress, final String conf_phone, final String virtualshopstatus) {
        final DatabaseReference Root;

        Root = FirebaseDatabase.getInstance().getReferenceFromUrl("https://smartcart-application-default-rtdb.firebaseio.com/");
        Root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                
                if ((snapshot.child("Vendors").child(conf_phone).exists())) {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("shopname", shopname);
                    userdataMap.put("shopaddress", shopaddress);
                    userdataMap.put("shopimage", downloadImageUrl);
                    userdataMap.put("VirtualShopApproval","not approved");
                    Root.child("Vendors").child(conf_phone).child("VirtualShopApproval").setValue("not approved");
                    Root.child("Vendors").child(conf_phone).child("virtualshopstatus").setValue(virtualshopstatus);

                    Root.child("VirtualShops before Approval").child(conf_phone).child("phone").setValue(conf_phone);
                    Root.child("VirtualShops before Approval").child(conf_phone).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(Vendor_Virtualshop.this, "Congratulations, your Virtual shop request has been forwarded. Wait for the approval", Toast.LENGTH_LONG).show();
                                loadingBar.dismiss();


                                Intent intent = new Intent(Vendor_Virtualshop.this, Vendor_Virtualshop_On_Pending.class);
                                startActivity(intent);
                                finish();
                            } else {
                                loadingBar.dismiss();
                                Toast.makeText(Vendor_Virtualshop.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else {

                    Toast.makeText(Vendor_Virtualshop.this, "Please enter the same number which was provided at registration.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(Vendor_Virtualshop.this, "Please try again ", Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                }
            });

    }

    }
package com.Smartcartt.app.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Smartcartt.app.Interface.ItemClickListner;
import com.Smartcartt.app.R;
import com.google.firebase.database.core.Context;

public class VendorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ItemClickListner listner;
    public TextView VendorName, VendorPhone, VendorEmail, VendorPassword,ApprovalStatus;


    public VendorViewHolder(View itemView){
        super(itemView);
        VendorName=itemView.findViewById(R.id.v_name);
        VendorPhone=itemView.findViewById(R.id.v_phone);
        VendorEmail=itemView.findViewById(R.id.v_email);
        VendorPassword=itemView.findViewById(R.id.v_password);
        ApprovalStatus=itemView.findViewById(R.id.apr_status);


    }
    @Override
    public void onClick(View view)
    {
        listner.onClick(view, getAdapterPosition(), false);
    }

    public void setItemClickListner(ItemClickListner listner) {
        this.listner = listner;
    }


}

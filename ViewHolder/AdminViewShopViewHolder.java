package com.Smartcartt.app.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.Smartcartt.app.Interface.ItemClickListner;
import com.Smartcartt.app.R;

public class AdminViewShopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ItemClickListner listner;
    public TextView ShopName, ShopAddress, V_Phone,ShopApprovalStatus;
    public ImageView ShopImagee;


    public AdminViewShopViewHolder(View itemView){
        super(itemView);
        ShopName=itemView.findViewById(R.id.a_shopname);
        ShopAddress=itemView.findViewById(R.id.a_shoaddress);
        V_Phone=itemView.findViewById(R.id.a_phone);
        ShopApprovalStatus=itemView.findViewById(R.id.a_appr_status);
        ShopImagee=itemView.findViewById(R.id.a_shopimage);



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

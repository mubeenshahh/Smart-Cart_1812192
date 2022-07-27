package com.Smartcartt.app.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Smartcartt.app.Interface.ItemClickListner;
import com.Smartcartt.app.R;
import com.google.firebase.database.core.Context;

public class ShopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView sname, saddress;
    public ImageView simage;
    private ItemClickListner listner;

    public ShopViewHolder( View itemView) {
        super(itemView);
        Context a;
        sname=(TextView) itemView.findViewById(R.id.sh_name);
        simage = (ImageView) itemView.findViewById(R.id.shop_image);
        saddress=(TextView) itemView.findViewById(R.id.shop_address);

    }


    @Override
    public void onClick(View view) {
        listner.onClick(view,getAdapterPosition(),false);
    }

    public void setItemClickListner(ItemClickListner listner) {
        this.listner = listner;
    }

}

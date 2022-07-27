package com.Smartcartt.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Smartcartt.app.Model.Products;
import com.Smartcartt.app.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class User_Show_Products extends AppCompatActivity {
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference ProductsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_show_products);

        recyclerView = findViewById(R.id.recycler_products_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Vendors").child(Current.v).child("Products");


            FirebaseRecyclerOptions<Products> options =
                    new FirebaseRecyclerOptions.Builder<Products>()
                            .setQuery(ProductsRef, Products.class)
                            .build();

            FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                    new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model)
                        {
                            holder.txtProductName.setText(model.getPname());
                            holder.txtProductDescription.setText(model.getDescription());
                            holder.txtProductPrice.setText("Price = " + model.getPrice() + "Rs.");
                            Picasso.get().load(model.getImage()).into(holder.imageView);
                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent =new Intent(User_Show_Products.this, User_Add_Product_To_Cart.class);
                                    intent.putExtra("pid",model.getPid());
                                    Current.CurrentVendor=Current.v;
                                    startActivity(intent);
                                }
                            });
                        }

                        @NonNull
                        @Override
                        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                            ProductViewHolder holder = new ProductViewHolder(view);
                            return holder;
                        }
                    };
            recyclerView.setAdapter(adapter);
            adapter.startListening();

        }


}
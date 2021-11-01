package com.example.lab4database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private ArrayList<Product> productArrayList;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView productName, productPrice, productId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.idProductName);
            productPrice = itemView.findViewById(R.id.idProductPrice);
            productId = itemView.findViewById(R.id.idProductId);
        }
    }

    public ProductAdapter(ArrayList<Product> productModalArrayList, Context context) {
        this.productArrayList = productModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,
               parent, false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        Product product = productArrayList.get(position);
        holder.productName.setText(product.getProductName());
        holder.productPrice.setText(String.valueOf(product.getPrice()));
        holder.productId.setText(String.valueOf(product.getId()));
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }
}

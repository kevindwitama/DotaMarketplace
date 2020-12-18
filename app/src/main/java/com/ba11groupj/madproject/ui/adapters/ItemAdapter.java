package com.ba11groupj.madproject.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ba11groupj.madproject.R;
import com.ba11groupj.madproject.helpers.DBHelper;
import com.ba11groupj.madproject.models.Item;
import com.ba11groupj.madproject.models.User;
import com.ba11groupj.madproject.ui.activities.BuyItemActivity;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    Context mCtx;
    ArrayList<Item> arrItem;
    User user;

    DBHelper database;

    public ItemAdapter(Context mCtx, User user) {
        this.mCtx = mCtx;
        DBHelper database = new DBHelper(mCtx);
        arrItem = database.fetchItems();
        this.user = user;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mCtx).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = arrItem.get(position);

        final int strItemId = item.getId();

        holder.lblItemName.setText(item.getName());
        holder.lblItemPrice.setText("Rp " + item.getPrice());
        holder.lblItemStock.setText("Stock: " + item.getStock());

        holder.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, BuyItemActivity.class);
                Bundle bundle = new Bundle();

                bundle.putInt("itemId", strItemId);
                bundle.putInt("userId", user.getId());
                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrItem.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView lblItemName;
        TextView lblItemPrice;
        TextView lblItemStock;
        Button btnBuy;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            lblItemName = itemView.findViewById(R.id.lblItemName);
            lblItemPrice = itemView.findViewById(R.id.lblItemPrice);
            lblItemStock = itemView.findViewById(R.id.lbltemStock);
            btnBuy = itemView.findViewById(R.id.btnBuyItem);
        }
    }
}

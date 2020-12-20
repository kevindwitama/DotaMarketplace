package com.github.kevindwitama.dotamarketplace.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.kevindwitama.dotamarketplace.R;
import com.github.kevindwitama.dotamarketplace.helpers.DBHelper;
import com.github.kevindwitama.dotamarketplace.models.Item;
import com.github.kevindwitama.dotamarketplace.models.User;
import com.github.kevindwitama.dotamarketplace.ui.activities.BuyItemActivity;

import java.util.ArrayList;

/**
 * Final Project ISYS6203 Mobile Application Development
 * Lab BL11 / XB11
 *
 * Dota Marketplace
 *
 * Contributed by
 * 2201825535 - Kevin Dwitama Putra
 * 2201836330 - Natasha Anugrah
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    final Context mCtx;
    final ArrayList<Item> arrItem;
    final User user;

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

        final int imgId = getDrawableId(position); // ambil resource id untuk gambar buat item di posisi skrg

        holder.lblItemName.setText(item.getName());
        holder.lblItemPrice.setText("Rp " + item.getPrice());
        holder.lblItemStock.setText("Stock: " + item.getStock());
        holder.imageProducts.setImageResource(imgId);

        // buka menu buy item
        holder.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, BuyItemActivity.class);
                Bundle bundle = new Bundle();

                // kirim data ke menu buy item
                bundle.putInt("itemId", strItemId);
                bundle.putInt("userId", user.getId());
                bundle.putInt("imgId", imgId);
                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });
    }

    private int getDrawableId(int position) {
        // ambil resource id untuk gambar buat item di posisi skrg
        switch (position) {
            case 0:
                return R.drawable.foto1;
            case 1:
                return R.drawable.foto2;
            case 2:
                return R.drawable.foto3;
            case 3:
                return R.drawable.foto4;
            case 4:
                return R.drawable.foto5;
            default:
                return R.drawable.foto6;
        }
    }

    @Override
    public int getItemCount() {
        return arrItem.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        final TextView lblItemName;
        final TextView lblItemPrice;
        final TextView lblItemStock;
        final ImageView imageProducts;
        final Button btnBuy;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            lblItemName = itemView.findViewById(R.id.lblItemName);
            lblItemPrice = itemView.findViewById(R.id.lblItemPrice);
            lblItemStock = itemView.findViewById(R.id.lbltemStock);
            imageProducts = itemView.findViewById(R.id.imgGameBuy);
            btnBuy = itemView.findViewById(R.id.btnBuyItem);
        }
    }
}

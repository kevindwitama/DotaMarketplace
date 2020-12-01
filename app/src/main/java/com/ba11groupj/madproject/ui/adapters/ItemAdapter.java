package com.ba11groupj.madproject.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ba11groupj.madproject.ui.activities.BuyItemActivity;
import com.ba11groupj.madproject.helpers.DataHelper;
import com.ba11groupj.madproject.models.Item;
import com.ba11groupj.madproject.R;
import com.ba11groupj.madproject.models.User;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ViewHolder> {

    Context mCtx;
    ArrayList<Item> arrItem;
    User user;

    public ItemAdapter(Context mCtx, User user) {
        this.mCtx = mCtx;
        this.arrItem = DataHelper.arrItem;
        this.user = user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mCtx).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = arrItem.get(position);

        final String strItemId = item.getId();

        holder.fldItemName.setText(item.getName());
        holder.fldItemPrice.setText("Rp " + item.getPrice());
        holder.fldItemStock.setText("Stock: " + item.getStock());

        holder.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, BuyItemActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("itemId", strItemId);
                bundle.putString("userId", user.getUserId());
                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrItem.size();
    }
}

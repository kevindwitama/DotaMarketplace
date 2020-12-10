package com.ba11groupj.madproject.ui.adapters;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ba11groupj.madproject.R;

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


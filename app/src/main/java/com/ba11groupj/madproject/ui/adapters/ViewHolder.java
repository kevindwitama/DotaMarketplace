package com.ba11groupj.madproject.ui.adapters;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ba11groupj.madproject.R;

class ViewHolder extends RecyclerView.ViewHolder {

    TextView fldItemName;
    TextView fldItemPrice;
    TextView fldItemStock;
    Button btnBuy;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        fldItemName = itemView.findViewById(R.id.txtItemName);
        fldItemPrice = itemView.findViewById(R.id.txtItemPrice);
        fldItemStock = itemView.findViewById(R.id.txtItemStock);
        btnBuy = itemView.findViewById(R.id.btnBuyItem);
    }
}


package com.ba11groupj.madproject.ui.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ba11groupj.madproject.R;

class TransViewHolder extends RecyclerView.ViewHolder {

    TextView lblTransDate;
    TextView lblItemName;
    TextView lblItemQty;
    TextView lblMinBalance;

    public TransViewHolder(@NonNull View transView) {
        super(transView);

        lblTransDate = transView.findViewById(R.id.lblTransDate);
        lblItemName = transView.findViewById(R.id.lblItemName);
        lblItemQty = transView.findViewById(R.id.lblItemQty);
        lblMinBalance = transView.findViewById(R.id.lblMinBalance);
    }
}


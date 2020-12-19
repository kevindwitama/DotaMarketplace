package com.github.kevindwitama.dotamarketplace.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.kevindwitama.dotamarketplace.R;
import com.github.kevindwitama.dotamarketplace.helpers.DBHelper;
import com.github.kevindwitama.dotamarketplace.helpers.DataHelper;
import com.github.kevindwitama.dotamarketplace.models.Transaction;

import java.util.ArrayList;

/**
 * Final Project ISYS6203 Mobile Application Development
 * Lab BL11 / XB11
 * <p>
 * Dota Marketplace
 * <p>
 * Contributed by
 * 2201825535 - Kevin Dwitama Putra
 * 2201836330 - Natasha Anugrah
 */

public class TransAdapter extends RecyclerView.Adapter<TransAdapter.TransViewHolder> {

    final Context mCtx;
    final ArrayList<Transaction> arrTrans;

    final DBHelper database;

    public TransAdapter(Context mCtx, int userId) {
        this.mCtx = mCtx;

        database = new DBHelper(mCtx);
        DataHelper.arrTrans = database.fetchTransactions(userId);
        arrTrans = DataHelper.arrTrans;
    }

    @NonNull
    @Override
    public TransViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mCtx).inflate(R.layout.history_layout, parent, false);
        return new TransViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TransViewHolder holder, final int position) {
        Transaction trans = arrTrans.get(position);

        String transDate = trans.getTransactionDate();
        String itemName = database.getItem(trans.getItemId()).getName();
        int itemQty = trans.getItemQty();

        // untuk kalkulasi minus balance value
        float minBal;
        float itemPrice = database.getItem(trans.getItemId()).getPrice();

        minBal = itemPrice * itemQty;

        holder.lblTransDate.setText(transDate);
        holder.lblItemName.setText(itemName);
        holder.lblItemQty.setText("Quantity: " + itemQty);
        holder.lblMinBalance.setText("" + minBal);
    }

    @Override
    public int getItemCount() {
        return arrTrans.size();
    }

    static class TransViewHolder extends RecyclerView.ViewHolder {

        final TextView lblTransDate;
        final TextView lblItemName;
        final TextView lblItemQty;
        final TextView lblMinBalance;

        public TransViewHolder(@NonNull View transView) {
            super(transView);

            lblTransDate = transView.findViewById(R.id.lblTransDate);
            lblItemName = transView.findViewById(R.id.lblItemName);
            lblItemQty = transView.findViewById(R.id.lblItemQty);
            lblMinBalance = transView.findViewById(R.id.lblMinBalance);
        }
    }

}

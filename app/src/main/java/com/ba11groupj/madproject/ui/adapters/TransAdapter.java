package com.ba11groupj.madproject.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ba11groupj.madproject.R;
import com.ba11groupj.madproject.helpers.DBHelper;
import com.ba11groupj.madproject.models.Transaction;
import com.ba11groupj.madproject.models.User;

import java.util.ArrayList;

public class TransAdapter extends RecyclerView.Adapter<TransViewHolder> {

    Context mCtx;
    ArrayList<Transaction> arrTrans;
    User user;

    DBHelper database;

    public TransAdapter(Context mCtx, User user) {
        this.mCtx = mCtx;
        database = new DBHelper(mCtx);
        arrTrans = database.fetchTransactions();
        this.user = user;
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
        Log.d("kontol", transDate + itemName + itemQty + minBal);

        holder.lblTransDate.setText(transDate);
        holder.lblItemName.setText(itemName);
        holder.lblItemQty.setText("Quantity: " + itemQty);
        holder.lblMinBalance.setText("" + minBal);
    }

    @Override
    public int getItemCount() {
        return arrTrans.size();
    }
}

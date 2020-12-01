package com.ba11groupj.madproject.ui.adapters;

import android.content.Context;
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

public class TransAdapter extends RecyclerView.Adapter<ViewHolder> {

    Context mCtx;
    ArrayList<Transaction> arrTrans;
    User user;

    DBHelper database;

    public TransAdapter(Context mCtx, User user) {
        this.mCtx = mCtx;
        this.arrTrans = database.fetchTransactions();
        this.user = user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mCtx).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Transaction trans = arrTrans.get(position);
        int itemPrice = database.getItem(trans.getItemId()).getPrice();
        int minusBal = itemPrice * trans.getItemQty();

        holder.fldItemName.setText(database.getItem(trans.getItemId()).getName());
        holder.fldItemPrice.setText("Rp " + itemPrice);
        holder.fldItemStock.setText("Total Cost: Rp -" + minusBal);
        holder.btnBuy.setText("Clear Entry");

        holder.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrTrans.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrTrans.size();
    }
}

package com.github.kevindwitama.dotamarketplace.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.kevindwitama.dotamarketplace.R;
import com.github.kevindwitama.dotamarketplace.helpers.DBHelper;
import com.github.kevindwitama.dotamarketplace.helpers.DataHelper;
import com.github.kevindwitama.dotamarketplace.ui.adapters.TransAdapter;

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

public class HistoryActivity extends AppCompatActivity {

    RecyclerView rvTrans;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;

    Button btnClear;

    int userId;

    Bundle bundle;

    final DBHelper database = new DBHelper(this);

    void initData() {
        bundle = getIntent().getExtras();

        userId = bundle.getInt("userId");
        database.getUser(userId);
    }

    void init() {
        rvTrans = findViewById(R.id.rv_trans);
        myAdapter = new TransAdapter(this, userId);
        layoutManager = new LinearLayoutManager(this);

        rvTrans.setLayoutManager(layoutManager);
        rvTrans.setAdapter(myAdapter);

        btnClear = findViewById(R.id.btnClearHistory);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initData();
        init();

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.clearTransactionHistory(userId);
                DataHelper.arrTrans.clear();
                myAdapter.notifyDataSetChanged();
            }
        });
    }
}
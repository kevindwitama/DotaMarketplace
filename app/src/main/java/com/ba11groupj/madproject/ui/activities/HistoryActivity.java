package com.ba11groupj.madproject.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ba11groupj.madproject.helpers.DBHelper;
import com.ba11groupj.madproject.R;
import com.ba11groupj.madproject.ui.adapters.TransAdapter;
import com.ba11groupj.madproject.models.User;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView rvTrans;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;

    Button btnClear;

    User user;
    int userId;

    Bundle bundle;

    DBHelper database;

    void initData() {
        bundle = getIntent().getExtras();

        userId = bundle.getInt("userId");
        database.getUser(userId);
    }

    void init() {
        rvTrans = findViewById(R.id.rv_item);
        myAdapter = new TransAdapter(this, user);
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
                database.clearTransactionHistory();
                myAdapter.notifyDataSetChanged();
            }
        });
    }
}
package com.ba11groupj.madproject.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ba11groupj.madproject.R;
import com.ba11groupj.madproject.helpers.DBHelper;
import com.ba11groupj.madproject.models.User;
import com.ba11groupj.madproject.ui.adapters.ItemAdapter;

public class MainFormActivity extends AppCompatActivity {

    TextView txtUsername, txtBalance;
    RecyclerView rvItem;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;

    User user;
    int userId;

    Bundle bundle;

    DBHelper database;

    void initData() {
        bundle = getIntent().getExtras();

        database = new DBHelper(this);
        userId = bundle.getInt("userId");
        user = database.getUser(userId);
    }

    void init() {
        rvItem = findViewById(R.id.rv_trans);
        myAdapter = new ItemAdapter(this, user);
        layoutManager = new LinearLayoutManager(this);

        rvItem.setLayoutManager(layoutManager);
        rvItem.setAdapter(myAdapter);

        txtUsername = findViewById(R.id.txtViewUsername);
        txtBalance = findViewById(R.id.txtViewBalance);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_form);

        initData();
        init();

        txtUsername.setText(user.getUsername());
        txtBalance.setText("Balance: Rp " + user.getBalance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.mainformmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_top_up) {
            Intent intent = new Intent(this, TopUpActivity.class);
            Bundle bundle = new Bundle();

            bundle.putInt("userId", userId);
            intent.putExtras(bundle);

            startActivity(intent);
        } else if (item.getItemId() == R.id.menu_history) {
            Intent intent = new Intent(this, HistoryActivity.class);
            Bundle bundle = new Bundle();

            bundle.putInt("userId", userId);
            intent.putExtras(bundle);

            startActivity(intent);
        } else if (item.getItemId() == R.id.menu_log_out) {
            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
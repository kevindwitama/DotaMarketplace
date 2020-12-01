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

import com.ba11groupj.madproject.helpers.DataHelper;
import com.ba11groupj.madproject.models.Item;
import com.ba11groupj.madproject.ui.adapters.ItemAdapter;
import com.ba11groupj.madproject.R;
import com.ba11groupj.madproject.models.User;

public class MainFormActivity extends AppCompatActivity {

    TextView txtUsername, txtBalance;
    RecyclerView rvItem;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;

    User user;
    String userId;

    Bundle bundle;

    void initData() {
        if (DataHelper.arrItem.isEmpty()) {
            DataHelper.arrItem.add(new Item("I0001", "Item 01", 10000, 10, 0, 0));
            DataHelper.arrItem.add(new Item("I0002", "Item 02", 20000, 20, 0, 0));
            DataHelper.arrItem.add(new Item("I0003", "Item 03", 30000, 30, 0, 0));
            DataHelper.arrItem.add(new Item("I0004", "Item 04", 40000, 40, 0, 0));
            DataHelper.arrItem.add(new Item("I0005", "Item 05", 50000, 50, 0, 0));
        }

        bundle = getIntent().getExtras();

        userId = bundle.getString("userId");
        user = DataHelper.getUser(userId);
    }

    void init() {
        rvItem = findViewById(R.id.rv_item);
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

            bundle.putString("userId", userId);
            intent.putExtras(bundle);

            startActivity(intent);
        } else if (item.getItemId() == R.id.menu_history) {
            Intent intent = new Intent(this, HistoryActivity.class);
            Bundle bundle = new Bundle();

            bundle.putString("userId", userId);
            intent.putExtras(bundle);

            startActivity(intent);
        } else if (item.getItemId() == R.id.menu_log_out) {
            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
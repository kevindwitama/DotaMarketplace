package com.github.kevindwitama.dotamarketplace.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.kevindwitama.dotamarketplace.R;
import com.github.kevindwitama.dotamarketplace.helpers.DBHelper;
import com.github.kevindwitama.dotamarketplace.models.User;

/**
 * Final Project ISYS6203 Mobile Application Development
 * Lab BL11 / XB11
 *
 * Dota Marketplace
 *
 * Contributed by
 * 2201825535 - Kevin Dwitama Putra
 * 2201836330 - Natasha Anugrah
 */

public class TopUpActivity extends AppCompatActivity {

    TextView txtBalance;
    EditText fldBalanceAmt, fldPassword;
    Button btnAddBalance;

    int userId;

    User user;

    Bundle bundle;

    final DBHelper database = new DBHelper(this);

    void initData() {
        bundle = getIntent().getExtras();

        userId = bundle.getInt("userId");
        user = database.getUser(userId);
    }

    void init() {
        txtBalance = findViewById(R.id.txtVwBalance);
        fldBalanceAmt = findViewById(R.id.txtAddBalance);
        fldPassword = findViewById(R.id.txtPassword);
        btnAddBalance = findViewById(R.id.btnAddBalance);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);

        initData();
        init();

        txtBalance.setText("Current Balance: Rp " + user.getBalance());

        // fungsi utk add balance
        btnAddBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strBalAmt = fldBalanceAmt.getText().toString();
                String strPassword = fldPassword.getText().toString();

                if (!strBalAmt.isEmpty() || !strPassword.isEmpty()) {
                    long balanceAmt = Long.parseLong(strBalAmt);

                    if (balanceAmt < 50000) {
                        Toast.makeText(TopUpActivity.this, "Balance to be added must be more than 50k!", Toast.LENGTH_SHORT).show();
                    } else if (!strPassword.equals(database.getUser(userId).getPassword())) {
                        Toast.makeText(TopUpActivity.this, "Password does not match!", Toast.LENGTH_LONG).show();
                    } else {

                        // update data user di database
                        database.updateUserBalance(user, user.getBalance() + balanceAmt);

                        Toast.makeText(TopUpActivity.this, "Top Up success!", Toast.LENGTH_LONG).show();

                        // kirim user id balik ke main form
                        Intent intent = new Intent(TopUpActivity.this, MainFormActivity.class);
                        Bundle bundle = new Bundle();

                        bundle.putInt("userId", userId);
                        intent.putExtras(bundle);

                        startActivity(intent);
                    }
                } else if (strBalAmt.isEmpty() || strPassword.isEmpty()) {
                    Toast.makeText(TopUpActivity.this, "All fields must be filled in!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
package com.ba11groupj.madproject.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ba11groupj.madproject.helpers.DBHelper;
import com.ba11groupj.madproject.models.Item;
import com.ba11groupj.madproject.R;
import com.ba11groupj.madproject.models.Transaction;
import com.ba11groupj.madproject.models.User;

import java.util.Date;

public class BuyItemActivity extends AppCompatActivity  {

    TextView txtVwItemName, txtVwItemPrice, txtVwItemStock;
    // txtVwCurrentBal;
    EditText fldQty;
    Button btnSellerLoc, btnCheckout;

    User user;
    Item item;

    int itemId;
    String itemName;
    int itemPrice;
    int itemStock;
    int userId;
    float userBalance;
    int subtotal;

    DBHelper database;

    void initData() {
        Bundle bundle = getIntent().getExtras();
        itemId = bundle.getInt("itemId");
        userId = bundle.getInt("userId");

        item = getItemData(itemId);
        user = getUserData(userId);

        itemName = item.getName();
        itemPrice = item.getPrice();
        itemStock = item.getStock();
        userBalance = user.getBalance();
        subtotal = 0;
    }

    void init() {
        txtVwItemName = findViewById(R.id.txtVwItemName);
        txtVwItemPrice = findViewById(R.id.txtVwItemPrice);
        txtVwItemStock = findViewById(R.id.txtVwItemStock);
//        txtVwCurrentBal = findViewById(R.id.txtVwCurrentBalance);

        fldQty = findViewById(R.id.txtQty);

        btnSellerLoc = findViewById(R.id.btnShowSellerLoc);
        btnCheckout = findViewById(R.id.btnCheckout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_item);

        initData();
        init();

        txtVwItemName.setText(itemName);
        txtVwItemPrice.setText("Rp " + itemPrice);
        txtVwItemStock.setText("Stock: " + itemStock);
//        txtVwCurrentBal.setText("Current Balance: Rp " + userBalance);

        btnSellerLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyItemActivity.this, SellerLocationActivity.class);
                startActivity(intent);
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strItemQty = fldQty.getText().toString().trim();

                if(!chkNumeric(strItemQty)) {
                    Toast.makeText(BuyItemActivity.this, "Quantity must be numeric!", Toast.LENGTH_SHORT).show();
                }else if(!strItemQty.isEmpty()) {
                    int itemQty = Integer.parseInt(strItemQty);

                    if (itemQty > itemStock) {
                        Toast.makeText(BuyItemActivity.this, "Quantity exceeds stock!", Toast.LENGTH_SHORT).show();
                    } else if (userBalance < (itemPrice * itemQty)) {
                        Toast.makeText(BuyItemActivity.this, "Not enough funds in your balance!", Toast.LENGTH_LONG).show();
                    } else {
                        database.insertNewTransaction(userId, itemId, itemQty, new Date());

                        item.setStock(itemStock - itemQty);
                        user.setBalance(userBalance - (itemPrice * itemQty));

                        Toast.makeText(BuyItemActivity.this, "Transaction success!", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(BuyItemActivity.this, MainFormActivity.class);
                        Bundle bundle = new Bundle();

                        bundle.putInt("userId", userId);
                        intent.putExtras(bundle);

                        startActivity(intent);
                    }
                } else if (strItemQty.isEmpty()) {
                    Toast.makeText(BuyItemActivity.this, "Quantity must be filled in!", Toast.LENGTH_SHORT).show();
                }
            }

            // validasi jika quantity numeric
            private boolean chkNumeric(String strItemQty) {
                boolean containsUpper = false;
                boolean containsSpecial = false;

                for (char c : strItemQty.toCharArray()) {
                    if (Character.isLetter(c)) {
                        containsUpper = true;
                    } else if (!Character.isLetterOrDigit(c)) {
                        containsSpecial = true;
                    }
                }

                if (containsUpper || containsSpecial) {
                    return false;
                } else {
                    return true;
                }
            }
        });
    }

    // mengambil object item
    Item getItemData(int itemId) {
        for (Item i : database.fetchItems()) {
            if (i.getId() == itemId) {
                return i;
            }
        }
        return null;
    }

    // mengambil object user
    User getUserData(int userId) {
        for (User u : database.fetchUsers()) {
            if (u.getUserId() == userId) {
                return u;
            }
        }
        return null;
    }
}

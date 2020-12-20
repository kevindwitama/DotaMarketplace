package com.github.kevindwitama.dotamarketplace.ui.activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.kevindwitama.dotamarketplace.R;
import com.github.kevindwitama.dotamarketplace.helpers.DBHelper;
import com.github.kevindwitama.dotamarketplace.models.Item;
import com.github.kevindwitama.dotamarketplace.models.User;

import java.text.SimpleDateFormat;
import java.util.Date;

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

public class BuyItemActivity extends AppCompatActivity {

    TextView txtVwItemName, txtVwItemPrice, txtVwItemStock;
    ImageView imgProduct;
    EditText fldQty;
    Button btnSellerLoc, btnCheckout;

    User user;
    Item item;

    int itemId;
    int imgId;
    String itemName;
    int itemPrice;
    int itemStock;
    int userId;
    float userBalance;

    IntentFilter intentFilter;

    final DBHelper database = new DBHelper(this);

    // buat receive sms
    private final BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            SmsMessage[] smsMessages;

            if (!bundle.isEmpty()) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                smsMessages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    smsMessages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    String msg = smsMessages[i].getMessageBody();
                    String from = smsMessages[i].getOriginatingAddress();

                    Toast.makeText(BuyItemActivity.this, "Message from " + from + ": " + msg, Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    void initData() {
        Bundle bundle = getIntent().getExtras();
        itemId = bundle.getInt("itemId");
        userId = bundle.getInt("userId");
        imgId = bundle.getInt("imgId");

        item = getItemData(itemId);
        user = getUserData(userId);

        itemName = item.getName();
        itemPrice = item.getPrice();
        itemStock = item.getStock();
        userBalance = user.getBalance();
    }

    void init() {
        txtVwItemName = findViewById(R.id.txtVwItemName);
        txtVwItemPrice = findViewById(R.id.txtVwItemPrice);
        txtVwItemStock = findViewById(R.id.txtVwItemStock);
        imgProduct = findViewById(R.id.imageproduct);

        fldQty = findViewById(R.id.txtQty);

        btnSellerLoc = findViewById(R.id.btnShowSellerLoc);
        btnCheckout = findViewById(R.id.btnCheckout);

        // check utk sms permission
        if (ContextCompat.checkSelfPermission(BuyItemActivity.this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED) {
            intentFilter = new IntentFilter();
            intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
            this.registerReceiver(br, intentFilter);
        }
    }

    // mengambil data item
    Item getItemData(int itemId) {
        for (Item i : database.fetchItems()) {
            if (i.getId() == itemId) {
                return i;
            }
        }
        return null;
    }

    // mengambil data user
    User getUserData(int userId) {
        for (User u : database.fetchUsers()) {
            if (u.getId() == userId) {
                return u;
            }
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_item);

        requestSmsPermission();
        initData();
        init();

        txtVwItemName.setText(itemName);
        txtVwItemPrice.setText("Rp " + itemPrice);
        txtVwItemStock.setText("Stock: " + itemStock);
        imgProduct.setImageResource(imgId);

        // buka menu seller location
        btnSellerLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyItemActivity.this, SellerLocationActivity.class);
                Bundle bundle = new Bundle();

                double latitude = item.getLatitude();
                double longitude = item.getLongitude();

                // kirim data lat n long ke menu seller loc
                bundle.putDouble("lat", latitude);
                bundle.putDouble("long", longitude);
                intent.putExtras(bundle);

                String toast = "Lat: " + latitude + ", Long: " + longitude;
                Toast.makeText(BuyItemActivity.this, toast, Toast.LENGTH_LONG).show();

                startActivity(intent);
            }
        });

        // fungsi utk button checkout
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strItemQty = fldQty.getText().toString().trim();

                if (!chkNumeric(strItemQty)) {
                    Toast.makeText(BuyItemActivity.this, "Quantity must be numeric!", Toast.LENGTH_SHORT).show();
                } else if (!strItemQty.isEmpty()) {
                    int itemQty = Integer.parseInt(strItemQty);

                    if (itemQty > itemStock) {
                        Toast.makeText(BuyItemActivity.this, "Quantity exceeds stock!", Toast.LENGTH_SHORT).show();
                    } else if (userBalance < (itemPrice * itemQty)) {
                        Toast.makeText(BuyItemActivity.this, "Not enough funds in your balance!", Toast.LENGTH_LONG).show();
                    } else {
                        // siapin tanggal utk dimasukkin ke transaction
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String date = dateFormat.format(new Date());
                        database.insertNewTransaction(userId, itemId, itemQty, date);

                        // update stock di database
                        database.updateItemStock(item, itemStock - itemQty);
                        database.updateUserBalance(user, userBalance - (itemPrice * itemQty));

                        String smsMsg = "Transaction success!";

                        // check utk sms permission
                        if (ContextCompat.checkSelfPermission(BuyItemActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(BuyItemActivity.this, "Please wait for SMS confirmation...", Toast.LENGTH_LONG).show();
                            SmsManager.getDefault().sendTextMessage("5554", null, smsMsg, null, null);
                        } else {
                            Toast.makeText(BuyItemActivity.this, smsMsg, Toast.LENGTH_SHORT).show();
                        }

                        // balik ke main form abis checkout
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

                return !containsUpper && !containsSpecial;
            }
        });
    }

    // request permission buat sms
    private void requestSmsPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS}, 0);
    }
}

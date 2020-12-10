package com.ba11groupj.madproject.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.ba11groupj.madproject.models.Item;
import com.ba11groupj.madproject.models.Transaction;
import com.ba11groupj.madproject.models.User;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "DotaMarketplaceDB.db";
    private static final int DB_VERSION = 1;

    // Users Table
    private static final String TABLE_USERS = "users";
    private static final String USER_ID = "userId";
    private static final String USER_FULLNAME = "fullname";
    private static final String USERNAME = "username";
    private static final String USER_PASS = "password";
    public static final String USER_PHONE = "phone";
    public static final String USER_GENDER = "gender";
    public static final String USER_BALANCE = "balance";

    // Items Table
    private static final String TABLE_ITEMS = "items";
    private static final String ITEM_ID = "itemId";
    private static final String ITEM_NAME = "name";
    private static final String ITEM_PRICE = "price";
    private static final String ITEM_STOCK = "stock";
    private static final String ITEM_LAT = "latitude";
    private static final String ITEM_LONG = "longitude";

    // Transactions Table
    private static final String TABLE_TRANSACTIONS = "transactions";
    private static final String TRANSACTION_ID = "transactionId";
    private static final String TRANSACTION_USER = "userId";
    private static final String TRANSACTION_ITEM = "itemId";
    private static final String TRANSACTION_QTY = "quantity";
    private static final String TRANSACTION_DATE = "date";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query;

        // create users table
        query = "CREATE TABLE " + TABLE_USERS + "(" +
                USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                USER_FULLNAME + " TEXT," +
                USERNAME + " TEXT," +
                USER_PASS + " TEXT," +
                USER_PHONE + " TEXT," +
                USER_GENDER + " BOOLEAN," +
                USER_BALANCE + " FLOAT" +
                ");";
        db.execSQL(query);

        // create items table
        query = "CREATE TABLE " + TABLE_ITEMS + "(" +
                ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ITEM_NAME + " TEXT," +
                ITEM_PRICE + " INTEGER," +
                ITEM_STOCK + " INTEGER," +
                ITEM_LAT + " FLOAT," +
                ITEM_LONG + " FLOAT" +
                ");";
        db.execSQL(query);

        // create transactions table
        query = "CREATE TABLE " + TABLE_TRANSACTIONS + "(" +
                TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TRANSACTION_USER + " INTEGER," +
                TRANSACTION_ITEM + " INTEGER," +
                TRANSACTION_QTY + " INTEGER," +
                TRANSACTION_DATE + " TEXT," +
                "FOREIGN KEY(" + TRANSACTION_USER + ") REFERENCES " + TABLE_USERS + "(" + USER_ID + ")" + "," +
                "FOREIGN KEY(" + TRANSACTION_ITEM + ") REFERENCES " + TABLE_ITEMS + "(" + ITEM_ID + ")" +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query;
        query = "DROP TABLE IF EXISTS "+TABLE_USERS;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS "+ TABLE_ITEMS;
        db.execSQL(query);
        onCreate(db);
    }

    public boolean insertNewUser(String username, String fullName, String password, String phoneNum, boolean gender, float balance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USERNAME, username);
        cv.put(USER_FULLNAME, fullName);
        cv.put(USER_PASS, password);
        cv.put(USER_PHONE, phoneNum);
        cv.put(USER_GENDER, gender);
        cv.put(USER_BALANCE, balance);

        long result = db.insert(TABLE_USERS, null, cv);
        return result != -1;
    }

    public boolean insertNewItem(String name, int price, int stock, float latitude, float longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ITEM_NAME, name);
        cv.put(ITEM_PRICE, price);
        cv.put(ITEM_STOCK, stock);
        cv.put(ITEM_LAT, latitude);
        cv.put(ITEM_LONG, longitude);

        long result = db.insert(TABLE_ITEMS, null, cv);
        return result != -1;
    }

    public boolean insertNewTransaction(int userId, int itemId, int qty, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TRANSACTION_USER, userId);
        cv.put(TRANSACTION_ITEM, itemId);
        cv.put(TRANSACTION_QTY, qty);
        cv.put(TRANSACTION_DATE, String.valueOf(date));

        long result = db.insert(TABLE_TRANSACTIONS, null, cv);
        return result != -1;
    }

    public ArrayList<User> fetchUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
        ArrayList<User> users = new ArrayList<>();
        while (cursor.moveToNext()) {
            User user;
            int userId = cursor.getInt(cursor.getColumnIndexOrThrow(USER_ID));
            String username = cursor.getString(cursor.getColumnIndexOrThrow(USERNAME));
            String fullName = cursor.getString(cursor.getColumnIndexOrThrow(USER_FULLNAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(USER_PASS));
            String phoneNum = cursor.getString(cursor.getColumnIndexOrThrow(USER_PHONE));
            boolean gender = cursor.getInt(cursor.getColumnIndexOrThrow(USER_GENDER)) != 0;
            float balance = cursor.getFloat(cursor.getColumnIndexOrThrow(USER_BALANCE));
            user = new User(userId, fullName, username, password, phoneNum, gender, balance);
            users.add(user);
        }
        cursor.close();

        return users;
    }

    public ArrayList<Item> fetchItems(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ITEMS, null);
        ArrayList<Item> items = new ArrayList<>();
        while(cursor.moveToNext()) {
            Item item;
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ITEM_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_NAME));
            int price = cursor.getInt(cursor.getColumnIndexOrThrow(ITEM_PRICE));
            int stock = cursor.getInt(cursor.getColumnIndexOrThrow(ITEM_STOCK));
            int latitude = cursor.getInt(cursor.getColumnIndexOrThrow(ITEM_LAT));
            int longitude = cursor.getInt(cursor.getColumnIndexOrThrow(ITEM_LONG));
            item = new Item(id, name, price, stock, latitude, longitude);
            items.add(item);
        }
        cursor.close();

        return items;
    }

    public ArrayList<Transaction> fetchTransactions() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TRANSACTIONS, null);
        ArrayList<Transaction> transactions = new ArrayList<>();
        while (cursor.moveToNext()) {
            Transaction t;
            int transId = cursor.getInt(cursor.getColumnIndexOrThrow(TRANSACTION_ID));
            int userId = cursor.getInt(cursor.getColumnIndexOrThrow(TRANSACTION_USER));
            int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(TRANSACTION_ITEM));
            int qty = cursor.getInt(cursor.getColumnIndexOrThrow(TRANSACTION_QTY));
            String transDate = cursor.getString(cursor.getColumnIndexOrThrow(TRANSACTION_DATE));
            t = new Transaction(transId, userId, itemId, qty, transDate);
            transactions.add(t);
        }
        cursor.close();

        return transactions;
    }

    public User getUser(int userId) {
        for (User u : this.fetchUsers()) {
            if (u.getId() == userId) {
                return u;
            }
        }
        return null;
    }

    public Item getItem(int itemId) {
        for (Item i : this.fetchItems()) {
            if (i.getId() == itemId) {
                return i;
            }
        }
        return null;
    }

    public void clearTransactionHistory() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_TRANSACTIONS);
    }

    public void updateItemStock(Item item, int newStock) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String itemId = String.valueOf(item.getId());

        cv.put(ITEM_STOCK, newStock);
        db.update(TABLE_ITEMS, cv, ITEM_ID + " = ?", new String[]{itemId});
    }

    public void updateUserBalance(User user, float newBalance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String userId = String.valueOf(user.getId());

        cv.put(USER_BALANCE, newBalance);
        db.update(TABLE_USERS, cv, USER_ID + " = ?", new String[]{userId});
    }
}

package com.ba11groupj.madproject.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.ba11groupj.madproject.models.Item;
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
                USER_PASS+" TEXT,"+
                USER_PHONE+" TEXT,"+
                USER_GENDER+" BOOLEAN,"+
                USER_BALANCE+" REAL"+
                ");";
        db.execSQL(query);

        // create items table
        query = "CREATE TABLE " + TABLE_ITEMS + "(" +
                ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ITEM_NAME + " TEXT," +
                ITEM_PRICE + " INT," +
                ITEM_STOCK + " INT," +
                ITEM_LAT + " REAL," +
                ITEM_LONG + " REAL" +
                ");";
        db.execSQL(query);

        // create transactions table
        query = "CREATE TABLE " + TABLE_TRANSACTIONS + "(" +
                TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TRANSACTION_USER + " INT," +
                TRANSACTION_ITEM + " INT," +
                TRANSACTION_DATE + " INT," +
                "FOREIGN KEY("+TRANSACTION_USER +") REFERENCES " + TABLE_USERS + "("+USER_ID+")" + "," +
                "FOREIGN KEY("+TRANSACTION_ITEM +") REFERENCES " + TABLE_ITEMS + "("+ITEM_ID+")" +
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

    public boolean insertNewUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USERNAME, username);
        cv.put(USER_PASS, password);

        long result = db.insert(TABLE_USERS, null , cv);
        if (result == -1) {
            return false;
        }

        return true;
    }

    public boolean insertNewRoom(String num, int price, boolean booked) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ITEM_NAME, num);
        cv.put(ITEM_PRICE, price);
        cv.put(ITEM_STOCK, booked);

        long result = db.insert(TABLE_ITEMS, null , cv);
        if (result == -1) {
            return false;
        }

        return true;
    }

    public ArrayList<User> fetchUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users", null);
        ArrayList<User> users = new ArrayList<User>();
        while(cursor.moveToNext()) {
            User user;
            String email = cursor.getString(cursor.getColumnIndexOrThrow(USERNAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(USER_PASS));
            user = new User(email, password);
            users.add(user);
        }
        cursor.close();

        return users;
    }

    public ArrayList<Item> fetchItems(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM rooms", null);
        ArrayList<Item> items = new ArrayList<Item>();
        while(cursor.moveToNext()) {
            Item item;
            String num = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_NAME));
            int price = cursor.getInt(cursor.getColumnIndexOrThrow(ITEM_PRICE));
            boolean booked = cursor.getInt(cursor.getColumnIndexOrThrow(ITEM_STOCK)) != 0;
            item = new Item(num, price, booked);
            items.add(item);
        }
        cursor.close();

        return items;
    }

//    public void updateRoomStatus(Room room) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        String roomNum = room.getNum();
//        int booked;
//
//        if (room.isBooked()) {
//            booked = 1;
//        } else {
//            booked = 0;
//        }
//
//        cv.put(ROOM_BOOK, booked);
//        db.update(TABLE_ROOMS, cv, ROOM_NUM + " = ?", new String[]{roomNum});
//    }
}

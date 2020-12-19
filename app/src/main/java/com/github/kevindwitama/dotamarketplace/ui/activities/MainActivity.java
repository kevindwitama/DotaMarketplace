package com.github.kevindwitama.dotamarketplace.ui.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTabHost;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.kevindwitama.dotamarketplace.R;
import com.github.kevindwitama.dotamarketplace.helpers.DBHelper;
import com.github.kevindwitama.dotamarketplace.ui.fragments.LoginFragment;
import com.github.kevindwitama.dotamarketplace.ui.fragments.RegisterFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class MainActivity extends AppCompatActivity {

    private FragmentTabHost tabHost;

    public FragmentTabHost getTabHost() {
        return tabHost;
    }

    DBHelper database;

    void initData() {
        database = new DBHelper(this);
        if (database.fetchUsers().isEmpty()) {
            database.insertNewUser("admin", "admin", "123", "+62123", false, 1000000);
        }
        if (database.fetchItems().isEmpty()) {
            fetchJsonData();
        }
    }

    void init() {
        tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        tabHost.addTab(tabHost.newTabSpec("Login").setIndicator("Login", null), LoginFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("Register").setIndicator("Register", null), RegisterFragment.class, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        init();
    }

    private void fetchJsonData() {
        String url = "https://raw.githubusercontent.com/acad600/JSONRepository/master/ISYS6203/O212-ISYS6203-RM01-00-DotaMarketplace.json";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        String name = obj.getString("name");
                        int price = obj.getInt("price");
                        int stock = obj.getInt("stock");
                        double latitude = obj.getDouble("latitude");
                        double longitude = obj.getDouble("longitude");

                        database.insertNewItem(name, price, stock, latitude, longitude);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MyActivity", "onErrorResponse" + error.toString());
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

}
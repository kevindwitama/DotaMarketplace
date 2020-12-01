package com.ba11groupj.madproject.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTabHost;

import com.ba11groupj.madproject.helpers.DataHelper;
import com.ba11groupj.madproject.ui.fragments.LoginFragment;
import com.ba11groupj.madproject.R;
import com.ba11groupj.madproject.ui.fragments.RegisterFragment;
import com.ba11groupj.madproject.models.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static FragmentTabHost tabHost;

    public static FragmentTabHost getTabHost() {
        return tabHost;
    }

    ArrayList<User> arrUser;

    void initData() {
        arrUser = DataHelper.arrUser;
        if (arrUser.isEmpty()) {
            arrUser.add(new User("admin", "admin", "123", "+62", true, 1000000));
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
}
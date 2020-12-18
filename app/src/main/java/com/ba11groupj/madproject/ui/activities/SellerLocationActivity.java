package com.ba11groupj.madproject.ui.activities;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.ba11groupj.madproject.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SellerLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap sellerMaps;
    float latitude, longitude;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_location);

        initData();
        init();

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        sellerMaps = googleMap;
        LatLng sellerLoc = new LatLng(latitude, longitude);
        sellerMaps.addMarker(new MarkerOptions().position(sellerLoc).title("Seller Location"));
        sellerMaps.moveCamera(CameraUpdateFactory.newLatLng(sellerLoc));
    }

    public void initData() {
        Bundle bundle = getIntent().getExtras();
        latitude = bundle.getFloat("lat");
        longitude = bundle.getFloat("long");
    }

    public void init() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.googleMaps);
    }
}
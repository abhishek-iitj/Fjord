package com.example.abhishek.fjord;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String airQualityType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Bundle extras = getIntent().getExtras();
        String latitude, longitude;
        latitude = extras.getString("lat");
        longitude = extras.getString("long");
        addLocationMarker(latitude, longitude);
        airQualityType = "usepa-aqi";
        addAirQualityIndex(latitude, longitude);
    }

    private void addAirQualityIndex(String latitude, String longitude) {
        TileProvider tileProvider = new UrlTileProvider(256, 256) {
            @Override
            public synchronized URL getTileUrl(int x, int y, int zoom) {
                String tileUrl = "https://tiles.waqi.info/tiles/" + airQualityType + "/" + zoom +  "/"  +  x  +  "/"  +  y  +  ".png?token=427d4e86c04f476ae1af11fac004829fd53e8d20";
                URL url = null;
                try {
                    url = new URL(tileUrl);
                } catch (MalformedURLException e) {
                    throw new AssertionError(e);
                }
                return url;
            }
        };
        mMap.addTileOverlay(new TileOverlayOptions().tileProvider(tileProvider));
    }

    private void addLocationMarker(String latitude, String longitude) {
        LatLng currentLocation = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        mMap.addMarker(new MarkerOptions().position(currentLocation).title("Marker at Current Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
    }
}
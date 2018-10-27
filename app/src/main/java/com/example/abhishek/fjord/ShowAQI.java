package com.example.abhishek.fjord;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ShowAQI extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_aqi);
        Bundle extras = getIntent().getExtras();
        String latitude, longitude;
        latitude = extras.getString("lat");
        longitude = extras.getString("long");
        Log.d("Latitude", latitude);
        Log.d("Longitude", longitude);
    }
}

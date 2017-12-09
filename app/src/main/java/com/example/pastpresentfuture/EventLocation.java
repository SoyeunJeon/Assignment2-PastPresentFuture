package com.example.pastpresentfuture;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class EventLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_location);
        TextView searchText = (TextView) findViewById(R.id.searchText);

        // Get a location detail from the previous event
        Bundle bundle = getIntent().getExtras();
        String location = bundle.getString("location");
        searchText.setText(location);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    double latitude, longitude;

    public void onSearch(View view) {
        TextView searchText = (TextView) findViewById(R.id.searchText);
        String location = searchText.getText().toString();
        List<Address> addressList = null;

        // From the name of a location, find a latitude and longitude by using Geocoder
        // and display that location on map
        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            latitude = address.getLatitude();
            longitude = address.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(latLng).title("Where you choose"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        }
    }
}

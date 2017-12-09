package com.example.pastpresentfuture;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HistoryMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Initialize database
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference().child("history");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // From the database, display all the location on map that is under "history" storage
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    HistoryStore historyStore = data.getValue(HistoryStore.class);
                    double latitude = historyStore.getLatitude();
                    double longitude = historyStore.getLongitude();
                    String title = historyStore.getTitle().toString();
                    LatLng latLng = new LatLng(latitude, longitude);
                    mMap.addMarker(new MarkerOptions().position(latLng).title(title));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("history map tag", "now running onStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("history map tag", "now running onResume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("history map tag", "now running onPause");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("history map tag", "now running onRestart");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("history map tag", "now running onStop");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("history map tag", "now running onDestroy");
    }
}

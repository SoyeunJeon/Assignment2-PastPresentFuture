package com.example.pastpresentfuture;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MyHistory extends AppCompatActivity {

    TextView textView1;
    EditText editText;
    Button button;
    String address;
    String title;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_history);

        // Get current latitude and longitude using gps object
        GPS gps = new GPS(getApplicationContext());
        Location l = gps.getLocation();
        final double lat = l.getLatitude();
        final double lon = l.getLongitude();

        // Get a string of address from the known latitude and longitude
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addressList = null;
        try {
            addressList = geocoder.getFromLocation(lat, lon, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        address = addressList.get(0).getAddressLine(0);

        textView1 = (TextView) findViewById(R.id.textView1);
        textView1.setText(address);

        // Store the data from user in database
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference().child("history");

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = editText.getText().toString();
                HistoryStore historyStore = new HistoryStore(address, title, lat, lon);
                mDatabaseReference.push().setValue(historyStore);
                editText.setText("");
            }
        });
    }

    // Button
    public void seeAll(View view) {
        Intent intent = new Intent(this, HistoryMap.class);
        startActivity(intent);
    }

    // Logs
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("my history tag", "now running onStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("my history tag", "now running onResume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("my history tag", "now running onPause");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("my history tag", "now running onRestart");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("my history tag", "now running onStop");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("my history tag", "now running onDestroy");
    }
}

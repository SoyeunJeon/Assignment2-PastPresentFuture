package com.example.pastpresentfuture;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * Created by s3622567 on 3/12/2017.
 */

// Call GPS to get location of the user location

public class GPS implements LocationListener{

    Context context;
    public GPS(Context context) {this.context = context;}

    public Location getLocation() {
        // Get a permission to use of location service
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Permission not granted", Toast.LENGTH_SHORT).show();
            return null;
        }

        // Check is the GPS turned on on phone and get current location
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean GPSOn = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (GPSOn) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 10, this);
            Location l = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return l;
        } else {
            Toast.makeText(context, "Please enable GPS", Toast.LENGTH_LONG).show();
        }
        return null;
    }


    @Override
    public void onLocationChanged(Location location) {

    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
    @Override
    public void onProviderEnabled(String provider) {

    }
    @Override
    public void onProviderDisabled(String provider) {

    }
}

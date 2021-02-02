package com.example.mainthings.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.widget.Toast;

import com.example.mainthings.R;
import com.example.mainthings.callbacks.LocationPermissionCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;


public class LocationManager{

    private final FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private LatLng currentLocationLatLng ;
    private final Boolean removeUpdateLocation;
    private Activity activity ;
    private LocationPermissionCallback locationPermissionCallback;

    public LocationManager(Activity activity , Boolean removeUpdateLocation) {
        this.activity = activity;
        this.removeUpdateLocation = removeUpdateLocation;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
    }

    @SuppressLint("MissingPermission")
    public void locationRequest( LocationPermissionCallback locationPermissionCallback){

        this.locationPermissionCallback = locationPermissionCallback;
        buildLocationRequest();
        buildLocationCallBack();

        fusedLocationClient.requestLocationUpdates(locationRequest , locationCallback , Looper.myLooper());
    }

    public void buildLocationRequest(){
        locationRequest  = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(20);

    }

    public void buildLocationCallBack(){
        locationCallback = new LocationCallback(){

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                for (Location location : locationResult.getLocations()){

                    currentLocationLatLng = new LatLng(location.getLatitude() , location.getLongitude());

                    if (removeUpdateLocation)
                        fusedLocationClient.removeLocationUpdates(locationCallback);

                    if (locationPermissionCallback != null)
                        locationPermissionCallback.locationCallback(currentLocationLatLng , location.isFromMockProvider());
                }
            }
        };
    }

}

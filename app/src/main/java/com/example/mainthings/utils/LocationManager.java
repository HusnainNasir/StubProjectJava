package com.example.mainthings.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.widget.Toast;

import com.example.mainthings.R;
import com.example.mainthings.callbacks.PermissionCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import androidx.annotation.NonNull;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class LocationManager implements EasyPermissions.PermissionCallbacks {

    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private LatLng currentLocationLatLng ;
    private final Context context;
    private final Boolean removeUpdateLocation;

    public LocationManager(Context context , Boolean removeUpdateLocation) {
        this.context = context;
        this.removeUpdateLocation = removeUpdateLocation;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient((Activity) context);
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

//                    if (callback != null)
//                        callback.getLocationCallBack(currentLocationLatLng , location.isFromMockProvider());
                }
            }
        };
    }

    public void locationPermission(LocationCallback locationCallback){

        this.locationCallback = locationCallback;

        requestLocationPermission();
    }

    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(Constants.FINE_AND_COURSE_LOCATION_PERMISSION)
    public void requestLocationPermission(){
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE , Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(context, perms)) {
            // Already have permission, do the thing

            buildLocationRequest();
            buildLocationCallBack();
            fusedLocationClient.requestLocationUpdates(locationRequest , locationCallback , Looper.myLooper());

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(new PermissionRequest.Builder((Activity) context, Constants.FINE_AND_COURSE_LOCATION_PERMISSION, perms)
                    .setTheme(R.style.AlertDialogCustom)
                    .build());
        }

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

        if (requestCode == Constants.FINE_AND_COURSE_LOCATION_PERMISSION){
            buildLocationRequest();
            buildLocationCallBack();
            fusedLocationClient.requestLocationUpdates(locationRequest , locationCallback , Looper.myLooper());

        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

        Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        EasyPermissions.onRequestPermissionsResult(requestCode , permissions , grantResults , this);
    }


}

package com.example.mainthings.ui.main_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.mainthings.BaseActivity;
import com.example.mainthings.R;
import com.example.mainthings.utils.LocationManager;


public class MainActivity extends BaseActivity {

    LocationManager locationManager ;

    @Override
    protected void created(Bundle savedInstance) {

        locationManager = new LocationManager(this , true);


        locationPermission( isPermission -> {

            if ((Boolean) isPermission){

                locationManager.locationRequest( ((currentLatLng, fakeLocation) -> {
                    if (currentLatLng != null){
                        Toast.makeText(this, "Latitude" + currentLatLng.longitude + " Longitude " + currentLatLng.longitude, Toast.LENGTH_SHORT).show();
                    }
                }));
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected String getTag() {
        return MainActivity.class.getSimpleName();
    }
}
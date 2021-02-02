package com.example.mainthings;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.widget.Toast;

import com.example.mainthings.callbacks.LocationPermissionCallback;
import com.example.mainthings.callbacks.PermissionCallback;
import com.example.mainthings.utils.AlertDialogs;
import com.example.mainthings.utils.Constants;
import com.example.mainthings.utils.PreferenceHelper;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public abstract class BaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{


    private PreferenceHelper preferenceHelper;
    private Gson gson;
    private PermissionCallback permissionCallback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
            ButterKnife.bind(this);
            preferenceHelper = PreferenceHelper.preferenceInstance(getApplicationContext());
            gson = new Gson();
            created(savedInstanceState);
        }

    }



    public boolean locationEnabled() {

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gps_enabled || network_enabled;
    }


    public void locationPermission(PermissionCallback permissionCallback){

        this.permissionCallback = permissionCallback;

        if (locationEnabled()){
            requestLocationPermission();
        }else{
            AlertDialogs.showAlertDialog(this, getString(R.string.location_setting), getString(R.string.enableLocation), getString(R.string.setting), getString(R.string.cancel), false, action -> {
                if ((int) action == Constants.CLICK_POSITIVE) {
                    startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS) , AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE);
                }
            });
        }
    }


    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(Constants.FINE_AND_COURSE_LOCATION_PERMISSION)
    public void requestLocationPermission(){
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing

            permissionCallback.permissionCallback(true);

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(new PermissionRequest.Builder(this, Constants.FINE_AND_COURSE_LOCATION_PERMISSION, perms)
                    .setTheme(R.style.AlertDialogCustom)
                    .build());
        }

    }


    public void cameraPermission(PermissionCallback permissionCallback){

        this.permissionCallback = permissionCallback;

        requestCameraPermission();
    }

    @AfterPermissionGranted(Constants.CAMERA_AND_READ_AND_WRITE_PERMISSION)
    public void requestCameraPermission(){
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE , Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            permissionCallback.permissionCallback(true);
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(new PermissionRequest.Builder(this, Constants.CAMERA_AND_READ_AND_WRITE_PERMISSION, perms)
                    .setTheme(R.style.AlertDialogCustom)
                    .build());
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Some permissions have been granted
        if (requestCode == Constants.CAMERA_AND_READ_AND_WRITE_PERMISSION)
            permissionCallback.permissionCallback(true);
        else if (requestCode == Constants.FINE_AND_COURSE_LOCATION_PERMISSION)
            permissionCallback.permissionCallback(true);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Some permissions have been denied
        if (requestCode == Constants.CAMERA_AND_READ_AND_WRITE_PERMISSION)
            permissionCallback.permissionCallback(false);
        else if (requestCode == Constants.FINE_AND_COURSE_LOCATION_PERMISSION)
            permissionCallback.permissionCallback(false);

        if (EasyPermissions.somePermissionPermanentlyDenied(this, list)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
        }
    }

    public PreferenceHelper getPreferenceHelper() {
        return preferenceHelper;
    }

    public Gson getGSON() {
        return gson;
    }

    protected abstract int getLayoutId();

    protected abstract String getTag();

    protected abstract void created(Bundle savedInstance);

}

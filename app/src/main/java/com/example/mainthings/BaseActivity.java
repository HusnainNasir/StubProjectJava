package com.example.mainthings;

import android.Manifest;
import android.os.Bundle;

import com.example.mainthings.callbacks.PermissionCallback;
import com.example.mainthings.utils.Constants;
import com.example.mainthings.utils.PreferenceHelper;
import com.google.gson.Gson;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
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
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Some permissions have been denied
        if (requestCode == Constants.CAMERA_AND_READ_AND_WRITE_PERMISSION)
            permissionCallback.permissionCallback(false);
    }

    public PreferenceHelper getPreferenceHelper() {
        return preferenceHelper;
    }

    public Gson getGSON() {
        return gson;
    }

    protected abstract int getLayoutId();

    protected abstract void created(Bundle savedInstance);

}

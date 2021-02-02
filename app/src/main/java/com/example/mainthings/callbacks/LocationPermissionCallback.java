package com.example.mainthings.callbacks;

import com.google.android.gms.maps.model.LatLng;

public interface LocationPermissionCallback {
    void locationCallback(LatLng currentLatLng , boolean fakeLocation);
}

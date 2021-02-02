package com.example.mainthings.callbacks;

import com.google.android.gms.maps.model.LatLng;

interface LocationCallback {
    void locationCallback(LatLng currentLatLng , boolean fakeLocation);
}

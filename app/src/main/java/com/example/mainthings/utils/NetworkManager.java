package com.example.mainthings.utils;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Completable;

public class NetworkManager {

    private final NetworkRequest networkRequest ;
    private final ConnectivityManager connectivityManager ;
    private static NetworkManager networkManager ;

    public NetworkManager(Application application) {
        this.networkRequest = new NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build();
        this.connectivityManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkManager = this;
    }

    public static NetworkManager getNetworkManager(){
        return networkManager ;
    }

    public LiveData<Boolean> isNetworkState() {

        MutableLiveData<Boolean> isNetwork = new MutableLiveData<>();

        connectivityManager.registerNetworkCallback(networkRequest, new ConnectivityManager.NetworkCallback(){
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);

                isNetwork.postValue(true);
            }

            @Override
            public void onLosing(@NonNull Network network, int maxMsToLive) {
                super.onLosing(network, maxMsToLive);
                isNetwork.postValue(false);
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                isNetwork.postValue(false);
            }

            @Override
            public void onUnavailable() {
                super.onUnavailable();
                isNetwork.postValue(false);
            }

        });

        return isNetwork;
    }

    public boolean isConnected(){
        NetworkCapabilities capability = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        if (capability != null)
            return capability.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ;
        else
            return false;
    }

    public boolean isWorking(){

        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }

    }
}

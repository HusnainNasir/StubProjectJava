package com.example.mainthings;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

public class MyApplication extends MultiDexApplication {

    private static MyApplication instance;

    public MyApplication() {
        super();
    }

    public static MyApplication getInstance() { return instance==null?new MyApplication():instance; }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}

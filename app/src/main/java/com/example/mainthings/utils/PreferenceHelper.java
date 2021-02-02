package com.example.mainthings.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    private static PreferenceHelper preferenceInstance = null ;

    private final SharedPreferences app_prefs;
    private final SharedPreferences.Editor editor;

    private final String AUTH_TOKEN = "auth_token";

    public static PreferenceHelper preferenceInstance(Context context){

        if (preferenceInstance == null){
            preferenceInstance = new PreferenceHelper(context);
        }

        return preferenceInstance ;
    }

    private PreferenceHelper(Context context) {

        app_prefs = context.getSharedPreferences(Constants.DB_NAME, Context.MODE_PRIVATE);
        editor = app_prefs.edit();
        editor.apply();
    }

    public void setOnChange(SharedPreferences.OnSharedPreferenceChangeListener listener){
        app_prefs.registerOnSharedPreferenceChangeListener(listener);
    }


    // Auth Token Setter/Getter

    public void setAuthToken(String authToken) {
        editor.putString(AUTH_TOKEN, authToken);
        editor.apply();
    }

    public String getAuthToken() {
        return app_prefs.getString(AUTH_TOKEN, null);
    }
}

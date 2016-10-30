package com.example.nguyen.mobilehackthon.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by PMSANG on 10/29/2016.
 */
public class PreferenceHelper {
    Context mContext;
    SharedPreferences preferences;

    public PreferenceHelper(Context appContext) {
        mContext = appContext;
        preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public void putString(String key, String value) {

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return preferences.getString(key, "");
    }


    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

}

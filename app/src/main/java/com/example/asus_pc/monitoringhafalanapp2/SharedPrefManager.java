package com.example.asus_pc.monitoringhafalanapp2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by muhtar on 12/13/17.
 */

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "SharedPrefConfig";
    @SuppressLint("StaticFieldLeak")
    private static SharedPrefManager sharedPrefConfig;
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public SharedPrefManager(Context context) {
        this.context = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (sharedPrefConfig == null) {
            sharedPrefConfig = new SharedPrefManager(context);
        }

        return sharedPrefConfig;
    }

    public boolean setData(String strData) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("data", strData);
        editor.apply();
        return true;
    }

    public String getData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("data", "");
    }
}

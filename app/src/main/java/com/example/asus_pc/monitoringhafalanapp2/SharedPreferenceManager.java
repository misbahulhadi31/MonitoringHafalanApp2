package com.example.asus_pc.monitoringhafalanapp2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by muhtar on 12/26/17.
 */

public class SharedPreferenceManager {
    private static final String SHARED_PREF_NAME = "SharedPreferenceManager";
    private static final String NAMA_PEMARAF = "namaPemaraf";
    @SuppressLint("StaticFieldLeak")
    private static SharedPreferenceManager sharedPreferenceManager;
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public SharedPreferenceManager(Context context) {
        this.context = context;
    }

    public static synchronized SharedPreferenceManager getInstance(Context context){
        if (sharedPreferenceManager == null){
            sharedPreferenceManager = new SharedPreferenceManager(context);
        }

        return sharedPreferenceManager;
    }

    public String getNamaPemaraf(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(NAMA_PEMARAF, null);
    }

    public boolean setNamaPemaraf(String strCode){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NAMA_PEMARAF, strCode);
        editor.apply();
        return  true;
    }

    public void removeNamaPemaraf(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(NAMA_PEMARAF).apply();
    }

}

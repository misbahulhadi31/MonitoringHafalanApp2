package com.example.asus_pc.monitoringhafalanapp2;

import android.app.Application;

import com.eyro.mesosfer.Mesosfer;

/**
 * Created by ASUS-PC on 06/11/2017.
 */

public class mesosfer extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Mesosfer.initialize(this, "xxAQ3L8D3u", "nm3vwP4SryzYsjCFhoGlYzHlS82JH3Bm");
    }
}

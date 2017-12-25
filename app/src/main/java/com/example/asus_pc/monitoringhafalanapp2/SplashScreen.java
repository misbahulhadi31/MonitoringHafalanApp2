package com.example.asus_pc.monitoringhafalanapp2;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.asus_pc.monitoringhafalanapp2.app.Config;
import com.eyro.mesosfer.MesosferData;

import java.util.List;

public class SplashScreen extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private List<MesosferData> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SharedPreferenceManager.getInstance(getApplicationContext()).getNamaPemaraf().equals("")){
                    Intent mainIntent = new Intent(SplashScreen.this, LoginActivity.class);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                } else {
                    Intent mainIntent = new Intent(SplashScreen.this, MainMenuActivity.class);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}

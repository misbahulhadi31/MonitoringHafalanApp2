package com.example.asus_pc.monitoringhafalanapp2;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashScreen.this, MainMenuActivity.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
                Toast.makeText(getApplicationContext(), "Assalamu'alaikum, ustad",
                        Toast.LENGTH_SHORT).show();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}

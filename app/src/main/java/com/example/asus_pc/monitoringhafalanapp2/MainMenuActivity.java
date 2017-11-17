package com.example.asus_pc.monitoringhafalanapp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainMenuActivity extends AppCompatActivity {
    ImageView tambah, monitor, list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        tambah = (ImageView) findViewById(R.id.tambah);
        monitor = (ImageView) findViewById(R.id.monitor);
        list = (ImageView) findViewById(R.id.list);

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, TambahSantriActivity.class);
                startActivity(intent);
            }
        });

        monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, HafalanSantriActivity.class);
                startActivity(intent);
            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, KelolaSantriActivity.class);
                startActivity(intent);
            }
        });
    }
}

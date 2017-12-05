package com.example.asus_pc.monitoringhafalanapp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LihatParafActivity extends AppCompatActivity {
    Button ubah;
    TextView text1, text2, text3, text4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_paraf);

        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LihatParafActivity.this, DetailParafActivity.class);
                startActivity(intent);
            }
        });
    }
}

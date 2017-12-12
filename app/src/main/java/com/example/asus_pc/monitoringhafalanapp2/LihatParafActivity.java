package com.example.asus_pc.monitoringhafalanapp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LihatParafActivity extends AppCompatActivity {
    Button ubah;
    TextView text1, text2, text3, text4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_paraf);

        text1 =  findViewById(R.id.EditText1);
        text2 =  findViewById(R.id.EditText2);
        text3 =  findViewById(R.id.EditText3);
        text4 =  findViewById(R.id.EditText4);
        ubah = findViewById(R.id.lihat);

        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LihatParafActivity.this, DetailParafActivity.class);
                startActivity(intent);
            }
        });
    }
}

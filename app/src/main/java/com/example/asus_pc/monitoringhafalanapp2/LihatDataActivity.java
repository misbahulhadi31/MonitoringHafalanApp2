package com.example.asus_pc.monitoringhafalanapp2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LihatDataActivity extends AppCompatActivity {
    protected Cursor cursor;
    DataSantri dbSantri;
    Button ton1;
    TextView text1, text2, text3, text4, text5, text6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data);

        dbSantri = new DataSantri(this);
        text1 = (TextView) findViewById(R.id.EditText1);
        text2 = (TextView) findViewById(R.id.EditText2);
        text3 = (TextView) findViewById(R.id.EditText3);
        text4 = (TextView) findViewById(R.id.EditText4);
        text5 = (TextView) findViewById(R.id.EditText5);
        text6 = (TextView) findViewById(R.id.EditText6);
        ton1 = (Button) findViewById(R.id.lihat);

        ton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LihatDataActivity.this, ParafSantriActivity.class);
                startActivity(intent);
            }
        });

        SQLiteDatabase db = dbSantri.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM santri WHERE namaSantri = '" +
                getIntent().getStringExtra("namaSantri") + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            cursor.moveToPosition(0);
            text1.setText(cursor.getString(0).toString());
            text2.setText(cursor.getString(1).toString());
            text3.setText(cursor.getString(2).toString());
            text4.setText(cursor.getString(3).toString());
            text5.setText(cursor.getString(4).toString());
            text6.setText(cursor.getString(5).toString());
        }
    }
}

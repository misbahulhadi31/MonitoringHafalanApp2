package com.example.asus_pc.monitoringhafalanapp2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UbahDataActivity extends AppCompatActivity {
    protected Cursor cursor;
    DataSantri dbSantri;
    Button ubah;
    EditText text1, text2, text3, text4, text5, text6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_data);

        dbSantri = new DataSantri(this);
        text1 = (EditText) findViewById(R.id.noInduk);
        text2 = (EditText) findViewById(R.id.namaSantri);
        text3 = (EditText) findViewById(R.id.kelas);
        text4 = (EditText) findViewById(R.id.konsulat);
        text5 = (EditText) findViewById(R.id.namaWali);
        text6 = (EditText) findViewById(R.id.noTelp);
        ubah = (Button) findViewById(R.id.simpan);

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

        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                SQLiteDatabase db = dbSantri.getWritableDatabase();
                db.execSQL("update santri set namaSantri='"+
                        text2.getText().toString() +"', kelas='" +
                        text3.getText().toString()+"', konsulat='"+
                        text4.getText().toString() +"', namaWali='" +
                        text5.getText().toString() +"', noTelp='" +
                        text6.getText().toString() + "' where noInduk='" +
                        text1.getText().toString()+"'");
                Toast.makeText(getApplicationContext(), "Berhasil Diubah", Toast.LENGTH_LONG).show();
                KelolaSantriActivity.ka.RefreshList();
                finish();
            }
        });
    }
}

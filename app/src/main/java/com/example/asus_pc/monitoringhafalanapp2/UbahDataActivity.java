package com.example.asus_pc.monitoringhafalanapp2;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
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

        text1 = (EditText) findViewById(R.id.noInduk);
        text2 = (EditText) findViewById(R.id.namaSantri);
        text3 = (EditText) findViewById(R.id.kelas);
        text4 = (EditText) findViewById(R.id.konsulat);
        text5 = (EditText) findViewById(R.id.namaWali);
        text6 = (EditText) findViewById(R.id.noTelp);
        ubah = (Button) findViewById(R.id.simpan);

        Intent i = getIntent();
        text1.setText(i.getStringExtra(config.NOMOR_INDUK));
        text2.setText(i.getStringExtra(config.NAMA_SANTRI));
        text3.setText(i.getStringExtra(config.KELAS));
        text4.setText(i.getStringExtra(config.KONSULAT));
        text5.setText(i.getStringExtra(config.NAMA_WALI));
        text6.setText(i.getStringExtra(config.NO_TELEPON));


        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(UbahDataActivity.this);
                ab.setMessage("Yakin Untuk Mengubah Data ?")
                        .setCancelable(false)
                        .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getApplicationContext(), "Data Berhasil Diubah", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UbahDataActivity.this, KelolaSantriActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = ab.create();
                alert.show();
            }
        });
    }
}

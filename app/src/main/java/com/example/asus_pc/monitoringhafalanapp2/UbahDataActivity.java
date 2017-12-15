package com.example.asus_pc.monitoringhafalanapp2;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eyro.mesosfer.GetCallback;
import com.eyro.mesosfer.MesosferData;
import com.eyro.mesosfer.MesosferException;
import com.eyro.mesosfer.MesosferObject;
import com.eyro.mesosfer.SaveCallback;

public class UbahDataActivity extends AppCompatActivity {
    private String objectId;
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
        objectId = i.getStringExtra("id");
        /*text1.setText(i.getStringExtra(Config.NOMOR_INDUK));
        text2.setText(i.getStringExtra(Config.NAMA_SANTRI));
        text3.setText(i.getStringExtra(Config.KELAS));
        text4.setText(i.getStringExtra(Config.KONSULAT));
        text5.setText(i.getStringExtra(Config.NAMA_WALI));
        text6.setText(i.getStringExtra(Config.NO_TELEPON));*/

        getDatabyId(objectId);

        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(UbahDataActivity.this);
                ab.setMessage("Yakin Untuk Mengubah Data ?")
                        .setCancelable(false)
                        .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                updateDataSantri(objectId);
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

    private void getDatabyId(String objectId){
        MesosferData.createWithObjectId(objectId).fetchAsync(new GetCallback<MesosferData>() {
            @Override
            public void done(MesosferData mesosferData, MesosferException e) {
                MesosferObject mesosferObject = mesosferData.getData();
                text1.setText(mesosferObject.optString(Config.NOMOR_INDUK));
                text2.setText(mesosferObject.optString(Config.NAMA_SANTRI));
                text3.setText(mesosferObject.optString(Config.KELAS));
                text4.setText(mesosferObject.optString(Config.KONSULAT));
                text5.setText(mesosferObject.optString(Config.NAMA_WALI));
                text6.setText(mesosferObject.optString(Config.NO_TELEPON));
            }
        });
    }

    private void updateDataSantri(String objectId){
        MesosferData.createWithObjectId(objectId).fetchAsync(new GetCallback<MesosferData>() {
            @Override
            public void done(MesosferData mesosferData, MesosferException e) {
                MesosferData.createData("Santri");
                mesosferData.setData(Config.NOMOR_INDUK, text1.getText().toString());
                mesosferData.setData(Config.NAMA_SANTRI, text2.getText().toString());
                mesosferData.setData(Config.KELAS, text3.getText().toString());
                mesosferData.setData(Config.KONSULAT, text4.getText().toString());
                mesosferData.setData(Config.NAMA_WALI, text5.getText().toString());
                mesosferData.setData(Config.NO_TELEPON, text6.getText().toString());
                mesosferData.saveAsync(new SaveCallback() {
                    @Override
                    public void done(MesosferException e) {
                        if (e != null){
                            Toast.makeText(UbahDataActivity.this, "update data santri gagal", Toast.LENGTH_SHORT).show();
                        } else {
                                Toast.makeText(getApplicationContext(), "update data santri berhasil", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UbahDataActivity.this, KelolaSantriActivity.class);
                                startActivity(intent);
                        }
                    }
                });
            }
        });
    }
}

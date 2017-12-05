package com.example.asus_pc.monitoringhafalanapp2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eyro.mesosfer.MesosferData;
import com.eyro.mesosfer.MesosferException;
import com.eyro.mesosfer.MesosferUser;
import com.eyro.mesosfer.RegisterCallback;
import com.eyro.mesosfer.SaveCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class TambahSantriActivity extends AppCompatActivity {
    EditText text1, text2, text3, text4, text5, text6;
    private String noIndukString, namaSantriString, kelasString, konsulatString, namaWaliString, noTelpString;

    private ProgressDialog loading;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_santri);

        text1 = (EditText) findViewById(R.id.noInduk);
        text2 = (EditText) findViewById(R.id.namaSantri);
        text3 = (EditText) findViewById(R.id.kelas);
        text4 = (EditText) findViewById(R.id.konsulat);
        text5 = (EditText) findViewById(R.id.namaWali);
        text6 = (EditText) findViewById(R.id.noTelp);

        loading = new ProgressDialog(this);
        loading.setIndeterminate(true);
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);


    }

    public void handleTambahSantri(View view) {
        // get all value from input
        noIndukString    = text1.getText().toString();
        namaSantriString = text2.getText().toString();
        kelasString      = text3.getText().toString();
        konsulatString   = text4.getText().toString();
        namaWaliString   = text5.getText().toString();
        noTelpString     = text6.getText().toString();


        // validating input values
        if (!isInputValid()) {
            // return if there is an invalid input
            return;
        }

        tambahData();
    }

    private boolean isInputValid() {
        // validating all input values if it is empty
        if (TextUtils.isEmpty(noIndukString)) {
            Toast.makeText(this, "Isi nomor Induk", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(namaSantriString)) {
            Toast.makeText(this, "Isi nama Santri", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(kelasString)) {
            Toast.makeText(this, "Isi kelas", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(konsulatString)) {
            Toast.makeText(this, "Isi konsulat", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(namaWaliString)) {
            Toast.makeText(this, "Isi nama wali", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(noTelpString)) {
            Toast.makeText(this, "Isi nomor telepon", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void tambahData() {
        // showing a progress dialog loading
        loading.setMessage("Menyimpan data....");
        loading.show();

        // create new instance of Mesosfer User
        MesosferData data = MesosferData.createData("Santri");
        // set default field
        data.setData("noInduk", noIndukString);
        data.setData("namaSantri", namaSantriString);
        data.setData("kelas", kelasString);
        data.setData("konsulat", konsulatString);
        data.setData("namaWali", namaWaliString);
        data.setData("noTelepon", noTelpString);
        // execute register user asynchronous
        data.saveAsync(new SaveCallback() {
            @Override
            public void done(MesosferException e) {
                // hide progress dialog loading
                loading.dismiss();

                // setup alert dialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahSantriActivity.this);
                builder.setNegativeButton(android.R.string.ok, null);
                Toast.makeText(getApplicationContext(), "Data telah disimpan", Toast.LENGTH_LONG).show();

                // check if there is an exception happen
                if (e != null) {
                    builder.setTitle("Error Happen");
                    builder.setMessage(
                            String.format(Locale.getDefault(), "Error code: %d\nDescription: %s",
                                    e.getCode(), e.getMessage())
                    );
                    dialog = builder.show();
                    return;
                }
            }
        });

        startActivity(new Intent(TambahSantriActivity.this, MainMenuActivity.class));
    }
}

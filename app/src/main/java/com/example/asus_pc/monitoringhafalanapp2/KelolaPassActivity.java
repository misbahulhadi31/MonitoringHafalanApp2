package com.example.asus_pc.monitoringhafalanapp2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus_pc.monitoringhafalanapp2.app.Config;
import com.eyro.mesosfer.FindCallback;
import com.eyro.mesosfer.GetCallback;
import com.eyro.mesosfer.MesosferData;
import com.eyro.mesosfer.MesosferException;
import com.eyro.mesosfer.MesosferObject;
import com.eyro.mesosfer.MesosferQuery;
import com.eyro.mesosfer.SaveCallback;

import java.util.List;

public class KelolaPassActivity extends AppCompatActivity {
    private String objectId;
    DataSantri dbSantri;
    Button save;
    EditText pass1, pass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_pass);

        pass1 = (EditText) findViewById(R.id.oldPass);
        pass2 = (EditText) findViewById(R.id.newPass);
        save = (Button) findViewById(R.id.daftar);

        Intent i = getIntent();
        objectId = i.getStringExtra("id");

        //getDatabyId(objectId);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(KelolaPassActivity.this);
                ab.setMessage("Yakin Untuk Mengubah Data ?")
                        .setCancelable(false)
                        .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (!isInputValid()) {
                                    // return if there is an invalid input
                                    return;
                                }else {
                                    checkPassword();
                                }
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
                pass2.setText(mesosferObject.optString(Config.PASS));

            }
        });
    }

    private void updateDataSantri(String objectId){
        MesosferData.createWithObjectId(objectId).fetchAsync(new GetCallback<MesosferData>() {
            @Override
            public void done(MesosferData mesosferData, MesosferException e) {
                MesosferData.createData("Ustad");
                mesosferData.setData(Config.PASS, pass2.getText().toString());
                mesosferData.setData("email", SharedPreferenceManager.getInstance(getApplicationContext()).getEmail());
                mesosferData.setData("namaUstad", SharedPreferenceManager.getInstance(getApplicationContext()).getNamaPemaraf());
                mesosferData.saveAsync(new SaveCallback() {
                    @Override
                    public void done(MesosferException e) {
                        if (e != null){
                            Toast.makeText(KelolaPassActivity.this, "Data tidak tersimpan", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Password berhasil diubah", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(KelolaPassActivity.this, MainMenuActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }

    private void checkPassword() {
        final String strWherePassword = pass1.getText().toString();
        MesosferQuery<MesosferData> query = MesosferData.getQuery("Ustad");
        query.whereEqualTo("password", strWherePassword);

        query.findAsync(new FindCallback<MesosferData>() {
            @Override
            public void done(List<MesosferData> list, MesosferException e) {
                if (list.size() == 0) {
                    Toast.makeText(KelolaPassActivity.this, "Password lama salah", Toast.LENGTH_SHORT).show();
                } else {
                    updateDataSantri(SharedPreferenceManager.getInstance(getApplicationContext()).getObjectId());
                }
            }
        });
    }

    private boolean isInputValid() {
        // validating all input values if it is empty
        if (TextUtils.isEmpty(pass1.getText().toString())) {
            Toast.makeText(this, "Masukkan password lama", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(pass2.getText().toString())) {
            Toast.makeText(this, "Masukkan password baru", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}

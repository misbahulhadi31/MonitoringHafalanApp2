package com.example.asus_pc.monitoringhafalanapp2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.eyro.mesosfer.GetCallback;
import com.eyro.mesosfer.MesosferData;
import com.eyro.mesosfer.MesosferException;
import com.eyro.mesosfer.MesosferObject;
import com.eyro.mesosfer.SaveCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailParafActivity extends AppCompatActivity {
    EditText text1, text2, text3;
    private String parafString, namaString, tanggalString, pemarafString, isiCatatanString;
    private Date tanggal;
    private Button simpan;
    private CheckBox checkBox;
    private String selesai;

    private ProgressDialog loading;
    private AlertDialog dialog;

    private MesosferData data;

    private static final String TAG = DetailParafActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_paraf);

        text1 = findViewById(R.id.tanggal);
        text2 = findViewById(R.id.pemaraf);
        text3 = findViewById(R.id.isiCatatan);
        simpan = findViewById(R.id.simpan);

        checkBox = findViewById(R.id.checkBox);

        final Intent intent = getIntent();
        parafString = intent.getStringExtra(Config.NO_PARAF);
        namaString = intent.getStringExtra(Config.NAMA_SANTRI);

        Log.d("parafString", "Value: " + parafString);
        Log.d("namaString", "Value: " + namaString);

        loading = new ProgressDialog(this);
        loading.setIndeterminate(true);
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);

        fetchData(intent.getStringExtra("id"));

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDataParaf(intent.getStringExtra("id"));
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DetailParafActivity.this, ParafSantriActivity.class);
        intent.putExtra(Config.NAMA_SANTRI, namaString);
        intent.putExtra(Config.SELESAI, selesai);
        startActivity(intent);
    }

    private boolean isInputValid() {
        if (TextUtils.isEmpty(tanggalString)) {
            Toast.makeText(this, "Isi tanggal", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            try {
                tanggal = format.parse(tanggalString);
            } catch (ParseException e) {
                Toast.makeText(this, "Mohon isi dengan format `01-01-2001`", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (TextUtils.isEmpty(pemarafString)) {
            Toast.makeText(this, "Isi pemaraf", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(isiCatatanString)) {
            Toast.makeText(this, "Isi catatan", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void fetchData(String objectId) {
        MesosferData.createWithObjectId(objectId).fetchAsync(new GetCallback<MesosferData>() {
            @Override
            public void done(MesosferData mesosferData, MesosferException e) {
                MesosferObject object = mesosferData.getData();
                String namaPemaraf = object.optString(Config.NAMA_PEMARAF);
                String catatan = object.optString(Config.CATATAN);
                String tanggalParaf = object.optString(Config.TANGGAL_PARAF);
                String selesai = object.optString(Config.SELESAI);

                text1.setText(tanggalParaf);
                text2.setText(namaPemaraf);
                text3.setText(catatan);

                if (selesai.equals("1")) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
            }
        });
    }

    private void updateDataParaf(String objectId){
        MesosferData.createWithObjectId(objectId).fetchAsync(new GetCallback<MesosferData>() {
            @Override
            public void done(MesosferData mesosferData, MesosferException e) {
                MesosferObject object = mesosferData.getData();
                String namaPemaraf = object.optString(Config.NAMA_PEMARAF);
                String catatan = object.optString(Config.CATATAN);
                String tanggalParaf = object.optString(Config.TANGGAL_PARAF);
                String selesai = object.optString(Config.SELESAI);

                //text1.setText(tanggalParaf);
                //text2.setText(namaPemaraf);
                //text3.setText(catatan);

                if (selesai.equals("1")){
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }

                if (mesosferData == null) {
                    mesosferData = MesosferData.createData("ParafHafalan");
                } else {
                    mesosferData.setData(Config.NAMA_SANTRI, namaString);
                    mesosferData.setData(Config.NO_PARAF, object.optString(Config.NO_PARAF));
                    mesosferData.setData(Config.NAMA_SURAH, object.optString(Config.NAMA_SURAH));
                    mesosferData.setData(Config.TANGGAL_PARAF, text1.getText().toString());
                    mesosferData.setData(Config.NAMA_PEMARAF, text2.getText().toString());
                    mesosferData.setData(Config.CATATAN, text3.getText().toString());
                    mesosferData.setData(Config.SELESAI, "1");
                    mesosferData.saveAsync(new SaveCallback() {
                        @Override
                        public void done(MesosferException e) {
                            if (e != null) {
                                Toast.makeText(DetailParafActivity.this, "update data paraf gagal", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(DetailParafActivity.this, "update data parad berhasil", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), ListHafalanActivity.class));
                            }
                        }
                    });
                }
            }
        });
    }
}
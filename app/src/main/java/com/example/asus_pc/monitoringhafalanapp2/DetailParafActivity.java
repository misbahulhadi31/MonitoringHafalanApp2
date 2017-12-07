package com.example.asus_pc.monitoringhafalanapp2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.eyro.mesosfer.MesosferData;
import com.eyro.mesosfer.MesosferException;
import com.eyro.mesosfer.MesosferQuery;
import com.eyro.mesosfer.MesosferUser;
import com.eyro.mesosfer.RegisterCallback;
import com.eyro.mesosfer.SaveCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailParafActivity extends AppCompatActivity {
    EditText text1,text2, text3;
    private String parafString, namaString, tanggalString, pemarafString, isiCatatanString;
    private Date tanggal;
    private CheckBox checkBox;
    private String selesai;

    private ProgressDialog loading;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_paraf);

        text1 =  findViewById(R.id.tanggal);
        text2 =  findViewById(R.id.pemaraf);
        text3 =  findViewById(R.id.isiCatatan);

        checkBox = findViewById(R.id.checkBox);

        Intent intent = getIntent();
        parafString = intent.getStringExtra(config.NO_PARAF);
        namaString = intent.getStringExtra(config.NAMA_SANTRI);

        Log.d("parafString", "Value: " + parafString);
        Log.d("namaString", "Value: " + namaString);

//        Intent intent = getIntent();
//
//        text1.setText(intent.getStringExtra(config.TANGGAL_PARAF));
//        text2.setText(intent.getStringExtra(config.NAMA_PEMARAF));
//        text3.setText(intent.getStringExtra(config.CATATAN));

//        Log.d("selesai", intent.getStringExtra(config.SELESAI));




        loading = new ProgressDialog(this);
        loading.setIndeterminate(true);
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DetailParafActivity.this, ParafSantriActivity.class);
        startActivity(intent);
    }

    public void handleDetailParaf(View view) {
        // get all value from input
        tanggalString = text1.getText().toString();
        pemarafString = text2.getText().toString();
        isiCatatanString = text3.getText().toString();


        // validating input values
        if (!isInputValid()) {
            // return if there is an invalid input
            return;
        }

        isiCatatan();
    }

    private boolean isInputValid() {
        // validating all input values if it is empty
        if (TextUtils.isEmpty(tanggalString)) {
            Toast.makeText(this, "Isi tanggal", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            try {
                tanggal = format.parse(tanggalString);
            } catch (ParseException e) {
                // show error message when user input invalid format of date
                Toast.makeText(this, "Mohon isi dengan format `01-01-2001`", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (TextUtils.isEmpty(pemarafString)) {
            Toast.makeText(this, "Isi pemaraf", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(isiCatatanString) ) {
            Toast.makeText(this, "Isi catatan", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void isiCatatan() {
        // showing a progress dialog loading
        loading.setMessage("Menyimpan data....");
        loading.show();

        // create new instance of Mesosfer User
        MesosferData data = MesosferData.createData("ParafHafalan");

        if (checkBox.isChecked()){
            selesai = "1";
        } else {
            selesai = "0";
        }


        // set default field
        data.setData("tanggalParaf", tanggal);
        data.setData("namaPemaraf", pemarafString);
        data.setData("catatan", isiCatatanString);
        data.setData(config.NO_PARAF, parafString);
        data.setData(config.NAMA_SANTRI, namaString);
        data.setData(config.SELESAI, selesai);
        // execute register user asynchronous
        data.saveAsync(new SaveCallback() {
            @Override
            public void done(MesosferException e) {
                // hide progress dialog loading
                loading.dismiss();

                // setup alert dialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailParafActivity.this);
                builder.setNegativeButton(android.R.string.ok, null);

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
                Toast.makeText(DetailParafActivity.this, "Data Telah Disimpan", Toast.LENGTH_SHORT).show();
            }
        });

        startActivity(new Intent(DetailParafActivity.this, ParafSantriActivity.class));
    }
}

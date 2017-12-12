package com.example.asus_pc.monitoringhafalanapp2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.eyro.mesosfer.MesosferData;
import com.eyro.mesosfer.MesosferException;
import com.eyro.mesosfer.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class AddParafActivity extends AppCompatActivity {
    private Spinner spinnerParaf;
    private Button btnSave;

    private String spinnerValue;
    private String surahValue;
    private String parafValue;
    private static final String TAG = AddParafActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_paraf);

        spinnerParaf = findViewById(R.id.spinner_paraf);
        btnSave = findViewById(R.id.btn_save);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Tambah Paraf");

        Log.d(TAG,"get data " + SharedPrefManager.getInstance(getApplicationContext()).getData());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.paraf, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerParaf.setAdapter(adapter);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addParaf();
            }
        });
    }

    private void addParaf() {
        Intent intent = getIntent();

        try {
            JSONObject jsonObject = new JSONObject(SharedPrefManager.getInstance(getApplicationContext()).getData());
            parafValue = jsonObject.getString(Config.NO_PARAF);
            Log.d(TAG, "paraf object " + parafValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "paraf val "+ parafValue);

        spinnerValue = String.valueOf(spinnerParaf.getSelectedItem());

        switch (spinnerValue) {
            case "Paraf 1":
                surahValue = "An-Naas - At-Takwir";
                break;
            case "Paraf 2":
                surahValue = "Al-Qari'ah - Ad-Duha";
                break;
            case "Paraf 3":
                surahValue = "Al-Lail - Al-Balad";
                break;
            case "Paraf 4":
                surahValue = "Al-Fajr - Al-'Ala";
                break;
            case "Paraf 5":
                surahValue = "At-Taariq - Al-Insyiqaq";
                break;
            case "Paraf 6":
                surahValue = "Al-Mutaffifin - At-Takwir";
                break;
            case "Paraf 7":
                surahValue = "Abasa - An-Naba";
                break;
            case "Paraf 8":
                surahValue = "Al-Mulk";
                break;
            case "Paraf 9":
                surahValue = "Al-Waqiah";
                break;
            case "Paraf 10":
                surahValue = "Ar-Rahman";
                break;
            case "Paraf 11":
                surahValue = "Yaasin";
                break;
            case "Paraf 12":
                surahValue = "Tahlil";
                break;
            default:
                surahValue = "zonk";
                break;
        }

        if (spinnerValue.equals(SharedPrefManager.getInstance(getApplicationContext()).getData())){
            Toast.makeText(this, "sama", Toast.LENGTH_SHORT).show();
        } else {
            MesosferData data = MesosferData.createData("ParafHafalan");

            data.setData(Config.NAMA_SANTRI, intent.getStringExtra(Config.NAMA_SANTRI));
            data.setData(Config.NAMA_SURAH, surahValue);
            data.setData(Config.NO_PARAF, spinnerValue);

            data.saveAsync(new SaveCallback() {
                @Override
                public void done(MesosferException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setNegativeButton(android.R.string.ok, null);
                    // check if there is an exception happen
                    if (e != null) {
                        builder.setTitle("Error Happen");
                        builder.setMessage(
                                String.format(Locale.getDefault(), "Error code: %d\nDescription: %s",
                                        e.getCode(), e.getMessage())
                        );
                        return;
                    }
                    Toast.makeText(getApplicationContext(), "Tambah paraf berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}

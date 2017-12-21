package com.example.asus_pc.monitoringhafalanapp2;

import android.support.v7.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.asus_pc.monitoringhafalanapp2.app.Config;
import com.eyro.mesosfer.FindCallback;
import com.eyro.mesosfer.MesosferData;
import com.eyro.mesosfer.MesosferException;
import com.eyro.mesosfer.MesosferQuery;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ParafSantriActivity extends AppCompatActivity {
    private GridView gridView;
    private SimpleAdapter adapter;
    private ProgressDialog loading;
    private AlertDialog dialog;
    private List<Map<String, String>> mapDataList;
    private ArrayList<ParafSantri> parafSantriArrayList;
    private static final int[] to = new int[] { R.id.listParaf, R.id.listSurah};
    private static final String[] from = new String[] {"noParaf","namaSurah"};
    private String parafString, namaString, surahString, waliString, teleponString;
    private List<MesosferData> listData;
    private TextView textViewEmpty;
    private ImageView imageView;
    private CustomListAdapter customListAdapter;
    private MesosferData data;
    private static final String TAG = ParafSantriActivity.class.getSimpleName();

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ParafSantriActivity.this, ListHafalanActivity.class);
        startActivity(intent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paraf_santri);

        mapDataList = new ArrayList<>();

        parafSantriArrayList = new ArrayList<>();

        Intent intent = getIntent();
        parafString = intent.getStringExtra(Config.NO_PARAF);
        surahString = intent.getStringExtra(Config.NAMA_SURAH);
        namaString = intent.getStringExtra(Config.NAMA_SANTRI);
        waliString = intent.getStringExtra(Config.NAMA_WALI);
        teleponString = intent.getStringExtra(Config.NO_TELEPON);

//        Log.d(TAG, namaString);

        gridView =  findViewById(R.id.dataParaf);
        textViewEmpty = findViewById(R.id.emptyElement2);
        imageView = findViewById(R.id.listGambar);
        adapter = new SimpleAdapter(getApplicationContext(), mapDataList, R.layout.paraf_list, from, to);
        customListAdapter = new CustomListAdapter(getApplicationContext(), parafSantriArrayList);

        gridView.setAdapter(customListAdapter);

        loading = new ProgressDialog(getApplicationContext());
        loading.setIndeterminate(true);
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);

        updateAndShowDataList();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                data = listData.get(i);
                //Toast.makeText(ParafSantriActivity.this, ""+data.getDataString("selesai"), Toast.LENGTH_SHORT).show();
                final CharSequence[] dialogitem = {"Isi Paraf", "Kirim Pesan"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ParafSantriActivity.this);
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch(item){
                            case 0 :
                                Intent intent = new Intent(getApplicationContext(), DetailParafActivity.class);
                                intent.putExtra(Config.TANGGAL_PARAF, data.getDataString(Config.TANGGAL_PARAF));
                                intent.putExtra(Config.USTAD, data.getDataString(Config.USTAD));
                                intent.putExtra(Config.CATATAN, data.getDataString(Config.CATATAN));
                                intent.putExtra(Config.SELESAI, data.getDataString(Config.SELESAI));
                                intent.putExtra(Config.NO_PARAF, data.getDataString(Config.NO_PARAF));
                                intent.putExtra(Config.NAMA_SANTRI, namaString);
                                intent.putExtra("id", data.getObjectId());
                                //parafSurah();
                                startActivity(intent);
                                break;
                            case 1 :
                                Intent i = new Intent(getApplicationContext(), KirimPesanActivity.class);
                                i.putExtra(Config.NAMA_SANTRI, namaString);
                                i.putExtra(Config.NAMA_WALI, waliString);
                                i.putExtra(Config.NO_TELEPON, teleponString);
                                i.putExtra(Config.NO_PARAF, data.getDataString(Config.NO_PARAF));
                                i.putExtra(Config.NAMA_SURAH, data.getDataString(Config.NAMA_SURAH));
                                i.putExtra(Config.PESAN, data.getDataString(Config.PESAN));
                                //Toast.makeText(getApplicationContext(), "Hafalan Belum Selesai", Toast.LENGTH_LONG).show();
                                startActivity(i);
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
        gridView.setEmptyView(imageView);
        gridView.setEmptyView(textViewEmpty);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Paraf Surah");
    }

    private void updateAndShowDataList() {
        //select * from parafSurah where namaSantri = '?';
        MesosferQuery<MesosferData> query = MesosferData.getQuery("ParafHafalan");
        query.whereEqualTo(Config.NAMA_SANTRI, namaString);
        //query.whereEqualTo();


        // showing a progress dialog loading
        //loading.setMessage("Loading...");
        //loading.show();

        query.findAsync(new FindCallback<MesosferData>() {
            @Override
            public void done(List<MesosferData> list, MesosferException e) {
                // hide progress dialog loading
                loading.dismiss();

                // check if there is an exception happen
                if (e != null) {
                    // setup alert dialog builder
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setNegativeButton(android.R.string.ok, null);
                    builder.setTitle("Error Happen");
                    builder.setMessage(
                            String.format(Locale.getDefault(), "Error code: %d\nDescription: %s",
                                    e.getCode(), e.getMessage())
                    );
                    dialog = builder.show();
                    return;
                }

                // clear all data list
                mapDataList.clear();
                listData = new ArrayList<>(list);
                for (final MesosferData data : list) {
                    Map<String, String> map = new HashMap<>();
                    ParafSantri parafSantri = new ParafSantri();

                    map.put("noParaf", data.getDataString("noParaf"));
                    map.put("namaSurah", data.getDataString("namaSurah"));

                    parafSantri.setNomorParaf(data.getDataString(Config.NO_PARAF));
                    parafSantri.setNamaSurah(data.getDataString(Config.NAMA_SURAH));
                    parafSantri.setSelesai(data.getDataString(Config.SELESAI));
                    parafSantri.setTanggalParaf(data.getDataString(Config.TANGGAL_PARAF));
                    parafSantri.setNamaPemaraf(data.getDataString(Config.NAMA_PEMARAF));
                    parafSantri.setCatatan(data.getDataString(Config.CATATAN));

                    try {
                        map.put("data", data.toJSON().toString(0));
                        Log.d("nomor paraf", data.getDataString("noParaf"));
                    } catch (JSONException e1) {
                        map.put("data", data.toJSON().toString());
                    }

                    mapDataList.add(map);
                    parafSantriArrayList.add(parafSantri);
                }

                customListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.paraf_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.add){
            Intent intent = new Intent(getApplicationContext(), AddParafActivity.class);
            intent.putExtra(Config.NAMA_SANTRI, namaString);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}

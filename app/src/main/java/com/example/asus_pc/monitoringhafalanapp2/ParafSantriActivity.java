package com.example.asus_pc.monitoringhafalanapp2;

import android.support.v7.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.eyro.mesosfer.FindCallback;
import com.eyro.mesosfer.MesosferData;
import com.eyro.mesosfer.MesosferException;
import com.eyro.mesosfer.MesosferQuery;
import com.eyro.mesosfer.SaveCallback;

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
    private String parafString, namaString, surahString;
    private List<MesosferData> listData;
    private TextView textViewEmpty;
    private ImageView imageView;
    private CustomListAdapter customListAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paraf_santri);

        mapDataList = new ArrayList<>();

        parafSantriArrayList = new ArrayList<>();

        Intent intent = getIntent();
        parafString = intent.getStringExtra(config.NO_PARAF);
        surahString = intent.getStringExtra(config.NAMA_SURAH);
        namaString = intent.getStringExtra(config.NAMA_SANTRI);

        gridView = (GridView) findViewById(R.id.dataParaf);
        textViewEmpty = (TextView)findViewById(R.id.emptyElement2);
        imageView = (ImageView)findViewById(R.id.listGambar);
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
                final MesosferData data = listData.get(i);
                //Toast.makeText(ParafSantriActivity.this, ""+data.getDataString("selesai"), Toast.LENGTH_SHORT).show();
                final CharSequence[] dialogitem = {"Cek Paraf", "Kirim Pesan"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ParafSantriActivity.this);
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch(item){
                            case 0 :
                                Intent intent = new Intent(getApplicationContext(), DetailParafActivity.class);
                                intent.putExtra(config.TANGGAL_PARAF, data.getDataString(config.TANGGAL_PARAF));
                                intent.putExtra(config.NAMA_PEMARAF, data.getDataString(config.NAMA_PEMARAF));
                                intent.putExtra(config.CATATAN, data.getDataString(config.CATATAN));
                                intent.putExtra(config.SELESAI, data.getDataString(config.SELESAI));
                                intent.putExtra(config.NO_PARAF, data.getDataString(config.NO_PARAF));
                                intent.putExtra(config.NAMA_SANTRI, data.getDataString(config.NAMA_SANTRI));
                                //parafSurah();
                                startActivity(intent);
                                break;
                            case 1 :
                                Toast.makeText(getApplicationContext(), "Hafalan Belum Selesai", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
        gridView.setEmptyView(imageView);
        gridView.setEmptyView(textViewEmpty);
    }

    private void updateAndShowDataList() {
        MesosferQuery<MesosferData> query = MesosferData.getQuery("ParafSurah");


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

                    parafSantri.setNomorParaf(data.getDataString(config.NO_PARAF));
                    parafSantri.setNamaSurah(data.getDataString(config.NAMA_SURAH));
                    parafSantri.setSelesai(data.getDataString(config.SELESAI));
                    parafSantri.setTanggalParaf(data.getDataString(config.TANGGAL_PARAF));
                    parafSantri.setNamaPemaraf(data.getDataString(config.NAMA_PEMARAF));
                    parafSantri.setCatatan(data.getDataString(config.CATATAN));

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

    private void parafSurah() {
        // showing a progress dialog loading
        loading.setMessage("Menyimpan data....");
        loading.show();

        // create new instance of Mesosfer User
        MesosferData data = MesosferData.createData("ParafHafalan");


        // set default field
        data.setData("noParaf", parafString);
        data.setData("namaSurah", surahString);
        data.setData(config.NAMA_SANTRI, namaString);
        // execute register user asynchronous
        data.saveAsync(new SaveCallback() {
            @Override
            public void done(MesosferException e) {
                // hide progress dialog loading
                loading.dismiss();

                // setup alert dialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(ParafSantriActivity.this);
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
                Toast.makeText(ParafSantriActivity.this, "Data Telah Disimpan", Toast.LENGTH_SHORT).show();
            }
        });

    }



}

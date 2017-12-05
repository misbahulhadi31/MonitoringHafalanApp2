package com.example.asus_pc.monitoringhafalanapp2;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

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

public class KelolaSantriActivity extends AppCompatActivity {
    private ListView listView;
    private SimpleAdapter adapter;
    private ProgressDialog loading;
    private android.support.v7.app.AlertDialog dialog;
    private List<Map<String, String>> mapDataList;
    private static final int[] to = new int[] { R.id.textNomor, R.id.texttNama};
    private static final String[] from = new String[] {"noInduk","namaSantri"};
    private List<MesosferData> listData;
    private TextView textViewEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_santri);

        mapDataList = new ArrayList<>();

        listView = (ListView) findViewById(R.id.dataSantri);
        textViewEmpty = (TextView)findViewById(R.id.emptyElement);
        adapter = new SimpleAdapter(getApplicationContext(), mapDataList, R.layout.santri_list, from, to);

        listView.setAdapter(adapter);

        loading = new ProgressDialog(getApplicationContext());
        loading.setIndeterminate(true);
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);

        updateAndShowDataList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final MesosferData data = listData.get(i);

                final CharSequence[] dialogitem = {"Ubah Data", "Hapus Data"};
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(KelolaSantriActivity.this);
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch(item){
                            case 0 :
                                Intent intent = new Intent(getApplicationContext(), UbahDataActivity.class);
                                intent.putExtra(config.NOMOR_INDUK, data.getDataString(config.NOMOR_INDUK));
                                intent.putExtra(config.NAMA_SANTRI, data.getDataString(config.NAMA_SANTRI));
                                intent.putExtra(config.KELAS, data.getDataString(config.KELAS));
                                intent.putExtra(config.KONSULAT, data.getDataString(config.KONSULAT));
                                intent.putExtra(config.NAMA_WALI, data.getDataString(config.NAMA_WALI));
                                intent.putExtra(config.NO_TELEPON, data.getDataString(config.NO_TELEPON));
                                startActivity(intent);
                                break;
                            case 1 :
                                android.support.v7.app.AlertDialog.Builder ab = new android.support.v7.app.AlertDialog.Builder(KelolaSantriActivity.this);
                                ab.setMessage("Yakin Untuk Menghapus ?")
                                        .setCancelable(false)
                                        .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Toast.makeText(getApplicationContext(), "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                                android.support.v7.app.AlertDialog alert = ab.create();
                                alert.show();
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
        listView.setEmptyView(textViewEmpty);
    }

    private void updateAndShowDataList() {
        MesosferQuery<MesosferData> query = MesosferData.getQuery("Santri");

        // showing a progress dialog loading
        //  loading.setMessage("Loading...");
//        loading.show();

        query.findAsync(new FindCallback<MesosferData>() {
            @Override
            public void done(List<MesosferData> list, MesosferException e) {
                // hide progress dialog loading
                loading.dismiss();

                // check if there is an exception happen
                if (e != null) {
                    // setup alert dialog builder
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getApplicationContext());
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
                    map.put("noInduk", data.getDataString("noInduk"));
                    map.put("namaSantri", data.getDataString("namaSantri"));
                    try {
                        map.put("data", data.toJSON().toString(0));
                    } catch (JSONException e1) {
                        map.put("data", data.toJSON().toString());
                    }

                    mapDataList.add(map);
                }

                adapter.notifyDataSetChanged();
            }
        });
    }
}

package com.example.asus_pc.monitoringhafalanapp2;

import android.app.AlertDialog;
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
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ParafSantriActivity extends AppCompatActivity {
    String[] daftar;
    Menu menu;
    protected Cursor cursor;
    DataSantri dbcenter;
    public static ParafSantriActivity pa;

    GridView gridView;
    ArrayList<paraf> parafArrayList;
    parafAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paraf_santri);

        pa = this;
        dbcenter = new DataSantri(this);
        RefreshList();

        gridView = (GridView) findViewById(R.id.dataParaf);
        parafArrayList = new ArrayList<>();
        adapter = new parafAdapter(parafArrayList,this);
        gridView.setAdapter(adapter);
        addParaf();
        adapter.notifyDataSetChanged();

    }

    public void RefreshList(){
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("select * from santri",null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc=0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            daftar[cc] = cursor.getString(1).toString();
        }
        gridView = (GridView) findViewById(R.id.dataParaf);
        gridView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        gridView.setSelected(true);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg11) {
                final String selection = daftar[arg2];
                final CharSequence[] dialogitem = {"Cek Hafalan", "Kirim Pesan"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ParafSantriActivity.this);
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch(item){
                            case 0 :
                                Intent i = new Intent(getApplicationContext(), DetailParafActivity.class);
                                i.putExtra("namaSantri", selection);
                                startActivity(i);
                                break;
                            case 1 :
                                Toast.makeText(getApplicationContext(), "Hafalan Belum Selesai", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                });
                builder.create().show();
            }});
        ((ArrayAdapter)gridView.getAdapter()).notifyDataSetInvalidated();
    }

    public void addParaf() {
        parafArrayList.add(new paraf("Paraf 1", "An-Naas ~ Al-Kautsar"));
        parafArrayList.add(new paraf("Paraf 2", "Al-Maa'un ~ Al-Takatsur"));
        parafArrayList.add(new paraf("Paraf 3", "Al-Qaari'ah ~ Al-'Alaq"));
        parafArrayList.add(new paraf("Paraf 4", "At-Tiin ~ Al-Balad"));
        parafArrayList.add(new paraf("Paraf 5", "An-Fajr ~ At-Thaariq"));
        parafArrayList.add(new paraf("Paraf 6", "Al-Buruuj ~ Al-Infithar"));
        parafArrayList.add(new paraf("Paraf 7", "At-Takwiir ~ An-Naba'"));
        parafArrayList.add(new paraf("Paraf 8", "Surah Al-Mulk"));
        parafArrayList.add(new paraf("Paraf 9", "Surah Ar-Rahmaan"));
        parafArrayList.add(new paraf("Paraf 10", "Surah Al-Waqi'ah"));
        parafArrayList.add(new paraf("Paraf 11", "Surah Yaasin"));
        parafArrayList.add(new paraf("Paraf 12", "Tahlil"));
    }
}

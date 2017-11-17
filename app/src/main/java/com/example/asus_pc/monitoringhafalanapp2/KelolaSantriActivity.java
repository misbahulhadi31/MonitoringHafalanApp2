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
import android.widget.ListView;
import android.widget.Toast;

public class KelolaSantriActivity extends AppCompatActivity {
    String[] daftar;
    ListView listView;
    Menu menu;
    protected Cursor cursor;
    DataSantri dbcenter;
    public static KelolaSantriActivity ka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_santri);

        ka = this;
        dbcenter = new DataSantri(this);
        RefreshList();
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
        listView = (ListView)findViewById(R.id.dataSantri);
        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        listView.setSelected(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2]; //.getItemAtPosition(arg2).toString();
                final CharSequence[] dialogitem = {"Ubah Data", "Hapus Data"};
                AlertDialog.Builder builder = new AlertDialog.Builder(KelolaSantriActivity.this);
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch(item){
                            case 0 :
                                Intent in = new Intent(getApplicationContext(), UbahDataActivity.class);
                                in.putExtra("namaSantri", selection);
                                startActivity(in);
                                break;
                            case 1 :
                                AlertDialog.Builder ab = new AlertDialog.Builder(KelolaSantriActivity.this);
                                ab.setMessage("Yakin Untuk Menghapus ?")
                                        .setCancelable(false)
                                        .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                SQLiteDatabase db = dbcenter.getWritableDatabase();
                                                db.execSQL("delete from santri where namaSantri = '"+selection+"'");
                                                RefreshList();
                                                Toast.makeText(getApplicationContext(), "Data Berhasil Dihapus", Toast.LENGTH_LONG).show();
                                            }
                                        })
                                        .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alert = ab.create();
                                alert.show();
                                break;
                        }
                    }
                });
                builder.create().show();
            }});
        ((ArrayAdapter)listView.getAdapter()).notifyDataSetInvalidated();
    }
}

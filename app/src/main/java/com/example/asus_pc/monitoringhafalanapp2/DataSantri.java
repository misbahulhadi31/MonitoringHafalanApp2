package com.example.asus_pc.monitoringhafalanapp2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ASUS-PC on 24/07/2017.
 */

public class DataSantri extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dataSantri.db";
    private static final int DATABASE_VERSION = 1;
    public DataSantri(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String sql = "create table santri(noInduk integer primary key, " +
                "namaSantri text null, " +
                "kelas text null, " +
                "konsulat text null, " +
                "namaWali text null, " +
                "noTelp integer null);";
        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);

        sql = "insert into santri (noInduk, namaSantri, kelas, konsulat, namaWali, noTelp) VALUES ('24196', 'Annisa Cahya', '1 EXT F', 'Lampung','Rozak', '+6281312500197');";
        db.execSQL(sql);

        sql = "insert into santri (noInduk, namaSantri, kelas, konsulat, namaWali, noTelp) VALUES ('24055', 'Abdul Alam', '2 IPA A', 'Bandung','Budiman', '+6281987649983');";
        db.execSQL(sql);

        sql = "insert into santri (noInduk, namaSantri, kelas, konsulat, namaWali, noTelp) VALUES ('24073', 'Ratu Gita', '2 IPS A', 'Cilegon','Wahyu', '+6282199850942');";
        db.execSQL(sql);

        sql = "insert into santri (noInduk, namaSantri, kelas, konsulat, namaWali, noTelp) VALUES ('23944', 'Fahrul Ramzi', '3 IPA B', 'Tangerang','Kurniawan', '+6287825436678');";
        db.execSQL(sql);

        sql = "insert into santri (noInduk, namaSantri, kelas, konsulat, namaWali, noTelp) VALUES ('23995', 'Soleha Ayu', '3 IPS C', 'Jakarta','Jaman', '+6289887675542');";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }
}

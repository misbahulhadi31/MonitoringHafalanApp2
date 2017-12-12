package com.example.asus_pc.monitoringhafalanapp2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LihatDataActivity extends AppCompatActivity {
    @BindView(R.id.noView)
    TextView noView;
    @BindView(R.id.EditText1)
    TextView EditText1;
    @BindView(R.id.namaView)
    TextView namaView;
    @BindView(R.id.EditText2)
    TextView EditText2;
    @BindView(R.id.kelasView)
    TextView kelasView;
    @BindView(R.id.EditText3)
    TextView EditText3;
    @BindView(R.id.konsulatView)
    TextView konsulatView;
    @BindView(R.id.EditText4)
    TextView EditText4;
    @BindView(R.id.namaWaliView)
    TextView namaWaliView;
    @BindView(R.id.EditText5)
    TextView EditText5;
    @BindView(R.id.noTelpView)
    TextView noTelpView;
    @BindView(R.id.EditText6)
    TextView EditText6;
    @BindView(R.id.lihat)
    Button lihat;

    private Intent i;

    private static final String TAG = LihatDataActivity.class.getSimpleName();

    private String namaSantri,namaWali,noTelepon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data);
        ButterKnife.bind(this);

        i = getIntent();
        namaSantri = i.getStringExtra(Config.NAMA_SANTRI);
        namaWali = i.getStringExtra(Config.NAMA_WALI);
        noTelepon = i.getStringExtra(Config.NO_TELEPON);

        EditText1.setText(i.getStringExtra(Config.NOMOR_INDUK));
        EditText2.setText(namaSantri);
        EditText3.setText(i.getStringExtra(Config.KELAS));
        EditText4.setText(i.getStringExtra(Config.KONSULAT));
        EditText5.setText(namaWali);
        EditText6.setText(noTelepon);
        
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LihatDataActivity.this, ListHafalanActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.lihat)
    public void lihatHafalan(){
        Intent intent = new Intent(getApplicationContext(), ParafSantriActivity.class);
//        intent.putExtra(Config.SELESAI, i.getStringExtra(Config.SELESAI));
        intent.putExtra(Config.NO_PARAF, i.getStringExtra(Config.NO_PARAF));
        intent.putExtra(Config.NAMA_SURAH, i.getStringExtra(Config.NAMA_SURAH));
        intent.putExtra(Config.NAMA_SANTRI, namaSantri);
        intent.putExtra(Config.NAMA_WALI, namaWali);
        intent.putExtra(Config.NO_TELEPON, noTelepon);
        startActivity(intent);
    }
}

package com.example.asus_pc.monitoringhafalanapp2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data);
        ButterKnife.bind(this);

         i = getIntent();

        EditText1.setText(i.getStringExtra(config.NOMOR_INDUK));
        EditText2.setText(i.getStringExtra(config.NAMA_SANTRI));
        EditText3.setText(i.getStringExtra(config.KELAS));
        EditText4.setText(i.getStringExtra(config.KONSULAT));
        EditText5.setText(i.getStringExtra(config.NAMA_WALI));
        EditText6.setText(i.getStringExtra(config.NO_TELEPON));
        
    }

    @OnClick(R.id.lihat)
    public void lihatHafalan(){
        Intent intent = new Intent(getApplicationContext(), ParafSantriActivity.class);
        intent.putExtra(config.SELESAI, i.getStringExtra(config.SELESAI));
        intent.putExtra(config.NO_PARAF, i.getStringExtra(config.NO_PARAF));
        intent.putExtra(config.NAMA_SURAH, i.getStringExtra(config.NAMA_SURAH));
        intent.putExtra(config.NAMA_SANTRI, i.getStringExtra(config.NAMA_SANTRI));
        startActivity(intent);
    }
}

package com.example.asus_pc.monitoringhafalanapp2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.asus_pc.monitoringhafalanapp2.app.Config;

public class KirimPesanActivity extends AppCompatActivity {
    EditText santri,surah, wali, nomor, pesan;
    Button kirim;

    private Intent i;

    private static final String TAG = KirimPesanActivity.class.getSimpleName();

    private String namaSantri, namaWali,noTelepon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kirim_pesan);

        santri = (EditText) findViewById(R.id.editSantri);
        surah = (EditText) findViewById(R.id.editSurah);
        wali = (EditText) findViewById(R.id.editWali);
        nomor = (EditText) findViewById(R.id.editNomor);
        pesan = (EditText) findViewById(R.id.editPesan);
        kirim = (Button)findViewById(R.id.kirim);

        i = getIntent();
        namaSantri = i.getStringExtra(Config.NAMA_SANTRI);
        namaWali = i.getStringExtra(Config.NAMA_WALI);
        noTelepon = i.getStringExtra(Config.NO_TELEPON);
        //namaSurah = i.getStringExtra(Config.NAMA_SURAH);

        santri.setText(namaSantri);
        wali.setText(namaWali);
        nomor.setText(noTelepon);
        surah.setText(i.getStringExtra(Config.NO_PARAF));

        //pesan.setText(i.getStringExtra(Config.PESAN));

        pesan.setText("Assalamu'alaikum Bapak "+wali.getText().toString()+ "\n" +
                "Alhamduliilah anak bapak " +santri.getText().toString()+" telah menyelesaikan hafalan surah " +surah.getText().toString()+ "\n" +
                "Semoga anak bapak bisa mengamalkan hafalan Qur'annya serta dapat menjaga hafalannya, Amiin..");

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = pesan.getText().toString();
                String phoneNo = nomor.getText().toString();
                if(!TextUtils.isEmpty(message) && !TextUtils.isEmpty(phoneNo)) {
                    Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNo));
                    smsIntent.putExtra("sms_body", message);
                    startActivity(smsIntent);
                }
            }
        });
    }
}

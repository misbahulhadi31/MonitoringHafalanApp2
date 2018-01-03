package com.example.asus_pc.monitoringhafalanapp2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus_pc.monitoringhafalanapp2.app.Config;
import com.eyro.mesosfer.LogOutCallback;
import com.eyro.mesosfer.MesosferData;
import com.eyro.mesosfer.MesosferException;
import com.eyro.mesosfer.MesosferUser;

import java.util.List;
import java.util.Locale;

public class MainMenuActivity extends AppCompatActivity {
    ImageView tambah, monitor, list, pas;
    TextView buka;
    private long lastPressedTime;
    private static final int PERIOD = 2000;
    private ProgressDialog loading;
    private AlertDialog dialog;
    private List<MesosferData> listData;
    private MesosferData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        loading = new ProgressDialog(this);
        loading.setIndeterminate(true);
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);

        tambah = findViewById(R.id.tambah);
        monitor = findViewById(R.id.monitor);
        list = findViewById(R.id.list);
        pas = findViewById(R.id.passw);
        buka = findViewById(R.id.welcome);


        buka.setText("\"Sebaik-baik kalian adalah yang mempelajari Al-Qur'an dan mengajarkannya.\"[Al-Bukhari]");

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, TambahSantriActivity.class);
                startActivity(intent);
            }
        });

        monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, ListHafalanActivity.class);
                startActivity(intent);
            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, KelolaSantriActivity.class);
                startActivity(intent);
            }
        });

        pas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final MesosferData data =listData.get();
                Intent intent = new Intent(getApplicationContext(), KelolaPassActivity.class);
                //intent.putExtra("id", data.getObjectId());
                startActivity(intent);
            }
        });

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            switch (event.getAction()) {
                case KeyEvent.ACTION_DOWN:
                    if (event.getDownTime() - lastPressedTime < PERIOD) {
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Tekan sekali lagi untuk keluar dari Aplikasi.",
                                Toast.LENGTH_SHORT).show();
                        lastPressedTime = event.getEventTime();
                    }
                    return true;
            }
        }
        return false;
    }

    public void handleLogout(View view) {
        loading.setMessage("Logging out...");
        loading.show();
        MesosferUser.logOutAsync(new LogOutCallback() {
            @Override
            public void done(MesosferException e) {
                loading.dismiss();

                if (e != null) {
                    // setup alert dialog builder
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
                    builder.setNegativeButton(android.R.string.ok, null);
                    builder.setTitle("Log Out Error");
                    builder.setMessage(
                            String.format(Locale.getDefault(), "Error code: %d\nDescription: %s",
                                    e.getCode(), e.getMessage())
                    );
                    dialog = builder.show();
                }

                Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.logout) {
            SharedPreferenceManager.getInstance(getApplicationContext()).removeNamaPemaraf();
            Intent mainIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(mainIntent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}

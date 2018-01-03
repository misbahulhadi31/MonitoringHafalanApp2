package com.example.asus_pc.monitoringhafalanapp2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus_pc.monitoringhafalanapp2.app.Config;
import com.example.asus_pc.monitoringhafalanapp2.app.SessionManager;
import com.eyro.mesosfer.FindCallback;
import com.eyro.mesosfer.LogInCallback;
import com.eyro.mesosfer.MesosferData;
import com.eyro.mesosfer.MesosferException;
import com.eyro.mesosfer.MesosferQuery;
import com.eyro.mesosfer.MesosferUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    private EditText textEmail,textPass, hak;
    private Button regis,submit;
    private ProgressDialog loading;
    private AlertDialog dialog;
    private SessionManager session;
    private Button masuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());

        textEmail = (EditText) findViewById(R.id.email);
        textPass = (EditText) findViewById(R.id.pass);
        regis = (Button) findViewById(R.id.daftar);
        masuk = (Button) findViewById(R.id.masuk);

        loading = new ProgressDialog(this);
        loading.setIndeterminate(true);
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog1 = new AlertDialog.Builder(LoginActivity.this);
                View view = getLayoutInflater().inflate(R.layout.akses,null);
                hak = (EditText)view.findViewById(R.id.akses);
                submit = (Button)view.findViewById(R.id.submit);
                dialog1.setView(view);

                final AlertDialog dialog2 = dialog1.create();
                dialog2.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (hak.getText().toString().equals("FURIOUS39")) {
                            Toast.makeText(LoginActivity.this, "Akses diterima", Toast.LENGTH_SHORT).show();
                            dialog2.dismiss();
                            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                        }
                        else if(hak.getText().toString().isEmpty())  {
                            Toast.makeText(LoginActivity.this, "Masukkan kode akses", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(LoginActivity.this, "Akses ditolak", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isInputValid()) {
                    // return if there is an invalid input
                    return;
                }
                checkLogin();
            }
        });
    }

    private void checkLogin() {
        final String strWhereEmail = textEmail.getText().toString();
        final String strWherePass = textPass.getText().toString();
        MesosferQuery<MesosferData> query = MesosferData.getQuery("Ustad");
        query.whereEqualTo("email", strWhereEmail);
        query.whereEqualTo("password", strWherePass);

        query.findAsync(new FindCallback<MesosferData>() {
            @Override
            public void done(List<MesosferData> list, MesosferException e) {
                if (list.size() == 0) {
                    Toast.makeText(LoginActivity.this, "Email atau Password salah", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                    String dataArray = Arrays.toString(list.toArray());
                    Intent intent = new Intent(getApplicationContext(), KelolaPassActivity.class);
                    try {
                        JSONArray jsonArray = new JSONArray(dataArray);
                        JSONObject row = jsonArray.getJSONObject(0);
                        String namaPemaraf = row.getString("namaUstad");
                        Log.d("object id", "" + row.getString("objectId"));
                        SharedPreferenceManager.getInstance(getApplicationContext()).setNamaPemaraf(namaPemaraf);
                        SharedPreferenceManager.getInstance(getApplicationContext()).setObjectId(row.getString("objectId"));
                        SharedPreferenceManager.getInstance(getApplicationContext()).setEmail(row.getString("email"));
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
                }
            }
        });
    }

    private boolean isInputValid() {
        // validating all input values if it is empty
        if (TextUtils.isEmpty(textEmail.getText().toString())) {
            Toast.makeText(this, "Masukkan email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(textPass.getText().toString())) {
            Toast.makeText(this, "Masukkan password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}

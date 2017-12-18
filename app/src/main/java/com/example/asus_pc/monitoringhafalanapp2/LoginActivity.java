package com.example.asus_pc.monitoringhafalanapp2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus_pc.monitoringhafalanapp2.app.SessionManager;
import com.eyro.mesosfer.LogInCallback;
import com.eyro.mesosfer.MesosferException;
import com.eyro.mesosfer.MesosferUser;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    private EditText textEmail,textPass, hak;
    private Button regis,submit;
    private ProgressDialog loading;
    private AlertDialog dialog;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());

        textEmail = (EditText) findViewById(R.id.email);
        textPass = (EditText) findViewById(R.id.pass);
        regis = (Button) findViewById(R.id.daftar);

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
                        if (hak.getText().toString().equals("DARQO3")) {
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
    }

    public void handleLogin(View view) {
        final String username = textEmail.getText().toString();
        String password = textPass.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Masukkan Email ", Toast.LENGTH_LONG).show();
            textEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Masukkan Password ", Toast.LENGTH_LONG).show();
            textPass.requestFocus();
            return;
        }

        loading.setMessage("Logging in...");
        loading.show();
        MesosferUser.logInAsync(username, password, new LogInCallback() {
            @Override
            public void done(MesosferUser user, MesosferException e) {
                loading.dismiss();
                if (e != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Login Failed");
                    builder.setMessage(
                            String.format(Locale.getDefault(), "Error code: %d\nDescription: %s",
                                    e.getCode(), e.getMessage())
                    );

                    dialog = builder.show();
                    return;
                }

                Toast.makeText(LoginActivity.this, "User logged in...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                startActivity(intent);

                session.createLoginSession(username);
                finish();
                //startActivity(new Intent(LoginActivity.this, CommentActivity.class));
            }
        });
    }
}

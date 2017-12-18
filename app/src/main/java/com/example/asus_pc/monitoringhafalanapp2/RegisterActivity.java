package com.example.asus_pc.monitoringhafalanapp2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.eyro.mesosfer.MesosferException;
import com.eyro.mesosfer.MesosferUser;
import com.eyro.mesosfer.RegisterCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {
    EditText textEmail, textPass, textNama;
    private ProgressDialog loading;
    private AlertDialog dialog;
    private String email, pass, nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textEmail = (EditText) findViewById(R.id.email);
        textPass = (EditText) findViewById(R.id.pass);
        textNama = (EditText) findViewById(R.id.lengkap);

        loading = new ProgressDialog(this);
        loading.setIndeterminate(true);
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
    }

    public void handleRegister(View view) {
        // get all value from input
        email = textEmail.getText().toString();
        pass = textPass.getText().toString();
        nama = textNama.getText().toString();

        // validating input values
        if (!isInputValid()) {
            // return if there is an invalid input
            return;
        }

        registerUser();
    }

    private boolean isInputValid() {
        // validating all input values if it is empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Masukkan Email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Masukkan Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(nama)) {
            Toast.makeText(this, "Masukkan Nama Lengkap", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void registerUser() {
        // showing a progress dialog loading
        loading.setMessage("Registering...");
        loading.show();

        // create new instance of Mesosfer User
        MesosferUser newUser = MesosferUser.createUser();
        // set default field
        newUser.setEmail(email);
        newUser.setPassword(pass);
        newUser.setFirstName(nama);
        //newUser.setLastName(lastname);
        // set custom field
        // execute register user asynchronous
        newUser.registerAsync(new RegisterCallback() {
            @Override
            public void done(MesosferException e) {
                // hide progress dialog loading
                loading.dismiss();

                // setup alert dialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setNegativeButton(android.R.string.ok, null);
                Toast.makeText(getApplicationContext(), "Data berhasil ditambah", Toast.LENGTH_SHORT).show();

                // check if there is an exception happen
                if (e != null) {
                    builder.setTitle("Error Happen");
                    builder.setMessage(
                            String.format(Locale.getDefault(), "Error code: %d\nDescription: %s",
                                    e.getCode(), e.getMessage())
                    );
                    dialog = builder.show();
                    return;
                }
            }
        });

        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
}

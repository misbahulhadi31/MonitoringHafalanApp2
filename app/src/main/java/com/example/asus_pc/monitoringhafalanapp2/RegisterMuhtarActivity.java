package com.example.asus_pc.monitoringhafalanapp2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.eyro.mesosfer.FindCallback;
import com.eyro.mesosfer.MesosferData;
import com.eyro.mesosfer.MesosferException;
import com.eyro.mesosfer.MesosferQuery;

import java.util.List;

public class RegisterMuhtarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_muhtar);

        updateAndShowDataList();
    }

    private void updateAndShowDataList() {
        final String strWhere = "jos@gmail.com";
        final String strWherePass = "123";
        MesosferQuery<MesosferData> query = MesosferData.getQuery("Ustad");
        query.whereEqualTo("email", strWhere);
        query.whereEqualTo("password", strWherePass);

        query.findAsync(new FindCallback<MesosferData>() {
            @Override
            public void done(List<MesosferData> list, MesosferException e) {

                if (e != null) {
                    Toast.makeText(RegisterMuhtarActivity.this, "gagal fetch data login", Toast.LENGTH_SHORT).show();
                }
                if (list.size() == 0) {
                    Toast.makeText(RegisterMuhtarActivity.this, "data tidak ada", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterMuhtarActivity.this, "data ada", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

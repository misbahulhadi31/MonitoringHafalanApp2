package com.example.asus_pc.monitoringhafalanapp2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.asus_pc.monitoringhafalanapp2.app.Config;
import com.eyro.mesosfer.MesosferData;
import com.eyro.mesosfer.MesosferException;
import com.eyro.mesosfer.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class AddParafActivity extends AppCompatActivity {
    private Spinner spinnerParaf;
    private Button btnSave;

    private String spinnerValue;
    private String surahValue;
    private String parafValue;
    private static final String TAG = AddParafActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_paraf);

        spinnerParaf = findViewById(R.id.spinner_paraf);
        btnSave = findViewById(R.id.btn_save);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Tambah Paraf");

        Log.d(TAG,"get data " + SharedPrefManager.getInstance(getApplicationContext()).getData());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.paraf, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerParaf.setAdapter(adapter);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addParaf();
            }
        });
    }

    private void addParaf() {
        Intent intent = getIntent();

        try {
            JSONObject jsonObject = new JSONObject(SharedPrefManager.getInstance(getApplicationContext()).getData());
            parafValue = jsonObject.getString(Config.NO_PARAF);
            Log.d(TAG, "paraf object " + parafValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "paraf val "+ parafValue);

        spinnerValue = String.valueOf(spinnerParaf.getSelectedItem());

        switch (spinnerValue) {
            case "001. Al-Fatihah":
                surahValue = "7 ayat";
                break;
            case "002. Al-Baqarah":
                surahValue = "286 ayat";
                break;
            case "003. Ali-Imran":
                surahValue = "200 ayat";
                break;
            case "004. An-Nisaa":
                surahValue = "176 ayat";
                break;
            case "005. Al-Maidah":
                surahValue = "120 ayat";
                break;
            case "006. Al-An'am":
                surahValue = "165 ayat";
                break;
            case "007. Al-A'raf":
                surahValue = "206 ayat";
                break;
            case "008. Al-Anfal":
                surahValue = "75 ayat";
                break;
            case "009. At-Tauba":
                surahValue = "129 ayat";
                break;
            case "010. Yunus":
                surahValue = "109 ayat";
                break;
            case "011. Hud":
                surahValue = "123 ayat";
                break;
            case "012. Yusuf":
                surahValue = "111 ayat";
                break;
            case "013. Ar-Ra'd":
                surahValue = "43 ayat";
                break;
            case "014. Ibrahim":
                surahValue = "42 ayat";
                break;
            case "015. Al-Hijr":
                surahValue = "99 ayat";
                break;
            case "016. An-Nahl":
                surahValue = "128 ayat";
                break;
            case "017. Al-Isra":
                surahValue = "111 ayat";
                break;
            case "018. Al-Kahfi":
                surahValue = "110 ayat";
                break;
            case "019. Maryam":
                surahValue = "98 ayat";
                break;
            case "020. Ta-ha":
                surahValue = "135 ayat";
                break;
            case "021. Al-Anbiyaa":
                surahValue = "112 ayat";
                break;
            case "022. Al-Hajj":
                surahValue = "78 ayat";
                break;
            case "023. Al-Muminun":
                surahValue = "118 ayat";
                break;
            case "024. An-Nur":
                surahValue = "64 ayat";
                break;
            case "025. Al-Furqan":
                surahValue = "77 ayat";
                break;
            case "026. Ash-Shu'araa":
                surahValue = "227 ayat";
                break;
            case "027. An-Naml":
                surahValue = "93 ayat";
                break;
            case "028. Al-Qashash":
                surahValue = "88 ayat";
                break;
            case "029. Al-Ankabut":
                surahValue = "69 ayat";
                break;
            case "030. Ar-Rum":
                surahValue = "60 ayat";
                break;
            case "031. Luqman":
                surahValue = "34 ayat";
                break;
            case "032. As-Sajdah":
                surahValue = "30 ayat";
                break;
            case "033. Al-Ahzab":
                surahValue = "73 ayat";
                break;
            case "034. Saba":
                surahValue = "54 ayat";
                break;
            case "035. Faathir":
                surahValue = "45 ayat";
                break;
            case "036. Ya-Sin":
                surahValue = "83 ayat";
                break;
            case "037. Ash-Shaffat":
                surahValue = "182 ayat";
                break;
            case "038. Sad":
                surahValue = "88 ayat";
                break;
            case "039. Az-Zumar":
                surahValue = "75 ayat";
                break;
            case "040. Al-Mu'min":
                surahValue = "85 ayat";
                break;
            case "041. Ha-Mim":
                surahValue = "54 ayat";
                break;
            case "042. Ash-Shura":
                surahValue = "53 ayat";
                break;
            case "043. Az-Zukhruf":
                surahValue = "89 ayat";
                break;
            case "044. Ad-Dukhan":
                surahValue = "59 ayat";
                break;
            case "045. Al-Jathiya":
                surahValue = "37 ayat";
                break;
            case "046. Al-Ahqaf":
                surahValue = "35 ayat";
                break;
            case "047. Muhammad":
                surahValue = "38 ayat";
                break;
            case "048. Al-Fat-h":
                surahValue = "29 ayat";
                break;
            case "049. Al-Hujurat":
                surahValue = "18 ayat";
                break;
            case "050. Qaaf":
                surahValue = "45 ayat";
                break;
            case "051. Az-Zariyat":
                surahValue = "60 ayat";
                break;
            case "052. At-Tur":
                surahValue = "49 ayat";
                break;
            case "053. An-Najm":
                surahValue = "62 ayat";
                break;
            case "054. Al-Qamar":
                surahValue = "55 ayat";
                break;
            case "055. Ar-Rahman":
                surahValue = "78 ayat";
                break;
            case "056. Al-Waaqi'ah":
                surahValue = "96 ayat";
                break;
            case "057. Al-Hadid":
                surahValue = "29 ayat";
                break;
            case "058. Al-Mujadila":
                surahValue = "22 ayat";
                break;
            case "059. Al-Hasyr":
                surahValue = "24 ayat";
                break;
            case "060. Al-Mumtahana":
                surahValue = "13 ayat";
                break;
            case "061. As-Shaff":
                surahValue = "14 ayat";
                break;
            case "062. Al-jumu'ah":
                surahValue = "11 ayat";
                break;
            case "063. Al-munafik":
                surahValue = "11 ayat";
                break;
            case "064. At-Tagabun":
                surahValue = "18 ayat";
                break;
            case "065. At-talaq":
                surahValue = "12 ayat";
                break;
            case "066. At-Tahrim":
                surahValue = "12 ayat";
                break;
            case "067. Al-Mulk":
                surahValue = "30 ayat";
                break;
            case "068. Al-Qalam":
                surahValue = "52 ayat";
                break;
            case "069. Al-aqqa":
                surahValue = "52 ayat";
                break;
            case "070. Al-Ma'arij":
                surahValue = "44 ayat";
                break;
            case "071. Nuh":
                surahValue = "28 ayat";
                break;
            case "072. Al-Jinn":
                surahValue = "28 ayat";
                break;
            case "073. Al-Muzzammil":
                surahValue = "20 ayat";
                break;
            case "074. Al-Muddathth":
                surahValue = "56 ayat";
                break;
            case "075. Al-Qiyamat":
                surahValue = "40 ayat";
                break;
            case "076. Ad-Dahr":
                surahValue = "31 ayat";
                break;
            case "077. Al-Mursalat":
                surahValue = "50 ayat";
                break;
            case "078. An-Nabaa":
                surahValue = "40 ayat";
                break;
            case "079. An-Nazi'at":
                surahValue = "46 ayat";
                break;
            case "080. Abasa":
                surahValue = "42 ayat";
                break;
            case "081. At-Takwir":
                surahValue = "29 ayat";
                break;
            case "082. Al-Infitar":
                surahValue = "19 ayat";
                break;
            case "083. Al-Mutaffife":
                surahValue = "36 ayat";
                break;
            case "084. Al-Inshiqaq":
                surahValue = "25 ayat";
                break;
            case "085. Al-Buruj":
                surahValue = "22 ayat";
                break;
            case "086. At-Tariq":
                surahValue = "17 ayat";
                break;
            case "087. Al-A'la":
                surahValue = "19 ayat";
                break;
            case "088. Al-Gashiya":
                surahValue = "26 ayat";
                break;
            case "089. Al-Fajr":
                surahValue = "30 ayat";
                break;
            case "090. Al-Balad":
                surahValue = "20 ayat";
                break;
            case "091. Ash-Shams":
                surahValue = "15 ayat";
                break;
            case "092. Al-Lail":
                surahValue = "21 ayat";
                break;
            case "093. Adz-Dhuha":
                surahValue = "11 ayat";
                break;
            case "094. Al-Syarh":
                surahValue = "8 ayat";
                break;
            case "095. At-Tin":
                surahValue = "8 ayat";
                break;
            case "096. Al-Alaq":
                surahValue = "19 ayat";
                break;
            case "097. Al-Qadr":
                surahValue = "5 ayat";
                break;
            case "098. Al-Baiyina":
                surahValue = "8 ayat";
                break;
            case "099. Al-Zalzalah":
                surahValue = "8 ayat";
                break;
            case "100. Al-Adiyat":
                surahValue = "11 ayat";
                break;
            case "101. Al-Qari'ah":
                surahValue = "11 ayat";
                break;
            case "102. At-Takathur":
                surahValue = "8 ayat";
                break;
            case "103. Al-Asr":
                surahValue = "3 ayat";
                break;
            case "104. Al-Humazah":
                surahValue = "9 ayat";
                break;
            case "105. Al-Fil":
                surahValue = "5 ayat";
                break;
            case "106. Quraish":
                surahValue = "4 ayat";
                break;
            case "107. Al-Ma'un":
                surahValue = "7 ayat";
                break;
            case "108. Al-Kautsar":
                surahValue = "3 ayat";
                break;
            case "109. Al-Kafirun ":
                surahValue = "6 ayat";
                break;
            case "110. An-Nashr":
                surahValue = "3 ayat";
                break;
            case "111. Al-Lahab":
                surahValue = "5 ayat";
                break;
            case "112. Al-Ikhlas":
                surahValue = "4 ayat";
                break;
            case "113. Al-Falaq":
                surahValue = "5 ayat";
                break;
            case "114. An-Nas":
                surahValue = "6 ayat";
                break;
            default:
                surahValue = "Zonk";
                break;
        }

        if (spinnerValue.equals(SharedPrefManager.getInstance(getApplicationContext()).getData())){
            Toast.makeText(this, "sama", Toast.LENGTH_SHORT).show();
        } else {
            MesosferData data = MesosferData.createData("ParafHafalan");

            data.setData(Config.NAMA_SANTRI, intent.getStringExtra(Config.NAMA_SANTRI));
            data.setData(Config.NAMA_SURAH, surahValue);
            data.setData(Config.NO_PARAF, spinnerValue);
            data.setData(Config.SELESAI, "0");

            data.saveAsync(new SaveCallback() {
                @Override
                public void done(MesosferException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setNegativeButton(android.R.string.ok, null);
                    // check if there is an exception happen
                    if (e != null) {
                        builder.setTitle("Error Happen");
                        builder.setMessage(
                                String.format(Locale.getDefault(), "Error code: %d\nDescription: %s",
                                        e.getCode(), e.getMessage())
                        );
                        return;
                    }
                    //Toast.makeText(getApplicationContext(), "Tambah paraf berhasil", Toast.LENGTH_SHORT).show();
                    //Intent intent = getIntent();
                    finish();
                    startActivity(new Intent(getApplicationContext(), ListHafalanActivity.class));
                }
            });
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}

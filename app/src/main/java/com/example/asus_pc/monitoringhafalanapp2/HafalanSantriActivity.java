package com.example.asus_pc.monitoringhafalanapp2;

import android.support.v7.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.asus_pc.monitoringhafalanapp2.app.Config;
import com.eyro.mesosfer.FindCallback;
import com.eyro.mesosfer.MesosferData;
import com.eyro.mesosfer.MesosferException;
import com.eyro.mesosfer.MesosferQuery;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */

public class HafalanSantriActivity extends Fragment {
    private ListView listView;
    private SimpleAdapter adapter;
    private ProgressDialog loading;
    private AlertDialog dialog;
    private List <Map<String, String>> mapDataList;
    private static final int[] to = new int[] { R.id.textNomor, R.id.texttNama};
    private static final String[] from = new String[] {"nomorInduk","namaSantri"};
    private List<MesosferData> listData;
    private TextView textViewEmpty;

    public HafalanSantriActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_hafalan_santri, container, false);

        mapDataList = new ArrayList<>();

        listView = (ListView) view.findViewById(R.id.dataSantri);
        textViewEmpty = (TextView)view.findViewById(R.id.emptyElement);
        adapter = new SimpleAdapter(getActivity(), mapDataList, R.layout.santri_list, from, to);

        listView.setAdapter(adapter);

        loading = new ProgressDialog(getActivity());
        loading.setIndeterminate(true);
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);

        updateAndShowDataList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final MesosferData data = listData.get(i);
                Intent intent = new Intent(getActivity(), LihatDataActivity.class);

                intent.putExtra(Config.NOMOR_INDUK, data.getDataString(Config.NOMOR_INDUK));
                intent.putExtra(Config.NAMA_SANTRI, data.getDataString(Config.NAMA_SANTRI));
                intent.putExtra(Config.KELAS, data.getDataString(Config.KELAS));
                intent.putExtra(Config.KONSULAT, data.getDataString(Config.KONSULAT));
                intent.putExtra(Config.NAMA_WALI, data.getDataString(Config.NAMA_WALI));
                intent.putExtra(Config.NO_TELEPON, data.getDataString(Config.NO_TELEPON));
                startActivity(intent);
            }
        });

        listView.setEmptyView(textViewEmpty);

        return view;
    }

    private void updateAndShowDataList() {
        MesosferQuery<MesosferData> query = MesosferData.getQuery("Santri");

        // showing a progress dialog loading
        loading.setMessage("Loading...");
        loading.show();

        query.findAsync(new FindCallback<MesosferData>() {
            @Override
            public void done(List<MesosferData> list, MesosferException e) {
                // hide progress dialog loading
                loading.dismiss();

                // check if there is an exception happen
                if (e != null) {
                    // setup alert dialog builder
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setNegativeButton(android.R.string.ok, null);
                    builder.setTitle("Error Happen");
                    builder.setMessage(
                            String.format(Locale.getDefault(), "Error code: %d\nDescription: %s",
                                    e.getCode(), e.getMessage())
                    );
                    dialog = builder.show();
                    return;
                }

                // clear all data list
                mapDataList.clear();
                listData = new ArrayList<>(list);
                for (final MesosferData data : list) {
                    Map<String, String> map = new HashMap<>();
                    map.put("nomorInduk", data.getDataString("noInduk"));
                    map.put("namaSantri", data.getDataString("namaSantri"));
                    try {
                        map.put("data", data.toJSON().toString(0));
                    } catch (JSONException e1) {
                        map.put("data", data.toJSON().toString());
                    }

                    mapDataList.add(map);
                }

                adapter.notifyDataSetChanged();
            }
        });
    }


}

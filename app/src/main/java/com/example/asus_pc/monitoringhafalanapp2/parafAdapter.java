package com.example.asus_pc.monitoringhafalanapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ASUS-PC on 31/10/2017.
 */

public class parafAdapter extends BaseAdapter{
    ArrayList<paraf> parafArrayList;
    Context context;

    public parafAdapter(ArrayList<paraf> parafArrayList, Context context) {
        this.parafArrayList = parafArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return parafArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return parafArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.paraf_list, parent, false);
        }

        paraf paraf = parafArrayList.get(position);

        TextView noParaf = (TextView) convertView.findViewById(R.id.listParaf);
        TextView nmSurah = (TextView) convertView.findViewById(R.id.listSurah);

        noParaf.setText(paraf.getNomorParaf());
        nmSurah.setText(paraf.getNamaSurah());


        return convertView;
    }
}

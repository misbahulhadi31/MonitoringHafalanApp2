package com.example.asus_pc.monitoringhafalanapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by muhtar on 11/27/17.
 */

public class CustomListAdapter extends BaseAdapter {
    private Context context; //context
    private ArrayList<ParafSantri> parafSantriArrayList; //data source of the list adapter

    //public constructor
    public CustomListAdapter(Context context, ArrayList<ParafSantri> parafSantriArrayList) {
        this.context = context;
        this.parafSantriArrayList = parafSantriArrayList;
    }

    @Override
    public int getCount() {
        return parafSantriArrayList.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return parafSantriArrayList.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.paraf_list, parent, false);
        }

        // get current item to be displayed
        ParafSantri parafSantri = (ParafSantri) getItem(position);

        // get the TextView for item name and item description
        TextView textViewParaf = (TextView)
                convertView.findViewById(R.id.listParaf);
        TextView textViewSurah = (TextView)
                convertView.findViewById(R.id.listSurah);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.listGambar);

        //sets the text for item name and item description from the current item object
        textViewParaf.setText(parafSantri.getNomorParaf());
        textViewSurah.setText(parafSantri.getNamaSurah());

        if (parafSantri.getSelesai().equals("0")){
            imageView.setImageResource(R.drawable.notyet);
        } else if (parafSantri.getSelesai().equals("1")){
            imageView.setImageResource(R.drawable.finish);
        } else {
            imageView.setImageResource(R.drawable.notyet);
        }

        // returns the view for the current row
        return convertView;
    }
}
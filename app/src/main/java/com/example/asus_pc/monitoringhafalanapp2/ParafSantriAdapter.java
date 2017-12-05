package com.example.asus_pc.monitoringhafalanapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by muhtar on 11/27/17.
 */

public class ParafSantriAdapter extends SimpleAdapter {
    private Context mContext;
    public LayoutInflater inflater = null;

    public ParafSantriAdapter(Context context,
                              List<? extends Map<String, ?>> data, int resource, String[] from,
                              int[] to) {
        super(context, data, resource, from, to);
        mContext = context;
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.paraf_list, null);

        HashMap<String, Object> data = (HashMap<String, Object>) getItem(position);

        return vi;
    }
}

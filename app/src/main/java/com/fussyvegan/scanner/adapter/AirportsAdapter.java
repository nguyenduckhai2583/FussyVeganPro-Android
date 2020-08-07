package com.fussyvegan.scanner.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fussyvegan.scanner.R;

import java.util.List;

public class AirportsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<String> nameAirports;
    private List<String> codeAirports;

    public AirportsAdapter(List<String> nameAirports, List<String> codeAirports){
        this.codeAirports = codeAirports;
        this.nameAirports = nameAirports;
    }
    @Override
    public int getCount() {
        return nameAirports.size();
    }

    @Override
    public Object getItem(int position) {
        return nameAirports.get(position)+", "+ codeAirports.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        View rowView = inflater.inflate(R.layout.item_airports, parent, false);
        Log.e("adapter", String.valueOf(nameAirports.size()));
        TextView nameAirport = rowView.findViewById(R.id.tv_name_airport);
        nameAirport.setText(nameAirports.get(position));
        TextView codeAirport = rowView.findViewById(R.id.tv_code_airport);
        codeAirport.setText(codeAirports.get(position));
        return rowView;
    }
}

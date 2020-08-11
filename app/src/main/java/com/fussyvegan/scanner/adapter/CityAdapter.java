package com.fussyvegan.scanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fussyvegan.scanner.R;

import java.util.List;

public class CityAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    List<String> city;
    List<String> listDes;

    public CityAdapter(List<String> city, List<String> des) {
        this.city = city;
        this.listDes = des;
    }

    @Override
    public int getCount() {
        return city.size();
    }

    @Override
    public Object getItem(int i) {
        return city.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null) {
            inflater = (LayoutInflater) viewGroup.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        View rowView = inflater.inflate(R.layout.city_item, viewGroup, false);
        TextView textView = rowView.findViewById(R.id.tvCity);
        TextView des = rowView.findViewById(R.id.tvDes);
        textView.setText(city.get(i));
        des.setText(listDes.get(i));
        return rowView;
    }
}

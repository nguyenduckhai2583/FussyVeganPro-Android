package com.fussyvegan.scanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fussyvegan.scanner.R;

import java.util.List;

public class CountryAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    List<String> settings;
    List<Integer> icLink;

    public CountryAdapter(List<String> settings, List<Integer> icLink) {
        this.settings = settings;
        this.icLink = icLink;
    }

    @Override
    public int getCount() {
        return settings.size();
    }

    @Override
    public Object getItem(int i) {
        return settings.get(i);
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
        View rowView = inflater.inflate(R.layout.item_choose_airline, viewGroup, false);
        TextView textView = rowView.findViewById(R.id.tv_name_airline);
        ImageView imgIcon = rowView.findViewById(R.id.img_ari_line);
        imgIcon.setImageResource(icLink.get(i));
        textView.setText(settings.get(i));
        return rowView;
    }
}

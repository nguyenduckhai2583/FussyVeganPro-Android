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

public class SettingAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    List<String> settings;
    List<Integer> icLink;

    public SettingAdapter(List<String> settings, List<Integer> icLink) {
        this.settings = settings;
        this.icLink = icLink;
    }

    @Override
    public int getCount() {
        return settings.size();
    }

    @Override
    public Object getItem(int position) {
        return settings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        View rowView = inflater.inflate(R.layout.setting_item, parent, false);
        TextView textView = rowView.findViewById(R.id.txvavgPrice);
        ImageView imgIcon = rowView.findViewById(R.id.imgIcon);
        imgIcon.setImageResource(icLink.get(position));
        textView.setText(settings.get(position));
        return rowView;
    }
}



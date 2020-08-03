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

public class AirlineAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    List<String> nameAriline;
    List<Integer> icLink;

    public AirlineAdapter(List<String> nameAriline, List<Integer> icLink){
        this.nameAriline = nameAriline;
        this.icLink = icLink;
    }

    @Override
    public int getCount() {
        return nameAriline.size();
    }

    @Override
    public Object getItem(int position) {
        return nameAriline.get(position);
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
        View rowView = inflater.inflate(R.layout.item_choose_airline, parent, false);
        TextView textView = rowView.findViewById(R.id.tv_name_airline);
        ImageView imgIcon = rowView.findViewById(R.id.img_ari_line);
        textView.setText(nameAriline.get(position));
        imgIcon.setImageResource(icLink.get(position));
        return  rowView;
    }
}

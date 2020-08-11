package com.fussyvegan.scanner.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.model.Resort;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ResortAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    List<Resort> resorts;
    private List<Integer> distanceList;

    public ResortAdapter(List<Resort> resorts,  List<Integer> distanceList){
        this.resorts = resorts;
        this.distanceList = distanceList;
    }
    @Override
    public int getCount() {
        return resorts.size();
    }

    @Override
    public Object getItem(int position) {
        return resorts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        View rowView = inflater.inflate(R.layout.item_restaurant, parent, false);

        ImageView imgResort = rowView.findViewById(R.id.imgImage);
        if (!resorts.get(position).getLink_photo_small().isEmpty()) {
            Picasso.get()
                    .load(resorts.get(position).getLink_photo())
                    .placeholder(R.drawable.ic_app_150)
                    .into(imgResort);
        }
        TextView tvName = rowView.findViewById(R.id.tvRestaurant);
        tvName.setText(resorts.get(position).getName());

        TextView tvLocation = rowView.findViewById(R.id.tvAddress);
        tvLocation.setText(resorts.get(position).getLocation());

        TextView tvTypeCusine = rowView.findViewById(R.id.tvTypeCusine);
        tvTypeCusine.setVisibility(View.GONE);

        TextView tvDistance= rowView.findViewById(R.id.tvDistance);
        tvDistance.setText(distanceList.get(position)+" km");

        return rowView;
    }
}

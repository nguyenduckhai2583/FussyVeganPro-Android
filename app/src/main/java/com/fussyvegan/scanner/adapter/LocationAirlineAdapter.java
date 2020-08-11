package com.fussyvegan.scanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.model.LocationAirport;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LocationAirlineAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<LocationAirport> locationAirports;
    private List<Integer> distanceList;

    public LocationAirlineAdapter(List<LocationAirport> locationAirports, List<Integer> distanceList){
        this.locationAirports = locationAirports;
        this.distanceList = distanceList;
    }
    @Override
    public int getCount() {
        return locationAirports.size();
    }

    @Override
    public Object getItem(int position) {
        return locationAirports.get(position);
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
        View rowView = inflater.inflate(R.layout.item_location, parent, false);
        ImageView imageView = rowView.findViewById(R.id.imgImage);
        if (!locationAirports.get(position).getLink_photo_small().isEmpty()) {
            Picasso.get()
                    .load(locationAirports.get(position).getLink_photo())
                    .placeholder(R.drawable.ic_app_150)
                    .into(imageView);
        }
        TextView nameLocation = rowView.findViewById(R.id.tvRestaurant);
        nameLocation.setText(locationAirports.get(position).getName());

        TextView location = rowView.findViewById(R.id.tvAddress);
        location.setText(locationAirports.get(position).getLocation());

        TextView cuisineType = rowView.findViewById(R.id.tvCuisineType);
        cuisineType.setText(locationAirports.get(position).getCuisine_type());

        TextView distance = rowView.findViewById(R.id.tvDistance);
        distance.setText(distanceList.get(position) + " km");



        return rowView;
    }
}

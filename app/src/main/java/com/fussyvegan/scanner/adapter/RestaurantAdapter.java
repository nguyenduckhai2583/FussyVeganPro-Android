package com.fussyvegan.scanner.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fussyvegan.scanner.OnRestaurantClickListener;
import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.model.restaurant.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.myHolder> {

    private List<Restaurant> list;
    private Context context;
    OnRestaurantClickListener listener;

    public RestaurantAdapter(List<Restaurant> list, Context context, OnRestaurantClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_restaurant, viewGroup, false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder myHolder, int i) {
        final Restaurant restaurant = list.get(i);
        Picasso.get().load(restaurant.getLink_photo_small()).into(myHolder.imgImage);
        myHolder.tvRestaurant.setText(restaurant.getName());
        myHolder.tvAddress.setText(restaurant.getLocation());
        myHolder.tvTypeCusine.setText(restaurant.getCuisine_type());
        myHolder.rlRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(restaurant);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class myHolder extends RecyclerView.ViewHolder {
        private ImageView imgImage;
        private TextView tvRestaurant, tvAddress, tvTypeCusine, tvDistance;
        private RelativeLayout rlRestaurant;
        public myHolder(@NonNull View itemView) {
            super(itemView);
            imgImage = itemView.findViewById(R.id.imgImage);
            tvRestaurant = itemView.findViewById(R.id.tvRestaurant);
            tvTypeCusine = itemView.findViewById(R.id.tvTypeCusine);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            rlRestaurant = itemView.findViewById(R.id.rlRestaurant);
        }
    }
}

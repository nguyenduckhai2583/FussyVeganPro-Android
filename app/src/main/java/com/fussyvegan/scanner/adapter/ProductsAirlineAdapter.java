package com.fussyvegan.scanner.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.model.ProductAirline;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductsAirlineAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    List<ProductAirline> mList = new ArrayList<>();
    Integer imageAirline;

    public ProductsAirlineAdapter(List<ProductAirline> mList, Integer imageAirline) {
        this.mList = mList;
        this.imageAirline = imageAirline;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
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
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.item_products_ari_line, parent, false);
        TextView nameProduct = rowView.findViewById(R.id.tv_name_product);
        TextView mealCode = rowView.findViewById(R.id.tv_meal_code);
        ImageView imgIcon = rowView.findViewById(R.id.img_ari_line);
        nameProduct.setText(mList.get(position).getMealName());
        mealCode.setText(mList.get(position).getMealCode());
        imgIcon.setImageResource(imageAirline);
        return rowView;
    }
}

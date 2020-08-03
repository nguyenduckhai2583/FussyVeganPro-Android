package com.fussyvegan.scanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.model.ProductAriline;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductsArilineAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    List<ProductAriline> mList = new ArrayList<>();

    public ProductsArilineAdapter(List<ProductAriline> mList){
        this.mList = mList;
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
        View rowView = inflater.inflate(R.layout.item_products_ari_line, parent, false);
        TextView nameProduct = rowView.findViewById(R.id.tv_name_product);
        TextView mealCode = rowView.findViewById(R.id.tv_meal_code);
        ImageView imgIcon = rowView.findViewById(R.id.img_ari_line);
        nameProduct.setText(mList.get(position).getMealName());
        mealCode.setText(mList.get(position).getMealCode());
        if (!mList.get(position).getAirlineLogo().isEmpty()) {
            Picasso.get()
                    .load(mList.get(position).getAirlineLogo())
                    .placeholder(R.drawable.ic_app_150)
                    .into(imgIcon);
        }
        return  rowView;
    }
}

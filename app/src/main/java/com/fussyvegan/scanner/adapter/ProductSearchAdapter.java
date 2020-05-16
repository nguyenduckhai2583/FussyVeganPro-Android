package com.fussyvegan.scanner.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.fussyvegan.scanner.model.Product;
import com.fussyvegan.scanner.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ProductSearchAdapter extends BaseAdapter implements Filterable {
    private LayoutInflater inflater;
    public List<Product> products = new ArrayList<>();
    public boolean isEdit;

    public ProductSearchAdapter(List<Product> products, boolean isEdit) {
        this.isEdit = isEdit;
    }

    public void updateData(List<Product> products) {
        this.products.clear();
        this.products.addAll(products);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (products != null) {
            return products.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        View rowView = inflater.inflate(R.layout.product_item, parent, false);
        TextView txvName = rowView.findViewById(R.id.txvName);
        ImageView imgProduct = rowView.findViewById(R.id.imgProduct);
        ImageView btnDelete = rowView.findViewById(R.id.btnDelete);
        ImageView imgPalm = rowView.findViewById(R.id.imgPalm);
        ImageView imgGmo = rowView.findViewById(R.id.imgGmo);
        ImageView imgGluten = rowView.findViewById(R.id.imgGluten);
        ImageView imgNut = rowView.findViewById(R.id.imgNut);
        ImageView imgSoy = rowView.findViewById(R.id.imgSoy);



        if (isEdit) {
            btnDelete.setVisibility(View.VISIBLE);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete(position);
                }
            });
        } else {
            btnDelete.setVisibility(View.GONE);
        }
        txvName.setText(products.get(position).getName());
        if (products.get(position).getVeganStatus().equals("VEGAN")) {
            txvName.setTextColor(Color.parseColor("#55AA44"));
        } else if (products.get(position).getVeganStatus().equals("CAUTION")) {
            txvName.setTextColor(Color.parseColor("#F3AF22"));
        } else if (products.get(position).getVeganStatus().equals("NOT VEGAN")) {
            txvName.setTextColor(Color.parseColor("#BE2813"));
        }

        Picasso.get().cancelRequest(imgProduct);
        if (!products.get(position).getLinkPhoto().isEmpty()) {
            Picasso.get()
                    .load(products.get(position).getLinkPhoto())

                    .placeholder(R.drawable.ic_app_150)
                    .into(imgProduct);
        }

        if (!products.get(position).getlinkPalm().isEmpty()) {
            Picasso.get()
                    .load(products.get(position).getlinkPalm())
                    .placeholder(R.drawable.ic_palm_unknown)
                    .into(imgPalm);
        }

        if (!products.get(position).getlinkGmo().isEmpty()) {
            Picasso.get()
                    .load(products.get(position).getlinkGmo())
                    .placeholder(R.drawable.ic_gmo_unknown)
                    .into(imgGmo);
        }

        if (!products.get(position).getlinkGluten().isEmpty()) {
            Picasso.get()
                    .load(products.get(position).getlinkGluten())
                    .placeholder(R.drawable.ic_gluten_unknown)
                    .into(imgGluten);
        }

        if (!products.get(position).getlinkNut().isEmpty()) {
            Picasso.get()
                    .load(products.get(position).getlinkNut())
                    .placeholder(R.drawable.ic_nut_unknown)
                    .into(imgNut);
        }

        if (!products.get(position).getlinkSoy().isEmpty()) {
            Picasso.get()
                    .load(products.get(position).getlinkSoy())
                    .placeholder(R.drawable.ic_soy_unknown)
                    .into(imgSoy);
        }



        return rowView;
    }


    public void delete(int position) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<Product> product = realm.where(Product.class).equalTo("id", products.get(position).getId()).findAll();
        ;
        product.deleteAllFromRealm();
        realm.commitTransaction();
        products.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                products = (List<Product>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                ArrayList<Product> filterProducts = new ArrayList<>();
                constraint = constraint.toString().toLowerCase();
                for (int i = 0; i < products.size(); i++) {
                    Product product = products.get(i);
                    if (product.getName().toLowerCase().contains(constraint.toString())) {
                        filterProducts.add(product);
                    }
                }

                results.count = filterProducts.size();
                results.values = filterProducts;

                return results;
            }
        };

        return filter;
    }

}

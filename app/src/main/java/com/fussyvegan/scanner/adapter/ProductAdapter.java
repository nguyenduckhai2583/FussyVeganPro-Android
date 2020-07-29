package com.fussyvegan.scanner.adapter;

import android.app.AppComponentFactory;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatRatingBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fussyvegan.scanner.APIInterface;
import com.fussyvegan.scanner.APILoginClient;
import com.fussyvegan.scanner.SearchFragment;
import com.fussyvegan.scanner.model.Product;
import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.model.ProductReview;
import com.fussyvegan.scanner.model.accountFlow.Reviews;
import com.fussyvegan.scanner.utils.SharedPrefs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fussyvegan.scanner.utils.Constant.ACCESS_TOKEN;

public class ProductAdapter extends BaseAdapter implements Filterable {
    private LayoutInflater inflater;
    public List<Product> products;
    public boolean isEdit;
    public boolean IngredientsFragment;
    public AppCompatRatingBar rb_AveRating;
    public TextView tvSumRating;
    ImageView imgVeganStatus;
    LinearLayout llIconsProductInfo;
    RelativeLayout llVeganStatus;
    LinearLayout llRate;
    public ProductAdapter(List<Product> products, boolean isEdit) {
        this.products = products;
        this.isEdit = isEdit;
        Log.e("product", "product adapter");
    }



    public ProductAdapter(boolean IngredientsFragment, List<Product> products, boolean isEdit) {
        this.products = products;
        this.isEdit = isEdit;
        this.IngredientsFragment = IngredientsFragment;
        Log.e("product", String.valueOf(IngredientsFragment));

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

        rb_AveRating = rowView.findViewById(R.id.rb_AveRating);
        tvSumRating = rowView.findViewById(R.id.tvSumRating);
        imgVeganStatus = rowView.findViewById(R.id.imgVeganstatus);
        llIconsProductInfo = rowView.findViewById(R.id.iconsProductInfo);
        llVeganStatus = rowView.findViewById(R.id.linearLayoutVeganStatus);
        llRate = rowView.findViewById(R.id.linearLayoutRate);
        if(!IngredientsFragment){
            //llIconsProductInfo.setVisibility(View.GONE);
            imgVeganStatus.setVisibility(View.VISIBLE);
            llRate.setVisibility(View.VISIBLE);
            //llRate.setVisibility(View.GONE);
        }
        //llRate.setVisibility(View.VISIBLE);


        if (products.get(position).getVeganStatus().equals("VEGAN")) {
            imgVeganStatus.setImageResource(R.drawable.vegan);
        }else if(products.get(position).getVeganStatus().equals("NOT VEGAN")) imgVeganStatus.setImageResource(R.drawable.notvegan);
        else imgVeganStatus.setImageResource(R.drawable.caution);

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
        if((int)products.get(position).getCountRatting()==0) tvSumRating.setText("No" + " Rating");
        else if((int)products.get(position).getCountRatting()==1)tvSumRating.setText("1" + " Rating");
        else tvSumRating.setText((int)products.get(position).getCountRatting() + " Ratings");
        rb_AveRating.setRating(products.get(position).getAvgRating());

        Picasso.get().cancelRequest(imgProduct);
        if (!products.get(position).getLinkPhoto().isEmpty()) {
            Picasso.get()
                    .load(products.get(position).getLinkPhoto())

                    .placeholder(R.drawable.ic_app_150)
                    .into(imgProduct);
        }

        return rowView;
    }


    public void delete(int position) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<Product> product = realm.where(Product.class).equalTo("id", products.get(position).getId()).findAll();
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

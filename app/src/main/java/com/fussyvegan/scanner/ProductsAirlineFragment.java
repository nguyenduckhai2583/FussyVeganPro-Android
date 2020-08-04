package com.fussyvegan.scanner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fussyvegan.scanner.activity.MainActivity;
import com.fussyvegan.scanner.activity.ProductAirlineDetailActivity;
import com.fussyvegan.scanner.activity.ProductDetailActivity;
import com.fussyvegan.scanner.adapter.ProductsAirlineAdapter;
import com.fussyvegan.scanner.model.ProductAirline;
import com.fussyvegan.scanner.model.ResourceProductAirline;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsAirlineFragment extends Fragment {

    private static final String NAME_AIRLINE_GET_PRODUCT = "name airline get products";
    private static final String NAME_OF_AIRLINE = "name airline";
    private static final String ARG_COLUMN_COUNT = "column-count";
    APIInterface apiInterface;
    private Integer mImageAirline = 1;
    private OnListFragmentInteractionListener mListener;
    List<ProductAirline> productAirlines;
    //ProductsArilineAdapter adapter;
    private TextView countProduct;
    ListView listView;
    MainActivity activity;

    private String mNameAirlineGetProduct;
    private String mNameAirline;

    public ProductsAirlineFragment() {
        productAirlines = new ArrayList<>();
    }
    public static ProductsAirlineFragment newInstance(String param1, String param2) {
        ProductsAirlineFragment fragment = new ProductsAirlineFragment();
        Bundle args = new Bundle();
        args.putString(NAME_AIRLINE_GET_PRODUCT, param1);
        args.putString(NAME_OF_AIRLINE, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNameAirlineGetProduct = getArguments().getString(NAME_AIRLINE_GET_PRODUCT);
            mNameAirline = getArguments().getString(NAME_OF_AIRLINE);
        }
        Log.e("NameAirlineGetProduct", mNameAirlineGetProduct);
        Log.e("NameAirline: ", mNameAirline);
        activity = (MainActivity) this.getActivity();
        getImageAirline();
        fetchProducts(mNameAirlineGetProduct);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products_air_line, container, false);
        TextView nameAirline = view.findViewById(R.id.tv_name_airline);
        Log.e("here", "here");
        nameAirline.setText(mNameAirline);
        countProduct = view.findViewById(R.id.tv_number_items);
        final ProductsAirlineAdapter adapter = new ProductsAirlineAdapter(productAirlines, mImageAirline);
        listView = view.findViewById(R.id.list_view_products);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > -1) {
                    Intent intent = new Intent(getActivity(), ProductAirlineDetailActivity.class);
                    Log.e("Product", productAirlines.get(position).toString());
                    intent.putExtra("product airline", productAirlines.get(position));
                    intent.putExtra("image airline", mImageAirline);
                    startActivity(intent);
                }
            }
        });
        activity.showNothing();
        activity.visibleBackItem(false);
        activity.invalidateOptionsMenu();
        return view;
    }

    public void fetchProducts(String keyword) {
        Log.e("Keyword", keyword);
        apiInterface = APIAirline.getClient().create(APIInterface.class);
        Call<ResourceProductAirline> call = null;
        call = apiInterface.getProductAirline(keyword);
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        dialog.show();
        call.enqueue(new Callback<ResourceProductAirline>() {
            @Override
            public void onResponse(Call<ResourceProductAirline> call, Response<ResourceProductAirline> response) {
                dialog.dismiss();
                Log.d("TAG","status: " + response.code());

                ResourceProductAirline resource = response.body();
               // Log.d("TAG","body: " + response.body().toString());
                productAirlines.clear();
                productAirlines = resource.getProducts();
                Log.e("TAG", productAirlines.toString());
                countProduct.setText("NUMBER OF ITEMS FOUND: "+ productAirlines.size());
                ProductsAirlineAdapter adapter =  new ProductsAirlineAdapter(productAirlines, mImageAirline);
                listView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<ResourceProductAirline> call, Throwable t) {
                    Log.e("fail", t.getMessage());
            }

        });
    }

    private void getImageAirline(){
        if (mNameAirlineGetProduct.equals("canada"))
            mImageAirline = R.drawable.air_canada;
        else if (mNameAirlineGetProduct.equals("air_china"))
            mImageAirline = R.drawable.air_china ;
        else if (mNameAirlineGetProduct.equals("france"))
            mImageAirline = R.drawable.air_france;
        else if (mNameAirlineGetProduct.equals("new_zealand"))
            mImageAirline = R.drawable.air_newzealand;
        else if (mNameAirlineGetProduct.equals("american"))
            mImageAirline = R.drawable.american;
        else if (mNameAirlineGetProduct.equals("british"))
            mImageAirline = R.drawable.british_airways;
        else if (mNameAirlineGetProduct.equals("cathay_pacific"))
            mImageAirline = R.drawable.cathay_pacific;
        else if (mNameAirlineGetProduct.equals("china_southern"))
            mImageAirline = R.drawable.china_southern;
        else if (mNameAirlineGetProduct.equals("delta"))
            mImageAirline = R.drawable.delta;
        else if (mNameAirlineGetProduct.equals("emirates"))
            mImageAirline = R.drawable.emirates;
        else if (mNameAirlineGetProduct.equals("etihad"))
            mImageAirline = R.drawable.etihad;
        else if (mNameAirlineGetProduct.equals("fiji"))
            mImageAirline = R.drawable.fiji_airways;
        else if (mNameAirlineGetProduct.equals("garuda"))
            mImageAirline = R.drawable.garuda;
        else if (mNameAirlineGetProduct.equals("japan"))
            mImageAirline = R.drawable.japan_airlines;
        else if (mNameAirlineGetProduct.equals("jetstar"))
            mImageAirline = R.drawable.jetstar;
        else if (mNameAirlineGetProduct.equals("malaysia"))
            mImageAirline = R.drawable.malaysia;
        else if (mNameAirlineGetProduct.equals("qantas"))
            mImageAirline = R.drawable.qantas;
        else if (mNameAirlineGetProduct.equals("qatar"))
            mImageAirline = R.drawable.qatar;
        else if (mNameAirlineGetProduct.equals("singapore"))
            mImageAirline = R.drawable.singapore;
        else if (mNameAirlineGetProduct.equals("south_african"))
            mImageAirline = R.drawable.south_african;
        else if (mNameAirlineGetProduct.equals("thai"))
            mImageAirline = R.drawable.thai;
        else if (mNameAirlineGetProduct.equals("united"))
            mImageAirline = R.drawable.united;
        else if (mNameAirlineGetProduct.equals("virgin_atlantic"))
            mImageAirline = R.drawable.virgin_atlantic;
        else if (mNameAirlineGetProduct.equals("virgin_australia"))
            mImageAirline = R.drawable.virgin_australia;
        else mImageAirline = R.drawable.vegan;
    }
}
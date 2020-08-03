package com.fussyvegan.scanner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.fussyvegan.scanner.activity.MainActivity;
import com.fussyvegan.scanner.adapter.ProductsArilineAdapter;
import com.fussyvegan.scanner.model.ProductAriline;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductsAirlineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductsAirlineFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String NAME_AIRLINE_GET_PRODUCT = "name airline get products";
    private static final String NAME_OF_AIRLINE = "name airline";
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    List<ProductAriline> productArilines = new ArrayList<>();
    ProductsArilineAdapter adapter;
    ListView listView;
    MainActivity activity;

    // TODO: Rename and change types of parameters
    private String mNameAirlineGetProduct;
    private String mNameAirline;

    public ProductsAirlineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment products_air_line.
     */
    // TODO: Rename and change types and number of parameters
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
        activity = (MainActivity) this.getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products_air_line, container, false);
        TextView nameAirline = view.findViewById(R.id.tv_name_airline);
        nameAirline.setText(mNameAirline);
        adapter = new ProductsArilineAdapter(productArilines);
        listView = view. findViewById(R.id.list_view_products);
        listView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }
}
package com.fussyvegan.scanner;

import android.annotation.SuppressLint;
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
import com.fussyvegan.scanner.adapter.AirlineAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AirportVeganOptionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AirportVeganOptionsFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String NAME_COUNTRY_AIRPORT= "name airport";

    List<String> nameAirline = new ArrayList<>();
    List<Integer> icLink = new ArrayList<>();
    private int mColumnCount = 1;
    AirlineAdapter adapter;
    ListView listView;
    MainActivity activity;


    public AirportVeganOptionsFragment() {
        nameAirline.add("Australia");
        nameAirline.add("New Zealand");
        nameAirline.add("USA");


        icLink.add(R.drawable.ic_australia);
        icLink.add(R.drawable.ic_newzealand);
        icLink.add(R.drawable.ic_usa);



    }

    String getCountry(int position) {
        switch (position) {
            case 0:
                return "Australia Airports";
            case 1:
                return "New Zealand Airports";
            case 2:
                return "USA Airports";
            default:
                return "Australia Airports";

        }

    }

    public static AirlineVeganOptions newInstance(int mColumnCount) {
        AirlineVeganOptions fragment = new AirlineVeganOptions();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, mColumnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        activity = (MainActivity) this.getActivity();

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_airline_vegan_options, container, false);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText("CHOOSE COUNTRY");
        adapter = new AirlineAdapter(nameAirline, icLink);
        listView = view. findViewById(R.id.list_view_airline);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG", position + "  click");
                String tag = "Airports";
                ProductsAirlineFragment fragment = new ProductsAirlineFragment();
                Bundle args = new Bundle();
                args.putString(NAME_COUNTRY_AIRPORT, getCountry(position));
                fragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();

            }
        });
        activity.showNothing();
        activity.visibleBackItem(false);
        activity.invalidateOptionsMenu();

        return view;
    }
}
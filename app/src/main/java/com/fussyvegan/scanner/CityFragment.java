package com.fussyvegan.scanner;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fussyvegan.scanner.activity.MainActivity;
import com.fussyvegan.scanner.activity.RestaurantActivity;
import com.fussyvegan.scanner.adapter.CityAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CityFragment extends Fragment {

    private static final String NAME_CITY = "City";
    private static final String NAME_COUNTRY = "Country";

    private String country;

    private TextView tvSearchCity;
    private CardView lyAllCities;

    private CityAdapter adapter;
    private List<String> listCity = new ArrayList<>();
    private List<String> listDes = new ArrayList<>();
    private ListView ltvCity;
    private MainActivity activity;

    public CityFragment() {
    }

    public static CityFragment newInstance() {
        return new CityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            country = getArguments().getString(NAME_COUNTRY);
            if (country != null) {
                Resources res = getResources();
                switch (country) {
                    case "Australia":
                        listCity.addAll(Arrays.asList(res.getStringArray(R.array.AUS_cities)));
                        listDes.addAll(Arrays.asList(res.getStringArray(R.array.AUS_cities_des)));
                        break;

                    case "New Zealand":
                        listCity.addAll(Arrays.asList(res.getStringArray(R.array.NEW_cities)));
                        listDes.addAll(Arrays.asList(res.getStringArray(R.array.NEW_cities_des)));
                        break;
                }
            }
        }

        activity = (MainActivity) this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_city, container, false);

        tvSearchCity = view.findViewById(R.id.tvSearchCity);
        String text = "Search All " + country;
        tvSearchCity.setText(text);

        lyAllCities = view.findViewById(R.id.lyAllCities);
        lyAllCities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, RestaurantActivity.class);
                intent.putExtra(NAME_CITY, "");
                intent.putExtra(NAME_COUNTRY, country);
                startActivity(intent);
            }
        });

        adapter = new CityAdapter(listCity, listDes);
        ltvCity = view.findViewById(R.id.ltvCity);
        ltvCity.setAdapter(adapter);

        ltvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(activity, RestaurantActivity.class);
                intent.putExtra(NAME_CITY, listCity.get(position));
                intent.putExtra(NAME_COUNTRY, "");
                startActivity(intent);
            }
        });

        activity.showNothing();
        activity.visibleBackItem(false);
        activity.invalidateOptionsMenu();

        return view;
    }
}
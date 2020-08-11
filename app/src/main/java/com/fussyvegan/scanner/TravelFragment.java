package com.fussyvegan.scanner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fussyvegan.scanner.activity.MainActivity;
import com.fussyvegan.scanner.adapter.TravelAdapter;

import java.util.ArrayList;
import java.util.List;

public class TravelFragment extends Fragment {

    private TravelAdapter adapter;
    private List<String> settings = new ArrayList<>();
    private List<Integer> icLink = new ArrayList<>();
    private ListView ltvSetting;
    private MainActivity activity;

    String tag = "";

    public TravelFragment() {
        settings.add("Airline Vegan Options");
        settings.add("Airport Vegan Options");
        settings.add("Vegan Friendly Resorts");
        settings.add("Search Food Chains");
        settings.add("Vegan Restaurants");

        icLink.add(R.drawable.ic_airline_64);
        icLink.add(R.drawable.ic_airport);
        icLink.add(R.drawable.ic_airline_64);
        icLink.add(R.drawable.ic_fastfood_96);
        icLink.add(R.drawable.ic_airline_64);
    }

    public static TravelFragment newInstance() {
        return new TravelFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (MainActivity) this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_travel, container, false);
        adapter = new TravelAdapter(settings, icLink);
        ltvSetting = view.findViewById(R.id.ltvTravel);
        ltvSetting.setAdapter(adapter);

        ltvSetting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadFragmentBy(position);
            }
        });

        activity.showNothing();
        activity.visibleBackItem(false);
        activity.invalidateOptionsMenu();

        return view;
    }

    public void loadFragmentBy(int type) {
        if (activity.findViewById(R.id.fragment_container) != null) {
            Fragment fragment = new Fragment();
            switch (type) {
                case 0:
                    fragment = new AirlineVeganOptions();
                    tag = "AirlineFragment";
                    break;
                case 1:
                    fragment = new AirportVeganOptionsFragment();
                    tag = "AirportFragment";
                    break;
                case 2:
                    fragment = new FastfoodFragment();
                    tag = "VeganFriendlyFragment";
                    break;
                case 3:
                    fragment = new FastfoodFragment();
                    tag = "FastfoodFragment";
                    break;
                case 4:
                    fragment = new CountryFragment();
                    tag = "VeganRestaurantFragment";
                    break;
            }
            // Add the fragment to the 'fragment_container' FrameLayout
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
        }
    }
}
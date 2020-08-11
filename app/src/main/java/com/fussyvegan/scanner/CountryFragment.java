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

public class CountryFragment extends Fragment {

    private static final String NAME_COUNTRY = "Country";

    private TravelAdapter adapter;
    private List<String> listCountry = new ArrayList<>();
    private List<Integer> icLink = new ArrayList<>();
    private ListView ltvCountry;
    private MainActivity activity;

    public CountryFragment() {
        listCountry.add("Australia");
        listCountry.add("New Zealand");

        icLink.add(R.drawable.ic_flag_australia);
        icLink.add(R.drawable.ic_newzealand);
    }

    public static CountryFragment newInstance() {
        return new CountryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (MainActivity) this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);
        adapter = new TravelAdapter(listCountry, icLink);
        ltvCountry = view.findViewById(R.id.ltvCountry);
        ltvCountry.setAdapter(adapter);

        ltvCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tag = "RestaurantFragment";
                CityFragment fragment = new CityFragment();
                Bundle args = new Bundle();
                args.putString(NAME_COUNTRY, listCountry.get(position));
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
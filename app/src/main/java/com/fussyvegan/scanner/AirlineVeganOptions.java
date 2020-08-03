package com.fussyvegan.scanner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fussyvegan.scanner.activity.MainActivity;
import com.fussyvegan.scanner.adapter.AirlineAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AirlineVeganOptions#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AirlineVeganOptions extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String NAME_AIRLINE_GET_PRODUCT= "name airline get products";
    private static final String NAME_OF_AIRLINE= "name airline";


    // TODO: Rename and change types of parameters
    List<String> nameAirline = new ArrayList<>();
    List<Integer> icLink = new ArrayList<>();
    private int mColumnCount = 1;
    AirlineAdapter adapter;
    ListView listView;
    MainActivity activity;


    public AirlineVeganOptions() {
        nameAirline.add("Air Canada");
        nameAirline.add("Air China");
        nameAirline.add("Air France");
        nameAirline.add("Air New Zealand");
        nameAirline.add("American Arilines");
        nameAirline.add("British Airways");
        nameAirline.add("Cathay Pacific");
        nameAirline.add("China Southern");
        nameAirline.add("Delta Arilines");
        nameAirline.add("Emirates");
        nameAirline.add("Etihad");
        nameAirline.add("Fiji Airways");
        nameAirline.add("Garuda Indonesia");
        nameAirline.add("Japan Airlines");
        nameAirline.add("Jetstar");
        nameAirline.add("Malaysia Airlines");
        nameAirline.add("QANTAS");
        nameAirline.add("Qatar Airways");
        nameAirline.add("Singapore Airlines");
        nameAirline.add("South African Airways");
        nameAirline.add("Thai Airways");
        nameAirline.add("United Airlines");
        nameAirline.add("Virgin Atlantic");
        nameAirline.add("Virgin Australia");

        icLink.add(R.drawable.air_canada);
        icLink.add(R.drawable.air_china);
        icLink.add(R.drawable.air_france);
        icLink.add(R.drawable.air_newzealand);
        icLink.add(R.drawable.american);
        icLink.add(R.drawable.british_airways);
        icLink.add(R.drawable.cathay_pacific);
        icLink.add(R.drawable.china_southern);
        icLink.add(R.drawable.delta);
        icLink.add(R.drawable.emirates);
        icLink.add(R.drawable.etihad);
        icLink.add(R.drawable.fiji_airways);
        icLink.add(R.drawable.garuda);
        icLink.add(R.drawable.japan_airlines);
        icLink.add(R.drawable.jetstar);
        icLink.add(R.drawable.malaysia);
        icLink.add(R.drawable.qantas);
        icLink.add(R.drawable.qatar);
        icLink.add(R.drawable.singapore);
        icLink.add(R.drawable.south_african);
        icLink.add(R.drawable.thai);
        icLink.add(R.drawable.united);
        icLink.add(R.drawable.virgin_atlantic);
        icLink.add(R.drawable.virgin_australia);


    }

    String getCountry(int position) {
        switch (position) {
            case 0:
                return "canada";
            case 1:
                return "china";
            case 2:
                return "france";
            case 3:
                return "new_zealand";
            case 4:
                return "american";
            case 5:
                return "british";
            case 6:
                return "cathay_pacific";
            case 7:
                return "china_southern";
            case 8:
                return "delta";
            case 9:
                return "emirates";
            case 10:
                return "etihad";
            case 11:
                return "fiji";
            case 12:
                return "garuda";
            case 13:
                return "japan";
            case 14:
                return "jetstar";
            case 15:
                return "malaysia";
            case 16:
                return "qantas";
            case 17:
                return "qatar";
            case 18:
                return "singapore";
            case 19:
                return "south_african";
            case 20:
                return "thai";
            case 21:
                return "united";
            case 22:
                return "american";
            case 23:
                return "virgin_atlantic";
            case 24:
                return "virgin_atlantic";
            default:
                return "virgin_australia";

        }

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AirlineVeganOptions.
     */
    // TODO: Rename and change types and number of parameters
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_airline_vegan_options, container, false);
        adapter = new AirlineAdapter(nameAirline, icLink);
        listView = view. findViewById(R.id.list_view_airline);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG", position + "  click");
                String tag = "Products Airline";
                ProductsAirlineFragment fragment = new ProductsAirlineFragment();
                Bundle args = new Bundle();
                args.putString(NAME_AIRLINE_GET_PRODUCT, getCountry(position));
                args.putString(NAME_OF_AIRLINE, nameAirline.get(position));
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
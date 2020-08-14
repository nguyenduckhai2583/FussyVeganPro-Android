package com.fussyvegan.scanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.fussyvegan.scanner.activity.MainActivity;
import com.fussyvegan.scanner.adapter.AirportsAdapter;

import java.util.ArrayList;
import java.util.List;


public class AirportsUSAFragment extends BaseFragment {

    private static final String NAME_COUNTRY_AIRPORT = "name airport";

    private String mNameAirport;
    List<String> nameAllAirports;
    List<String> codeAllAirports;


    MainActivity activity;


    public AirportsUSAFragment() {
        nameAllAirports = new ArrayList<>();
        codeAllAirports = new ArrayList<>();
        getAirports();
    }

    private void getAirports() {
        nameAllAirports.add("Atlanta");
        codeAllAirports.add("ATL");
        nameAllAirports.add("Charlotte");
        codeAllAirports.add("CLT");
        nameAllAirports.add("Chicago");
        codeAllAirports.add("ORD");
        nameAllAirports.add("Dallas/Fort Worth");
        codeAllAirports.add("DFW");
        nameAllAirports.add("Denver");
        codeAllAirports.add("DEN");
        nameAllAirports.add("Houston");
        codeAllAirports.add("IAH");
        nameAllAirports.add("Las Vegas");
        codeAllAirports.add("LAS");
        nameAllAirports.add("Los Angeles");
        codeAllAirports.add("LAX");
        nameAllAirports.add("Miami");
        codeAllAirports.add("MIA");
        nameAllAirports.add("Newark");
        codeAllAirports.add("EWR");
        nameAllAirports.add("New York City");
        codeAllAirports.add("JFK");
        nameAllAirports.add("Orlando");
        codeAllAirports.add("MCO");
        nameAllAirports.add("Phoenix");
        codeAllAirports.add("PHX");
        nameAllAirports.add("Wellington");
        codeAllAirports.add("DFW");

    }

    public static AirportsUSAFragment newInstance(String param1, String param2) {
        AirportsUSAFragment fragment = new AirportsUSAFragment();
        Bundle args = new Bundle();
        args.putString(NAME_COUNTRY_AIRPORT, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNameAirport = getArguments().getString(NAME_COUNTRY_AIRPORT);
        }
        activity = (MainActivity) this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_airports_u_s_a, container, false);

        TextView nameAirPort = view.findViewById(R.id.tv_name_airport);
        nameAirPort.setText(mNameAirport);

        AirportsAdapter adapter = new AirportsAdapter(nameAllAirports, codeAllAirports);
        ListView lvMajorAirports = view.findViewById(R.id.lvMajorAirports);
        lvMajorAirports.setAdapter(adapter);

        lvMajorAirports.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LocationAirportFragment fragment = new LocationAirportFragment();
                String tag = "LocationAirportFragment";
                Bundle args = new Bundle();
                args.putString("name location airport", nameAllAirports.get(position));
                args.putString("code location airport", codeAllAirports.get(position));
                fragment.setArguments(args);
                replaceFragment(R.id.frameLayoutContainer, fragment, true);
            }
        });


        activity.showNothing();
        activity.visibleBackItem(false);
        activity.invalidateOptionsMenu();

        return view;
    }
}
package com.fussyvegan.scanner;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fussyvegan.scanner.activity.MainActivity;
import com.fussyvegan.scanner.adapter.AirportsAdapter;

import java.util.ArrayList;
import java.util.List;

public class AirportsNewZealandFragment extends BaseFragment {

    private static final String NAME_COUNTRY_AIRPORT= "name airport";

    private String mNameAirport;
    List<String> nameAllAirports;
    List<String> codeAllAirports;


    MainActivity activity;

    public AirportsNewZealandFragment() {
        nameAllAirports = new ArrayList<>();
        codeAllAirports = new ArrayList<>();
        getAirports();
    }

    private void getAirports(){
        nameAllAirports.add("Auckland"); codeAllAirports.add("AKL");
        nameAllAirports.add("Christchurch"); codeAllAirports.add("CHC");
        nameAllAirports.add("Queenstown"); codeAllAirports.add("ZQN");
        nameAllAirports.add("Wellington"); codeAllAirports.add("WLG");
    }

    public static AirportsNewZealandFragment newInstance(String param1, String param2) {
        AirportsNewZealandFragment fragment = new AirportsNewZealandFragment();
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
        View view = inflater.inflate(R.layout.fragment_airports_new_zealand, container, false);
        TextView nameAirPort = view.findViewById(R.id.tv_name_airport);
        nameAirPort.setText(mNameAirport);

        AirportsAdapter adapter = new AirportsAdapter(nameAllAirports, codeAllAirports);
        ListView lvAllAirports = view.findViewById(R.id.lvAllAirports);
        lvAllAirports.setAdapter(adapter);

        lvAllAirports.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 2) {
                    MapLocationAirportFragment fragment = new MapLocationAirportFragment();
                    String tag = "MapLocationAirportFragment";
                    Bundle args = new Bundle();
                    args.putString("name location airport", nameAllAirports.get(position));
                    args.putString("code location airport", codeAllAirports.get(position));
                    args.putString("place", "NZL");
                    fragment.setArguments(args);
                    replaceFragment(R.id.frameLayoutContainer, fragment, true);

                } else {
                    LocationAirportFragment fragment = new LocationAirportFragment();
                    String tag = "LocationAirportFragment";
                    Bundle args = new Bundle();
                    args.putString("name location airport", nameAllAirports.get(position));
                    args.putString("code location airport", codeAllAirports.get(position));
                    fragment.setArguments(args);
                    replaceFragment(R.id.frameLayoutContainer, fragment, true);



                }
            }
        });

        activity.showNothing();
        activity.visibleBackItem(false);
        activity.invalidateOptionsMenu();

        return view;
    }
}
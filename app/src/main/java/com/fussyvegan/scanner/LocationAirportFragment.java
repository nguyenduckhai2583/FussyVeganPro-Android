package com.fussyvegan.scanner;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fussyvegan.scanner.activity.LocationAirportDetailActivity;
import com.fussyvegan.scanner.activity.MainActivity;
import com.fussyvegan.scanner.adapter.LocationAirlineAdapter;
import com.fussyvegan.scanner.adapter.ProductsAirlineAdapter;
import com.fussyvegan.scanner.model.LocationAirport;
import com.fussyvegan.scanner.model.Product;
import com.fussyvegan.scanner.model.ResourceLocationAirport;
import com.fussyvegan.scanner.model.ResourceProductAirline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LocationAirportFragment extends Fragment {

    private static final String NAME_LOCATION_AIRPORT = "name location airport";
    private static final String CODE_LOCATION_AIRPORT = "code location airport";

    private String mNameLocationAirport;
    private String mCodeLocationAirport;

    TextView numberLocation;
    ListView lvLocation;

    List<LocationAirport> locationAirports;

    APIInterface apiInterface;

    MainActivity activity;

    SearchView searchView;

    public LocationAirportFragment() {
        locationAirports = new ArrayList<>();
    }


    public static LocationAirportFragment newInstance(String param1, String param2) {
        LocationAirportFragment fragment = new LocationAirportFragment();
        Bundle args = new Bundle();
        args.putString(NAME_LOCATION_AIRPORT, param1);
        args.putString(CODE_LOCATION_AIRPORT, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNameLocationAirport = getArguments().getString(NAME_LOCATION_AIRPORT);
            mCodeLocationAirport = getArguments().getString(CODE_LOCATION_AIRPORT);
        }
        fetchLocationAirlines("",mCodeLocationAirport);
        activity = (MainActivity) this.getActivity();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_airport, container, false);

        TextView nameAirport = view.findViewById(R.id.tv_name_airport);
        nameAirport.setText(mNameLocationAirport+" " + mCodeLocationAirport);
        numberLocation = view.findViewById(R.id.tvNumProductFound);
        lvLocation = view.findViewById(R.id.ltvLocation);
        final LocationAirlineAdapter adapter = new LocationAirlineAdapter(locationAirports);
        lvLocation.setAdapter(adapter);
        lvLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), LocationAirportDetailActivity.class);
                intent.putExtra("location", locationAirports.get(position));
                startActivity(intent);
            }
        });
        activity.showNothing();
        activity.visibleBackItem(false);
        activity.invalidateOptionsMenu();

        searchView = view.findViewById(R.id.searchView);
        searchView.setIconified(false);
        searchView.setQuery(activity.keyword, false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                fetchLocationAirlines(s, mCodeLocationAirport);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //searchView.setQuery("", false);
        searchView.clearFocus();
    }

    public void fetchLocationAirlines(String search,String keyword) {
        Log.e("Keyword", keyword);
        apiInterface = APILocationAirports.getClient().create(APIInterface.class);
        Call<ResourceLocationAirport> call = null;
        call = apiInterface.getLocationAirport(search,keyword);
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        dialog.show();
        call.enqueue(new Callback<ResourceLocationAirport>() {
            @Override
            public void onResponse(Call<ResourceLocationAirport> call, Response<ResourceLocationAirport> response) {
                dialog.dismiss();
                Log.d("TAG","status: " + response.code());

                ResourceLocationAirport resource = response.body();
                // Log.d("TAG","body: " + response.body().toString());
                locationAirports.clear();
                locationAirports = resource.getProducts();
                Collections.sort(locationAirports, new Comparator<LocationAirport>(){
                    public int compare(LocationAirport p1, LocationAirport p2) {
                        return p1.getName().compareToIgnoreCase(p2.getName()); // To compare string values
                    }
                });
                Log.e("TAG", locationAirports.toString());
                numberLocation.setText(String.valueOf(locationAirports.size()));
                LocationAirlineAdapter adapter = new LocationAirlineAdapter(locationAirports);
                lvLocation.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<ResourceLocationAirport> call, Throwable t) {
                Log.e("fail", t.getMessage());
            }

        });
    }

}
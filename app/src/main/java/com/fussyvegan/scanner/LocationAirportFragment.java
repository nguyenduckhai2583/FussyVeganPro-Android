package com.fussyvegan.scanner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.fussyvegan.scanner.activity.LocationAirportDetailActivity;
import com.fussyvegan.scanner.activity.MainActivity;
import com.fussyvegan.scanner.adapter.LocationAirlineAdapter;
import com.fussyvegan.scanner.model.LocationAirport;
import com.fussyvegan.scanner.model.ResourceLocationAirport;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

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

    FusedLocationProviderClient fusedLocationProviderClient;

    private double latitudeCurrent;
    private double longitudeCurrent;
    List<Integer> distanceList;

    APIInterface apiInterface;

    MainActivity activity;

    SearchView searchView;
     LocationAirlineAdapter mAdapter;

    public LocationAirportFragment() {
        locationAirports = new ArrayList<>();
        distanceList = new ArrayList<>();
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
        fetchLocationAirlines("", mCodeLocationAirport);
        activity = (MainActivity) this.getActivity();
        getCurrentLocation();
        fetchLocationAirlines("", mCodeLocationAirport);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_airport, container, false);

        TextView nameAirport = view.findViewById(R.id.tv_name_airport);
        nameAirport.setText(mNameLocationAirport + " " + mCodeLocationAirport);
        numberLocation = view.findViewById(R.id.tvNumProductFound);
        lvLocation = view.findViewById(R.id.ltvLocation);
        mAdapter  = new LocationAirlineAdapter(locationAirports,distanceList);
        lvLocation.setAdapter(mAdapter);
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

    private void getCurrentLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                Log.e("Location", location.getLatitude() + ", " + location.getLongitude());
                latitudeCurrent = location.getLatitude();
                longitudeCurrent = location.getLongitude();
            }
        });
    }

    private List<Integer> distance(double lat1, double long1, List<LocationAirport> locationAirports) {
        List<Integer> distanceList = new ArrayList<>();
        for (LocationAirport locationAirport : locationAirports) {
            double distance = 0;

            double lat2 = Double.parseDouble(locationAirport.getLatitude());
            double long2 = Double.parseDouble(locationAirport.getLongitude());

            Location locationCurrent = new Location("Current");
            locationCurrent.setLatitude(lat1);
            locationCurrent.setLongitude(long1);

            Location locationCome = new Location("Come");
            locationCome.setLatitude(lat2);
            locationCome.setLongitude(long2);

            distance = locationCurrent.distanceTo(locationCome) / 1000;
            distanceList.add((int) distance);
        }
        return distanceList;

    }


    public void fetchLocationAirlines(String search, String keyword) {
        Log.e("Keyword", keyword);
        apiInterface = APILocationAirports.getClient().create(APIInterface.class);
        Call<ResourceLocationAirport> call = null;
        call = apiInterface.getLocationAirport(search, keyword);
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        dialog.show();
        call.enqueue(new Callback<ResourceLocationAirport>() {
            @Override
            public void onResponse(Call<ResourceLocationAirport> call, Response<ResourceLocationAirport> response) {
                dialog.dismiss();
                Log.d("TAG", "status: " + response.code());

                ResourceLocationAirport resource = response.body();
                // Log.d("TAG","body: " + response.body().toString());
                locationAirports.clear();
                locationAirports = resource.getProducts();
                Collections.sort(locationAirports, new Comparator<LocationAirport>() {
                    public int compare(LocationAirport p1, LocationAirport p2) {
                        return p1.getName().compareToIgnoreCase(p2.getName()); // To compare string values
                    }
                });
                Log.e("TAG", locationAirports.toString());
                numberLocation.setText(String.valueOf(locationAirports.size()));
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResourceLocationAirport> call, Throwable t) {
                Log.e("fail", t.getMessage());
            }

        });
    }


}
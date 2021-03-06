package com.fussyvegan.scanner;

import android.Manifest;
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

import com.fussyvegan.scanner.activity.MainActivity;
import com.fussyvegan.scanner.activity.ResortDetailActivity;
import com.fussyvegan.scanner.adapter.ResortAdapter;
import com.fussyvegan.scanner.model.Resort;
import com.fussyvegan.scanner.model.ResourceResort;
import com.fussyvegan.scanner.search.ResortFilter;
import com.fussyvegan.scanner.utils.GPSUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ResortsFragment extends Fragment implements GPSUtil.TurnOnGPS {

    private static final String NAME_COUNTRY = "country";

    private String mNameCountry;

    TextView numberResorts;
    ListView lvResorts;

    Location mLocation;
    GPSUtil gpsUtil;

    List<Resort> resorts;

    APIInterface apiInterface;

    MainActivity activity;

    SearchView searchView;

    FusedLocationProviderClient fusedLocationProviderClient;

    private double latitudeCurrent;
    private double longitudeCurrent;
    List<Integer> distanceList;

    String mSearch;
    String mKeyWord;

    public ResortsFragment() {
        resorts = new ArrayList<>();

    }

    public static ResortsFragment newInstance(String param1, String param2) {
        ResortsFragment fragment = new ResortsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNameCountry = getArguments().getString(NAME_COUNTRY);
        }
        activity = (MainActivity) this.getActivity();
        getCurrentLocation();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resorts, container, false);
        TextView nameResort = view.findViewById(R.id.tv_name_resort);
        nameResort.setText(mNameCountry + " Resorts");
        numberResorts = view.findViewById(R.id.tvNumResortsFound);
        lvResorts = view.findViewById(R.id.ltvResorts);
        final ResortAdapter resortAdapter = new ResortAdapter(resorts, distanceList);
        lvResorts.setAdapter(resortAdapter);
        lvResorts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ResortDetailActivity.class);
                intent.putExtra("resort", resorts.get(position));
                startActivity(intent);
            }
        });

        activity.showNothing();
        activity.visibleBackItem(false);
        activity.invalidateOptionsMenu();
        activity.showFilterSearchResortOnly();
        if (MyApplication.getInstanceResort().getFilter() != null) {
            fetchResort(MyApplication.getInstanceResort().getFilter());
        } else {
            fetchResort(mSearch);
        }

        searchView = view.findViewById(R.id.searchView);
        searchView.setIconified(false);
        searchView.setQuery(activity.keyword, false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String value) {
                mSearch = value;
                fetchResort(value);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return view;
    }

    @Subscribe
    public void OnCustomEvent(ResortFilter data) {
        if (data.isClear()) {
            fetchResort(null);

        } else {
            fetchResort(data.getFilter());
            MyApplication.getInstanceResort().setFilter(data.getFilter());

        }
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
                //  Log.e("Location", location.getLatitude() +", "+ location.getLongitude());
                if (location != null) {
                    latitudeCurrent = location.getLatitude();
                    longitudeCurrent = location.getLongitude();
                } else {
                    gpsUtil = new GPSUtil(getActivity(), true);
                    mLocation = gpsUtil.getCurrentLocation(true);
                    latitudeCurrent = gpsUtil.getLatitude();
                    longitudeCurrent = gpsUtil.getLongitude();
                }
            }
        });
    }

    private List<Integer> distance(double lat1, double long1, List<Resort> resorts) {
        List<Integer> distanceList = new ArrayList<>();
        for (Resort resort : resorts) {
            double distance = 0;

            double lat2 = Double.parseDouble(resort.getLatitude());
            double long2 = Double.parseDouble(resort.getLongitude());

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

    public void fetchResort(String keyword) {
        apiInterface = APIResort.getClient().create(APIInterface.class);
        Call<ResourceResort> call = null;
        call = apiInterface.getResorts(mSearch, keyword, mNameCountry);
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        dialog.show();
        call.enqueue(new Callback<ResourceResort>() {
            @Override
            public void onResponse(Call<ResourceResort> call, Response<ResourceResort> response) {
                dialog.dismiss();
                Log.d("TAG", "status: " + response.code());

                ResourceResort resource = response.body();
                // Log.d("TAG","body: " + response.body().toString());
                resorts.clear();
                resorts = resource.getProducts();
                Collections.sort(resorts, new Comparator<Resort>() {
                    public int compare(Resort p1, Resort p2) {
                        return p1.getName().compareToIgnoreCase(p2.getName()); // To compare string values
                    }
                });
                Log.e("TAG", resorts.toString());
                numberResorts.setText(String.valueOf(resorts.size()));
                distanceList = distance(latitudeCurrent, longitudeCurrent, resorts);
                ResortAdapter adapter = new ResortAdapter(resorts, distanceList);
                lvResorts.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResourceResort> call, Throwable t) {
                Log.e("fail", t.getMessage());
            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //searchView.setQuery("", false);
        searchView.clearFocus();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onChangeLocation(Location location) {
        mLocation = location;
    }
}
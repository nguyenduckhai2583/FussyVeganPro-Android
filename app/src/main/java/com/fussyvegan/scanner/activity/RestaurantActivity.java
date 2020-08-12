package com.fussyvegan.scanner.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fussyvegan.scanner.APIInterface;
import com.fussyvegan.scanner.APIRestaurantClient;
import com.fussyvegan.scanner.OnRestaurantClickListener;
import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.adapter.RestaurantAdapter;
import com.fussyvegan.scanner.model.CuisineType;
import com.fussyvegan.scanner.model.restaurant.Restaurant;
import com.fussyvegan.scanner.model.restaurant.RestaurantResponse;
import com.fussyvegan.scanner.utils.GPSUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RestaurantActivity extends AppCompatActivity implements OnRestaurantClickListener, GPSUtil.TurnOnGPS {

    public static final String TAG = RestaurantActivity.class.getSimpleName();
    private static final String NAME_CITY = "City";
    private static final String NAME_COUNTRY = "Country";
    private static final int CODE_FILTER = 100;
    private static final int LOCATION_PERMISSION_CODE = 1001;
    private static final String DISTANCE = "distance";
    private static final String LIST_CHOOSE = "listChoose";
    private static final String RESTAURANT = "restaurant";

    double latitudeCurrent;
    double longitudeCurrent;
    Location mLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    GPSUtil gpsUtil;

    ProgressDialog dialog;

    ImageView imgBack, imgFilter;
    TextView tvNumFilterActive, tvNumProductFound, tvSearch;
    EditText edtSearch;
    RecyclerView recyclerRestaurant;
    RestaurantAdapter adapter;
    ArrayList<Restaurant> list;
    ArrayList<String> listDistance;
    ArrayList<CuisineType> listChoose;
    int distance = 0;

    APIInterface apiInterface;

    String region;
    String country;
    String keySearch = "";
    String cuisineType = "";

    int totalPage;
    int currentPage = 1;

    int currentItem, totalItem, scrollOutItem;
    boolean isScrolling = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        Intent intent = getIntent();
        region = intent.getStringExtra(NAME_CITY);
        country = intent.getStringExtra(NAME_COUNTRY);

        checkPermissionLocation();
        addContent();
        addEvent();
        loadRestaurant();
    }

    private void loadRestaurant() {
        list.clear();
        listDistance.clear();
        apiInterface = APIRestaurantClient.getClient().create(APIInterface.class);
        dialog = ProgressDialog.show(this, "Loading...", "Please wait...", true);
        fetchData(keySearch, currentPage);
    }

    private void checkPermissionLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(RestaurantActivity.this);
        if (ActivityCompat.checkSelfPermission(RestaurantActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(RestaurantActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RestaurantActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_CODE);
        } else {
            getLocation();
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    latitudeCurrent = location.getLatitude();
                    longitudeCurrent = location.getLongitude();
                } else {
                    gpsUtil = new GPSUtil(RestaurantActivity.this, true);
                    mLocation = gpsUtil.getCurrentLocation(true);
                    latitudeCurrent = gpsUtil.getLatitude();
                    longitudeCurrent = gpsUtil.getLongitude();
                }
            }
        });
    }

    private void addEvent() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RestaurantActivity.this, RestaurantFilterActivity.class);
                intent.putExtra(DISTANCE, distance);
                intent.putExtra(LIST_CHOOSE, listChoose);
                startActivityForResult(intent, CODE_FILTER);
            }
        });

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvSearch.setVisibility(View.INVISIBLE);
                keySearch = edtSearch.getText().toString();
                filterData(1, cuisineType, false);
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!edtSearch.getText().toString().equals("")){
                    tvSearch.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void addContent() {
        listChoose = new ArrayList<>();
        imgBack = findViewById(R.id.imgBack);
        imgFilter = findViewById(R.id.imgFilter);
        tvNumFilterActive = findViewById(R.id.tvNumFilterActive);
        tvNumProductFound = findViewById(R.id.tvNumProductFound);
        tvSearch = findViewById(R.id.tvSearch);
        edtSearch = findViewById(R.id.edtSearch);

        list = new ArrayList<>();
        listDistance = new ArrayList<>();
        adapter = new RestaurantAdapter(list, listDistance,this, this);
        recyclerRestaurant = findViewById(R.id.recyclerRestaurant);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerRestaurant.setLayoutManager(linearLayoutManager);
        recyclerRestaurant.setAdapter(adapter);

        recyclerRestaurant.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItem = linearLayoutManager.getChildCount();
                totalItem = linearLayoutManager.getItemCount();
                scrollOutItem = linearLayoutManager.findFirstVisibleItemPosition();

                if (isScrolling && ((currentItem + scrollOutItem) == totalItem)) {
                    isScrolling = false;
                    currentPage++;
                    if (currentPage <= totalPage) {
                        filterData(currentPage, cuisineType, true);
                    }
                }
            }
        });
    }

    private void fetchData(String key, int page) {
        dialog.show();
        Call<RestaurantResponse> call = apiInterface.getRestaurant(key, country, region, page);
        call.enqueue(new Callback<RestaurantResponse>() {

            @Override
            public void onResponse(Call<RestaurantResponse> call, Response<RestaurantResponse> response) {
                if (response.code() == 200) {
                    list.addAll(response.body().getList());
                    listDistance.addAll(distance(latitudeCurrent, longitudeCurrent, response.body().getList()));
                    adapter.notifyDataSetChanged();
                    tvNumProductFound.setText(String.valueOf(response.body().getPaginate().getTotal()));
                    totalPage = response.body().getPaginate().getTotal_page();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<RestaurantResponse> call, Throwable t) {
                dialog.dismiss();
                call.cancel();
            }
        });
    }

    private void filterData(int page, String typeCuisine, final boolean isLoadmore) {
        dialog.show();
        if (!isLoadmore) {
            list.clear();
            listDistance.clear();
        }

        Call<RestaurantResponse> call = apiInterface.getRestaurantByFilter(keySearch, country, region, page,
                distance, latitudeCurrent, longitudeCurrent, typeCuisine);
        call.enqueue(new Callback<RestaurantResponse>() {

            @Override
            public void onResponse(Call<RestaurantResponse> call, Response<RestaurantResponse> response) {
                if (response.code() == 200) {
                    list.addAll(response.body().getList());
                    listDistance.addAll(distance(latitudeCurrent, longitudeCurrent, response.body().getList()));
                    adapter.notifyDataSetChanged();

                    if (!isLoadmore) {
                        tvNumFilterActive.setText(String.valueOf(response.body().getPaginate().getTotal()));
                    }
                    totalPage = response.body().getPaginate().getTotal_page();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<RestaurantResponse> call, Throwable t) {
                dialog.dismiss();
                call.cancel();
            }
        });
    }

    @Override
    public void onClick(Restaurant restaurant) {
        // Switcth to detail screen
        Intent intent = new Intent(RestaurantActivity.this, RestaurantDetailActivity.class);
        intent.putExtra(RESTAURANT, restaurant);
        startActivity(intent);
    }

    private List<String> distance(double lat1, double long1,List<Restaurant> restaurants) {
        List<String> distanceList= new ArrayList<>();
        for(Restaurant item : restaurants) {
            int distance;

            double lat2 = Double.parseDouble(item.getLatitude());
            double long2 = Double.parseDouble(item.getLongitude());

            Location locationCurrent = new Location("Current");
            locationCurrent.setLatitude(lat1);
            locationCurrent.setLongitude(long1);

            Location locationCome = new Location("Come");
            locationCome.setLatitude(lat2);
            locationCome.setLongitude(long2);

            distance = (int) (locationCurrent.distanceTo(locationCome)/1000);

            if (lat1 == 0 && long1 == 0) {
                distanceList.add(getResources().getString(R.string.tv_no_gps));
            } else {
                distanceList.add(distance + " km");
            }
        }
        return distanceList;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODE_FILTER && resultCode ==RESULT_OK) {
            if (data!=null) {
                listChoose.clear();
                distance = data.getIntExtra(DISTANCE, 0);
                listChoose = data.getParcelableArrayListExtra(LIST_CHOOSE);
                StringBuilder cuisine = new StringBuilder();
                for (CuisineType item : listChoose) {
                    cuisine.append(item.getType()).append(",");
                }
                if (cuisine.length() > 0) {
                    cuisineType = cuisine.toString().substring(0, cuisine.length()-1);
                } else {
                    cuisineType = "";
                }
                filterData(1, cuisineType, false);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            }
        }
    }

    @Override
    public void onChangeLocation(Location location) {
        mLocation = location;
    }
}
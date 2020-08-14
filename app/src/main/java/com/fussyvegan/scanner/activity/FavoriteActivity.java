package com.fussyvegan.scanner.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fussyvegan.scanner.APIInterface;
import com.fussyvegan.scanner.APILoginClient;
import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.adapter.FavoriteListAdapter;
import com.fussyvegan.scanner.model.LocationAirport;
import com.fussyvegan.scanner.model.Product;
import com.fussyvegan.scanner.model.ProductAirline;
import com.fussyvegan.scanner.model.Resort;
import com.fussyvegan.scanner.model.favorite.CreateListResponse;
import com.fussyvegan.scanner.model.favorite.Favorite;
import com.fussyvegan.scanner.model.favorite.FavoriteListResponse;
import com.fussyvegan.scanner.model.favorite.GroupFavorite;
import com.fussyvegan.scanner.model.restaurant.Restaurant;
import com.fussyvegan.scanner.utils.Constant;
import com.fussyvegan.scanner.utils.GPSUtil;
import com.fussyvegan.scanner.utils.SharedPrefs;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fussyvegan.scanner.utils.Constant.ACCESS_TOKEN;

public class FavoriteActivity extends AppCompatActivity implements FavoriteListAdapter.onDeleteItemListener {

    private static final int LOCATION_PERMISSION_CODE = 1001;

    ArrayList<Favorite> listResponse;
    ArrayList<String> listDistance;
    List<Object> listObject;
    ProgressDialog dialog;
    RecyclerView recyclerFavoriteList;
    FavoriteListAdapter adapter;
    ImageView imgBack;
    TextView tvEdit, tvNameGroup;
    boolean isEdit = false;

    GroupFavorite groupFavorite;

    double latitudeCurrent;
    double longitudeCurrent;
    Location mLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    GPSUtil gpsUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        groupFavorite = getIntent().getParcelableExtra("group");

        checkPermissionLocation();
        addContent();
        addEvent();
        getFavorite();
    }

    private void addEvent() {

        tvNameGroup.setText(groupFavorite.getName());

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.onEdit();
                isEdit = !isEdit;
                String status = isEdit ? "Done" : "Edit";
                tvEdit.setText(status);
            }
        });
    }

    private void addContent() {
        listDistance = new ArrayList<>();
        tvNameGroup = findViewById(R.id.tvNameGroup);
        tvEdit = findViewById(R.id.tvEdit);
        imgBack = findViewById(R.id.imgBack);
        listResponse = new ArrayList<>();
        listObject = new ArrayList<>();
        adapter = new FavoriteListAdapter(this, listObject, listResponse, listDistance, this);
        recyclerFavoriteList = findViewById(R.id.recyclerFavoriteList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerFavoriteList.setLayoutManager(layoutManager);
        recyclerFavoriteList.setAdapter(adapter);
    }

    public void getFavorite() {
        listObject.clear();
        listResponse.clear();
        dialog = ProgressDialog.show(this, "Loading...", "Please wait...", true);
        String token = SharedPrefs.getInstance().get(ACCESS_TOKEN, String.class);
        APIInterface apiInterface = APILoginClient.getClient().create(APIInterface.class);

        Call<FavoriteListResponse> call = apiInterface.getFavoriteList(token, groupFavorite.getId());
        call.enqueue(new Callback<FavoriteListResponse>() {
            @Override
            public void onResponse(Call<FavoriteListResponse> call, Response<FavoriteListResponse> response) {
                if (response.code() == 200) {
                    listResponse.addAll(response.body().getList());
                    cast(listResponse);
                    adapter.notifyDataSetChanged();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<FavoriteListResponse> call, Throwable t) {
                Toast.makeText(FavoriteActivity.this, "Load Error", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    private void checkPermissionLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(FavoriteActivity.this);
        if (ActivityCompat.checkSelfPermission(FavoriteActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(FavoriteActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(FavoriteActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_CODE);
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
                    gpsUtil = new GPSUtil(FavoriteActivity.this, true);
                    mLocation = gpsUtil.getCurrentLocation(true);
                    latitudeCurrent = gpsUtil.getLatitude();
                    longitudeCurrent = gpsUtil.getLongitude();
                }
            }
        });
    }

    public String calculateDistance(double latitude, double longtitude) {
        int distance;
        Location locationCurrent = new Location("Current");
        locationCurrent.setLatitude(latitudeCurrent);
        locationCurrent.setLongitude(longitudeCurrent);

        Location locationCome = new Location("Come");
        locationCome.setLatitude(latitude);
        locationCome.setLongitude(longtitude);

        distance = (int) (locationCurrent.distanceTo(locationCome)/1000);

        if (latitudeCurrent == 0 && longitudeCurrent == 0) {
            return getResources().getString(R.string.tv_no_gps);
        } else {
            return distance + " km";
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

    public void removeFavorite(int favoriteId) {
        dialog = ProgressDialog.show(this, "Loading...", "Please wait...", true);
        String token = SharedPrefs.getInstance().get(ACCESS_TOKEN, String.class);
        APIInterface apiInterface = APILoginClient.getClient().create(APIInterface.class);

        Call<CreateListResponse> call = apiInterface.deleteFavorite(token, favoriteId);
        call.enqueue(new Callback<CreateListResponse>() {
            @Override
            public void onResponse(Call<CreateListResponse> call, Response<CreateListResponse> response) {
                dialog.dismiss();
                if (response.code() == 200) {
                    getFavorite();
                } else {
                    Toast.makeText(FavoriteActivity.this, "Delete Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateListResponse> call, Throwable t) {
                Toast.makeText(FavoriteActivity.this, "Delete Error", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    private void cast(ArrayList<Favorite> list) {
        Gson gson = new Gson();

        for (Favorite item : list) {
            String objJSON = gson.toJson(item);
            switch (item.getFavoriteable_type()) {
                case Constant.FAVOR_PRODUCT:
                    Product productFavor = gson.fromJson(objJSON, Product.class);
                    listObject.add(productFavor);
                    listDistance.add("");
                    break;
                case Constant.FAVOR_FOODCHAIN:
                    Product product = gson.fromJson(objJSON, Product.class);
                    listObject.add(product);
                    listDistance.add("");
                    break;
                case Constant.FAVOR_RESORT:
                    Resort resort = gson.fromJson(objJSON, Resort.class);
                    listObject.add(resort);
                    if (resort.getLatitude() != null && resort.getLongitude() != null) {
                        listDistance.add(calculateDistance(Double.parseDouble(resort.getLatitude()), Double.parseDouble(resort.getLongitude())));
                    }
                    break;
                case Constant.FAVOR_RESTAURANT:
                    Restaurant restaurant = gson.fromJson(objJSON, Restaurant.class);
                    listObject.add(restaurant);
                    if (restaurant.getLatitude() != null && restaurant.getLongitude() != null) {
                        listDistance.add(calculateDistance(Double.parseDouble(restaurant.getLatitude()), Double.parseDouble(restaurant.getLongitude())));
                    }
                    break;
                case Constant.FAVOR_INGREDIENT:
                    Product productIngredient = gson.fromJson(objJSON, Product.class);
                    listObject.add(productIngredient);
                    listDistance.add("");
                    break;
                case Constant.FAVOR_AIRLINE:
                    ProductAirline airline = gson.fromJson(objJSON, ProductAirline.class);
                    listObject.add(airline);
                    listDistance.add("");
                    break;
                case Constant.FAVOR_AIRPORT:
                    LocationAirport airport = gson.fromJson(objJSON, LocationAirport.class);
                    listObject.add(airport);
                    if (airport.getLatitude() != null && airport.getLongitude() != null) {
                        listDistance.add(calculateDistance(Double.parseDouble(airport.getLatitude()), Double.parseDouble(airport.getLongitude())));
                    }
                    break;
            }
        }
    }

    @Override
    public void onDelete(int favoriteId) {
        removeFavorite(favoriteId);
    }
}
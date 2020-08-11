package com.fussyvegan.scanner.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fussyvegan.scanner.APIInterface;
import com.fussyvegan.scanner.APILoginClient;
import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.adapter.ProductReviewAdapter;
import com.fussyvegan.scanner.model.LocationAirport;
import com.fussyvegan.scanner.model.ProductReview;
import com.fussyvegan.scanner.model.accountFlow.Reviews;
import com.fussyvegan.scanner.utils.Constant;
import com.fussyvegan.scanner.utils.SharedPrefs;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import github.nisrulz.screenshott.ScreenShott;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fussyvegan.scanner.utils.Constant.ACCESS_TOKEN;

public class LocationAirportDetailActivity extends AppCompatActivity {

    private static final String TAG = LocationAirportDetailActivity.class.getSimpleName();

    private ImageView imgLocation;
    private TextView tvNameLocation;
    private TextView tvLocation;
    private TextView tvDescription;
    private TextView tvHours;

    private TextView tvDetails;
    private TextView tvMap;
    private TextView tvReview;
    private TextView tvWriteReview;
    TextView tvTotalReview;
    TextView tvRateFiveStar;
    TextView tvRateFourStar;
    TextView tvRateThreeStar;
    TextView tvRateTwoStar;
    TextView tvRateOneStar;

    private TextView tvCustom;

    LinearLayout lnDetails;
    RelativeLayout lnReview;
    // LinearLayout lnMap;

    MapView mMapView;
    private GoogleMap googleMap;

    AppCompatRatingBar rb_AveRating;
    TextView tvSumRating;

    private LocationAirport locationAirport;

    RecyclerView recyclerViewReview;
    ProductReviewAdapter mApdater;

    ImageView imgCamera;
    ImageView imgListWish;
    ImageView imgWebsite;

    private boolean isReview;
    private ProductReview reviewProduct;

    private Bitmap bitmap;
    private final static String[] requestWritePermission =
            {Manifest.permission.WRITE_EXTERNAL_STORAGE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_airport_detail);
        initView(savedInstanceState);
        handOnClick();
    }

    private void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        locationAirport = intent.getParcelableExtra("location");
        recyclerViewReview = findViewById(R.id.recyclerViewReview);
        initRecyclerView();
        getReview(locationAirport.getId(), 2);


        imgLocation = findViewById(R.id.imgLocation);
        if (!locationAirport.getLink_photo().isEmpty()) {
            Picasso.get()
                    .load(locationAirport.getLink_photo())
                    .placeholder(R.drawable.ic_app_150)
                    .into(imgLocation);
        }

        tvNameLocation = findViewById(R.id.txvName);
        tvNameLocation.setText(locationAirport.getName());

        tvLocation = findViewById(R.id.txvLocation);
        tvLocation.setText(locationAirport.getLocation());

        tvDescription = findViewById(R.id.tvInfo);
        tvDescription.setText(locationAirport.getDescription());

        tvHours = findViewById(R.id.tvHours);
        tvHours.setText(locationAirport.getHours());

        imgCamera = findViewById(R.id.imgCamera);
        imgListWish = findViewById(R.id.imgListWish);
        imgWebsite = findViewById(R.id.imgWebsite);

        tvCustom = findViewById(R.id.textView6);

        lnDetails = findViewById(R.id.linearLayoutDetail);
        lnReview = findViewById(R.id.lnReview);
        //lnMap = findViewById(R.id.linearLayoutDetail);

        tvDetails = findViewById(R.id.tvDetails);
        tvDetails.setSelected(true);
        tvMap = findViewById(R.id.tvMap);
        tvReview = findViewById(R.id.tvReview);
        tvWriteReview = findViewById(R.id.tvWriteReview);
        tvTotalReview = findViewById(R.id.tvTotalReview);
        tvRateFiveStar = findViewById(R.id.tvRateFiveStar);
        tvRateFourStar = findViewById(R.id.tvRateFourStar);
        tvRateThreeStar = findViewById(R.id.tvRateThreeStar);
        tvRateTwoStar = findViewById(R.id.tvRateTwoStar);
        tvRateOneStar = findViewById(R.id.tvRateOneStar);

        rb_AveRating = findViewById(R.id.rb_AveRating);
        tvSumRating = findViewById(R.id.tvSumRating);


        mMapView = findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
//                googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                LatLng location = new LatLng(Double.parseDouble(locationAirport.getLatitude()), Double.parseDouble(locationAirport.getLongitude()));
                googleMap.addMarker(new MarkerOptions().position(location).title("Marker Title").icon(getBitmap(LocationAirportDetailActivity.this, R.drawable.ic_restaurant)));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

    }

    private BitmapDescriptor getBitmap(Context context, int vectorID) {
        Drawable drawable = ContextCompat.getDrawable(context, vectorID);
        drawable.setBounds(0, 0, 72, 92);
        Bitmap bitmap = Bitmap.createBitmap(72, 92, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    private void handOnClick(){

        tvDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCustom.setText("Details");
                tvMap.setSelected(false);
                tvReview.setSelected(false);
                tvDetails.setSelected(true);

                mMapView.setVisibility(View.GONE);
                lnReview.setVisibility(View.GONE);
                lnDetails.setVisibility(View.VISIBLE);
            }
        });

        tvMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCustom.setText("Map");
                tvMap.setSelected(true);
                tvReview.setSelected(false);
                tvDetails.setSelected(false);

                mMapView.setVisibility(View.VISIBLE);
                lnReview.setVisibility(View.GONE);
                lnDetails.setVisibility(View.GONE);
            }
        });

        tvReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCustom.setText("Rating and Reviews");
                tvMap.setSelected(false);
                tvReview.setSelected(true);
                tvDetails.setSelected(false);

                mMapView.setVisibility(View.GONE);
                lnReview.setVisibility(View.VISIBLE);
                lnDetails.setVisibility(View.GONE);
            }
        });

        tvWriteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SharedPrefs.getInstance().get(Constant.IS_LOGIN, Boolean.class)) {
                    Intent intentLocation = new Intent(LocationAirportDetailActivity.this, ReviewActivity.class);
                    intentLocation.putExtra("location airport", locationAirport);
                    intentLocation.putExtra("category", 5);

                    if (isReview) {
                        intentLocation.putExtra("review", reviewProduct);
                    }

                    startActivityForResult(intentLocation, Constant.LAUNCH_REVIEW_ACTIVITY);
                } else {
                    Intent loginScreen = new Intent(LocationAirportDetailActivity.this, LoginActivity.class);
                    startActivity(loginScreen);
                }


            }

        });

        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap = ScreenShott.getInstance().takeScreenShotOfRootView(view);
                final boolean hasWritePermission = RuntimePermissionUtil.checkPermissonGranted(LocationAirportDetailActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (bitmap != null) {
                    if (hasWritePermission) {
                        saveScreenshot();
                    } else {
                        RuntimePermissionUtil.requestPermission(LocationAirportDetailActivity.this, requestWritePermission, 100);

                    }
                }
            }

        });

        imgListWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavorite();

            }
        });

        imgWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locationAirport.getLink_website() != null && locationAirport.getLink_website().contains("http")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(locationAirport.getLink_website()));
                    startActivity(intent);
                } else {
                    Toast.makeText(LocationAirportDetailActivity.this, "Weblink Not Available", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void saveScreenshot() {
        // Save the screenshot
        DateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String date = df.format(Calendar.getInstance().getTime());

        try {
            File file = ScreenShott.getInstance()
                    .saveScreenshotToPicturesFolder(LocationAirportDetailActivity.this, bitmap, "date");
            // Display a toast
            Toast.makeText(LocationAirportDetailActivity.this, "Bitmap Saved at " + file.getAbsolutePath(),
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addToFavorite() {
        Toast.makeText(this, "Added to My List", Toast.LENGTH_SHORT).show();
        Realm realm = Realm.getDefaultInstance();
        LocationAirport p = realm.where(LocationAirport.class).equalTo("id", locationAirport.getId()).findFirst();
        realm.beginTransaction();
        if (p == null) {
            p = realm.createObject(LocationAirport.class); // Create a new object
        }
        p.copy(locationAirport);
        realm.commitTransaction();
    }

    private void checkIsReview(List<ProductReview> productReviews) {
        for (int i = 0; i < productReviews.size(); i++) {

            if (productReviews.get(i).getUsername().equals(SharedPrefs.getInstance().get(Constant.USER_NAME, String.class))) {
                isReview = true;
                reviewProduct = productReviews.get(i);
                break;
            }
        }

    }

    private void setRating(List<ProductReview> productReviews) {
        tvTotalReview.setText(getTotalNumberPoint(productReviews));
        tvRateFiveStar.setText(String.valueOf(getNumberPoint(productReviews, 5)));
        tvRateFourStar.setText(String.valueOf(getNumberPoint(productReviews, 4)));
        tvRateThreeStar.setText(String.valueOf(getNumberPoint(productReviews, 3)));
        tvRateTwoStar.setText(String.valueOf(getNumberPoint(productReviews, 2)));
        tvRateOneStar.setText(String.valueOf(getNumberPoint(productReviews, 1)));
    }

    private String getTotalNumberPoint(List<ProductReview> productReviews) {
        float number = 0;
        for (int i = 0; i < productReviews.size(); i++) {
            number = number + productReviews.get(i).getRating();

        }
        return String.format("%.1f", number / productReviews.size());

    }

    private int getNumberPoint(List<ProductReview> productReviews, int point) {
        int number = 0;
        for (int i = 0; i < productReviews.size(); i++) {

            if (productReviews.get(i).getRating() == point) {
                number++;
            }
        }
        return number;

    }

    private void initRecyclerView() {
        mApdater = new ProductReviewAdapter(this);
        recyclerViewReview.setAdapter(mApdater);
        recyclerViewReview.setLayoutManager(new LinearLayoutManager(this));

    }

    private void setOverallRatingReview(List<ProductReview> data) {
        int numReview = data.size();

        float number = 0;
        for (int i = 0; i < data.size(); i++) {
            number = number + data.get(i).getRating();
        }

        float aveRating = number / data.size();

        tvSumRating.setText(numReview);
        rb_AveRating.setRating(aveRating);
    }

    private void getReview(int idProduct, int idType) {
        String token = SharedPrefs.getInstance().get(ACCESS_TOKEN, String.class);
        APIInterface apiInterface = APILoginClient.getClient().create(APIInterface.class);

        Log.e(TAG, token);

        Call<Reviews> call = apiInterface.getReviewProduct(token, idProduct, idType);
        call.enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {

                Log.d(TAG, String.valueOf(response.code()));
                Log.d(TAG, String.valueOf(response.body().getData()));
                if (!response.body().getData().isEmpty()) {
                    mApdater.updateData(response.body().getData());
                    setRating(response.body().getData());
                    checkIsReview(response.body().getData());
                    setOverallRatingReview(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });

    }
}
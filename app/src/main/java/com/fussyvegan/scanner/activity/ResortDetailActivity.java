package com.fussyvegan.scanner.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fussyvegan.scanner.APIInterface;
import com.fussyvegan.scanner.APILoginClient;
import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.adapter.ProductReviewAdapter;
import com.fussyvegan.scanner.dialog.BottomSheetListFavorite;
import com.fussyvegan.scanner.model.LocationAirport;
import com.fussyvegan.scanner.model.ProductReview;
import com.fussyvegan.scanner.model.Resort;
import com.fussyvegan.scanner.model.accountFlow.Reviews;
import com.fussyvegan.scanner.model.favorite.FavoriteType;
import com.fussyvegan.scanner.utils.Constant;
import com.fussyvegan.scanner.utils.SharedPrefs;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import github.nisrulz.screenshott.ScreenShott;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fussyvegan.scanner.utils.Constant.ACCESS_TOKEN;

public class ResortDetailActivity extends AppCompatActivity {

    private static final String TAG = ResortDetailActivity.class.getSimpleName();
    private static final String FAVORITE = "favorite";

    private ImageView imgResort;
    private TextView tvNameResort;
    private TextView tvLocation;
    private TextView tvDescription;
    ImageView imgBack;

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

    private Resort resort;

    RecyclerView recyclerViewReview;
    ProductReviewAdapter mApdater;

    ImageView imgCamera;
    ImageView imgListWish;
    ImageView imgWebsite;
    ImageView imgPhone;

    ImageView imgBtBook;
    ImageView imgBtExpedia;
    ImageView imgBtHotels;

    private Bitmap bitmap;
    private final static String[] requestWritePermission =
            {Manifest.permission.WRITE_EXTERNAL_STORAGE};



    private boolean isReview;
    private ProductReview reviewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resort_detail);
        initView(savedInstanceState);
        handOnClick();
    }

    private void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        resort = intent.getParcelableExtra("resort");
        recyclerViewReview = findViewById(R.id.recyclerViewReview);
        initRecyclerView();
        getReview(resort.getId(), 3);

        imgBack = findViewById(R.id.imgBack);

        imgResort = findViewById(R.id.imgResort);
        if (!resort.getLink_photo().isEmpty()) {
            Picasso.get()
                    .load(resort.getLink_photo())
                    .placeholder(R.drawable.ic_app_150)
                    .into(imgResort);
        }

        tvNameResort = findViewById(R.id.txvName);
        tvNameResort.setText(resort.getName());

        tvLocation = findViewById(R.id.txvLocation);
        tvLocation.setText(resort.getLocation());

        tvDescription = findViewById(R.id.tvInfo);
        tvDescription.setText(resort.getDescription());


        imgCamera = findViewById(R.id.imgCamera);
        imgListWish = findViewById(R.id.imgListWish);
        imgWebsite = findViewById(R.id.imgWebsite);
        imgPhone = findViewById(R.id.imgPhone);

        imgBtBook = findViewById(R.id.imgBtBooking);
        imgBtHotels = findViewById(R.id.imgBtHotels);
        imgBtExpedia = findViewById(R.id.imgBtExpedia);

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
                LatLng location = new LatLng(Double.parseDouble(resort.getLatitude()), Double.parseDouble(resort.getLongitude()));
                googleMap.addMarker(new MarkerOptions().position(location).title("Marker Title").icon(getBitmap(ResortDetailActivity.this, R.drawable.ic_restaurant)));

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

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


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
                    Intent intentLocation = new Intent(ResortDetailActivity.this, ReviewActivity.class);
                    intentLocation.putExtra("resort", resort);
                    intentLocation.putExtra("category", 3);

                    if (isReview) {
                        intentLocation.putExtra("review", reviewProduct);
                    }

                    startActivityForResult(intentLocation, Constant.LAUNCH_REVIEW_ACTIVITY);
                } else {
                    Intent loginScreen = new Intent(ResortDetailActivity.this, LoginActivity.class);
                    startActivity(loginScreen);
                }


            }

        });

        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap = ScreenShott.getInstance().takeScreenShotOfRootView(view);
                final boolean hasWritePermission = RuntimePermissionUtil.checkPermissonGranted(ResortDetailActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (bitmap != null) {
                    if (hasWritePermission) {
                        saveScreenshot();
                    } else {
                        RuntimePermissionUtil.requestPermission(ResortDetailActivity.this, requestWritePermission, 100);

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

        imgPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhoneNumber(resort.getPhone());
            }
        });


        imgWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resort.getLink_website() != null && resort.getLink_website().contains("http")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(resort.getLink_website()));
                    startActivity(intent);
                } else {
                    Toast.makeText(ResortDetailActivity.this, "Weblink Not Available", Toast.LENGTH_SHORT).show();
                }

            }
        });

        imgBtBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resort.getBooking_com() != null && resort.getBooking_com().contains("http")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(resort.getBooking_com()));
                    startActivity(intent);
                } else {
                    Toast.makeText(ResortDetailActivity.this, "Weblink Not Available", Toast.LENGTH_SHORT).show();
                }

            }
        });

        imgBtExpedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resort.getExpedia_com() != null && resort.getExpedia_com().contains("http")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(resort.getExpedia_com()));
                    startActivity(intent);
                } else {
                    Toast.makeText(ResortDetailActivity.this, "Weblink Not Available", Toast.LENGTH_SHORT).show();
                }

            }
        });

        imgBtHotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resort.getHotels_com() != null && resort.getHotels_com().contains("http")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(resort.getHotels_com()));
                    startActivity(intent);
                } else {
                    Toast.makeText(ResortDetailActivity.this, "Weblink Not Available", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void callPhoneNumber(String phone) {
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(ResortDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                startActivity(callIntent);

            }
            else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                startActivity(callIntent);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if(requestCode == 101)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                callPhoneNumber(resort.getPhone());
            }
            else
            {
                Log.e(TAG, "Permission not Granted");
            }
        }
    }

    private void saveScreenshot() {
        // Save the screenshot
        DateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String date = df.format(Calendar.getInstance().getTime());

        try {
            File file = ScreenShott.getInstance()
                    .saveScreenshotToPicturesFolder(ResortDetailActivity.this, bitmap, "date");
            // Display a toast
            Toast.makeText(ResortDetailActivity.this, "Bitmap Saved at " + file.getAbsolutePath(),
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addToFavorite() {
        showBottomSheet();
    }

    private void showBottomSheet() {
        BottomSheetListFavorite bottomSheetListFavorite = new BottomSheetListFavorite();
        Bundle bundle = new Bundle();
        bundle.putParcelable(FAVORITE, new FavoriteType(Constant.FAVOR_RESORT, resort.getId(), -1));
        bottomSheetListFavorite.setArguments(bundle);
        bottomSheetListFavorite.show(getSupportFragmentManager(), "Dialog");
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

        tvSumRating.setText("("+numReview+")");
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

    @Override
    protected void onResume() {
        super.onResume();
        getReview(resort.getId(), 3);
    }


}
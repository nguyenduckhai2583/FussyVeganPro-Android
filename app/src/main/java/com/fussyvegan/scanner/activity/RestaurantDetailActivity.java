package com.fussyvegan.scanner.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatRatingBar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fussyvegan.scanner.APIInterface;
import com.fussyvegan.scanner.APILoginClient;
import com.fussyvegan.scanner.DetailsFragment;
import com.fussyvegan.scanner.MapFragment;
import com.fussyvegan.scanner.OnRestaurantClickListener;
import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.ReviewFragment;
import com.fussyvegan.scanner.model.ProductReview;
import com.fussyvegan.scanner.model.accountFlow.Reviews;
import com.fussyvegan.scanner.model.restaurant.Restaurant;
import com.fussyvegan.scanner.utils.SharedPrefs;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import github.nisrulz.screenshott.ScreenShott;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fussyvegan.scanner.utils.Constant.ACCESS_TOKEN;

public class RestaurantDetailActivity extends AppCompatActivity implements OnRestaurantClickListener{

    private static final String RESTAURANT = "restaurant";
    private static final String LIST_REVIEW = "list_review";
    private static final int RESTAURANT_CATEGORY_ID = 4;
    private static final int CALL_CODE = 100;
    private static final int CAPTURE_CODE = 101;

    private final static String[] requestWritePermission =
            {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    ImageView imgBack, imgRes, imgCamera, imgListWish, imgCall, imgWebsite;
    TextView tvNameRes, tvAddress, tvAveReview, tvTabTitle;
    ViewPager viewPager;
    TabLayout tabLayout;
    AppCompatRatingBar reviewRatingBar;
    PageApdater adapter;
    private Restaurant restaurant;
    ProgressDialog dialog;
    ArrayList<ProductReview> list;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        restaurant = getIntent().getParcelableExtra(RESTAURANT);

        addContent();
        addEvent();

        getReview(restaurant.getId(), RESTAURANT_CATEGORY_ID);

        initViewPager();
        initData();
    }

    private void initData() {
        if (restaurant != null) {
            Picasso.get().load(restaurant.getLink_photo()).into(imgRes);
            tvNameRes.setText(restaurant.getName());
            tvAddress.setText(restaurant.getLocation());
        }
    }

    private void initViewPager() {
        adapter = new PageApdater(getSupportFragmentManager());

        Bundle bundle = new Bundle();
        bundle.putParcelable(RESTAURANT, restaurant);
        bundle.putParcelableArrayList(LIST_REVIEW, list);

        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);

        MapFragment mapFragment = new MapFragment();
        mapFragment.setArguments(bundle);

        ReviewFragment reviewFragment = new ReviewFragment();
        reviewFragment.setArguments(bundle);

        adapter.addFragment(detailsFragment, "Details");
        adapter.addFragment(mapFragment, "Map");
        adapter.addFragment(reviewFragment, "Reviews");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void addEvent() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tvTabTitle.setText(getString(R.string.tv_detail));
                        break;
                    case 1:
                        tvTabTitle.setText(getString(R.string.tv_map));
                        break;
                    case 2:
                        tvTabTitle.setText(getString(R.string.tv_review));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap = ScreenShott.getInstance().takeScreenShotOfRootView(view);
                final boolean hasWritePermission = RuntimePermissionUtil.checkPermissonGranted(RestaurantDetailActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (bitmap != null) {
                    if (hasWritePermission) {
                        saveScreenshot();
                    } else {
                        RuntimePermissionUtil.requestPermission(RestaurantDetailActivity.this, requestWritePermission, CAPTURE_CODE);

                    }
                }
            }
        });

        imgListWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(RestaurantDetailActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(RestaurantDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, CALL_CODE);
                } else {
                    makeCall();
                }
            }
        });

        imgWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (restaurant.getLink_website() != null && restaurant.getLink_website().contains("http")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(restaurant.getLink_website()));
                    startActivity(intent);
                } else {
                    Toast.makeText(RestaurantDetailActivity.this, "Weblink Not Available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void makeCall() {
        Uri uri = Uri.parse("tel:" + restaurant.getPhone());
        Intent itentcall = new Intent(Intent.ACTION_CALL);
        itentcall.setData(uri);
        startActivity(itentcall);
    }

    private void addContent() {
        list = new ArrayList<>();
        dialog = ProgressDialog.show(this, "Loading...", "Please wait...", true);
        imgBack = findViewById(R.id.imgBack);
        imgRes = findViewById(R.id.imgRes);
        imgCamera = findViewById(R.id.imgCamera);
        imgListWish = findViewById(R.id.imgListWish);
        imgCall = findViewById(R.id.ImgCall);
        imgWebsite = findViewById(R.id.imgWebsite);
        tvNameRes = findViewById(R.id.tvNameRes);
        tvAddress = findViewById(R.id.tvAddress);
        tvAveReview = findViewById(R.id.tvAveReview);
        tvTabTitle = findViewById(R.id.tvTabTitle);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        reviewRatingBar = findViewById(R.id.reviewRatingBar);
    }

    @Override
    public void onClick(Restaurant restaurant) {
        tvAveReview.setText(String.valueOf(restaurant.getAvg_rating()));
        reviewRatingBar.setRating(restaurant.getAvg_rating());
    }

    public static class PageApdater extends FragmentStatePagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitle = new ArrayList<>();

        PageApdater(@NonNull FragmentManager fm) {
            super(fm);
        }

        void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitle.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }

    private void getReview(int idProduct, int idType) {
        dialog.show();
        list.clear();
        String token = SharedPrefs.getInstance().get(ACCESS_TOKEN, String.class);
        APIInterface apiInterface = APILoginClient.getClient().create(APIInterface.class);

        Call<Reviews> call = apiInterface.getReviewProduct(token, idProduct, idType);
        call.enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {
                if (!response.body().getData().isEmpty()) {
                    getTotalNumberPoint(response.body().getData());
                    list.addAll(response.body().getData());
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {
                dialog.dismiss();
                call.cancel();
            }
        });
    }

    private void getTotalNumberPoint(List<ProductReview> productReviews) {
        float number = 0;
        for (int i = 0; i < productReviews.size(); i++) {
            number = number + productReviews.get(i).getRating();
        }

        int count_rating = productReviews.size();
        float rating = (float) (Math.round(number/count_rating*10) / 10.0);
        tvAveReview.setText(String.valueOf(rating));
        reviewRatingBar.setRating(rating);
    }

    private void saveScreenshot() {
        // Save the screenshot
        DateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String date = df.format(Calendar.getInstance().getTime());

        try {
            File file = ScreenShott.getInstance()
                    .saveScreenshotToPicturesFolder(RestaurantDetailActivity.this, bitmap, "date");
            // Display a toast
            Toast.makeText(RestaurantDetailActivity.this, "Bitmap Saved at " + file.getAbsolutePath(),
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void addToFavorite() {
//        Toast.makeText(this, "Added to My List", Toast.LENGTH_SHORT).show();
//
//        RealmConfiguration myConfig = new RealmConfiguration.Builder()
//                .name("myrealm.realm")
//                .schemaVersion(2)
//                .modules(new Restaurant())
//                .build();
//
//        Realm realm = Realm.getInstance(myConfig);
//        Restaurant res = realm.where(Restaurant.class).equalTo("id", restaurant.getId()).findFirst();
//        realm.beginTransaction();
//        if (res == null) {
//            res = realm.createObject(Restaurant.class); // Create a new object
//        }
//        res.copy(restaurant);
//        realm.commitTransaction();
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall();
            }
        } else if (requestCode == CAPTURE_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveScreenshot();
            }
        }
    }
}
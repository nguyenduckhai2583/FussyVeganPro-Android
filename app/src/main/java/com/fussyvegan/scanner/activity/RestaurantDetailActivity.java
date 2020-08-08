package com.fussyvegan.scanner.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.fussyvegan.scanner.DetailsFragment;
import com.fussyvegan.scanner.MapFragment;
import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.ReviewFragment;
import com.fussyvegan.scanner.model.restaurant.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDetailActivity extends AppCompatActivity {

    private static final String RESTAURANT = "restaurant";

    ImageView imgBack, imgRes, imgCamera, imgListWish, ImaCall, imgWebsite;
    TextView tvNameRes, tvAddress, tvNumReview, tvTabTitle;
    ViewPager viewPager;
    TabLayout tabLayout;
    AppCompatRatingBar reviewRatingBar;
    PageApdater adapter;
    private Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        restaurant = getIntent().getParcelableExtra(RESTAURANT);

        addContent();
        addEvent();

        initViewPager();
        initData();
    }

    private void initData() {
        if (restaurant != null) {
            Picasso.get().load(restaurant.getLink_photo()).into(imgRes);
            tvNameRes.setText(restaurant.getName());
            tvAddress.setText(restaurant.getLocation());
            String numReview = "(" +restaurant.getCount_rating() +")";
            tvNumReview.setText(numReview);
            reviewRatingBar.setRating((float) restaurant.getAvg_rating());
        }
    }

    private void initViewPager() {
        adapter = new PageApdater(getSupportFragmentManager());

        Bundle bundle = new Bundle();
        bundle.putParcelable(RESTAURANT, restaurant);

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
    }

    private void addContent() {
        imgBack = findViewById(R.id.imgBack);
        imgRes = findViewById(R.id.imgRes);
        imgCamera = findViewById(R.id.imgCamera);
        imgListWish = findViewById(R.id.imgListWish);
        ImaCall = findViewById(R.id.ImaCall);
        imgWebsite = findViewById(R.id.imgWebsite);
        tvNameRes = findViewById(R.id.tvNameRes);
        tvAddress = findViewById(R.id.tvAddress);
        tvNumReview = findViewById(R.id.tvNumReview);
        tvTabTitle = findViewById(R.id.tvTabTitle);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        reviewRatingBar = findViewById(R.id.reviewRatingBar);
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
}
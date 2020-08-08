package com.fussyvegan.scanner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fussyvegan.scanner.model.restaurant.Restaurant;

public class ReviewFragment extends Fragment {

    private static final String RESTAURANT = "restaurant";
    private Restaurant restaurant;

    TextView tvTotalReview, tvRateFiveStar, tvRateFourStar, tvRateThreeStar, tvRateTwoStar,
            tvRateOneStar, tvWriteReview;
    RecyclerView recyclerViewReview;

    public ReviewFragment() {
        // Required empty public constructor
    }

    public static ReviewFragment newInstance() {
        return new ReviewFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            restaurant = getArguments().getParcelable(RESTAURANT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_review, container, false);
        tvTotalReview = view.findViewById(R.id.tvTotalReview);
        tvRateFiveStar = view.findViewById(R.id.tvRateFiveStar);
        tvRateFourStar = view.findViewById(R.id.tvRateFourStar);
        tvRateThreeStar = view.findViewById(R.id.tvRateThreeStar);
        tvRateTwoStar = view.findViewById(R.id.tvRateTwoStar);
        tvRateOneStar = view.findViewById(R.id.tvRateOneStar);
        tvWriteReview = view.findViewById(R.id.tvWriteReview);
        recyclerViewReview = view.findViewById(R.id.recyclerViewReview);

        tvTotalReview.setText(String.valueOf(restaurant.getCount_rating()));

        return view;
    }
}